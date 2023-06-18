package Game;

import java.util.ArrayList;
import java.util.Stack;

import Game.Piece.*;

public class Game {
    public Board board;
    private boolean whiteTurn;

    private boolean whiteCanCastleKingSide;
    private boolean whiteCanCastleQueenSide;
    private boolean blackCanCastleKingSide;
    private boolean blackCanCastleQueenSide;

    private Stack<Piece> previouslyMoved;
    private Stack<Piece> previouslyCaptured;

    public Game() {
        whiteTurn = true;
        board = new Board();

        whiteCanCastleKingSide = true;
        whiteCanCastleQueenSide = true;
        blackCanCastleKingSide = true;
        blackCanCastleQueenSide = true;

        previouslyMoved = new Stack<Piece>();
        previouslyCaptured = new Stack<Piece>();
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    private static Type[] types = {Type.KING, Type.QUEEN, Type.ROOK, Type.BISHOP, Type.KNIGHT, Type.PAWN};

    public ArrayList<Move> legalMoves() {
        return whiteTurn ? board.generateMovesWhite() : board.generateMovesBlack();
    }

    public void makeMove(Move move) {
        Piece movedPiece = board.getPiece(move.from);

        if (movedPiece == null) {
            throw new IllegalArgumentException("No piece at " + move.from);
        }
        if (movedPiece.isWhite() != whiteTurn) {
            throw new IllegalArgumentException("Wrong color piece at " + move.from);
        }

        previouslyMoved.push(movedPiece);
        previouslyCaptured.push(board.getPiece(move.to));

        if (movedPiece.getType() == Type.KING) {
            if (whiteTurn) {
                whiteCanCastleKingSide = false;
                whiteCanCastleQueenSide = false;
            } else {
                blackCanCastleKingSide = false;
                blackCanCastleQueenSide = false;
            }
        }
        if (movedPiece.getType() == Type.ROOK) {
            if (whiteTurn) {
                if (move.from.file == 0 && move.from.rank == 0) {
                    whiteCanCastleQueenSide = false;
                } else if (move.from.file == 7 && move.from.rank == 0) {
                    whiteCanCastleKingSide = false;
                }
            } else {
                if (move.from.file == 0 && move.from.rank == 7) {
                    blackCanCastleQueenSide = false;
                } else if (move.from.file == 7 && move.from.rank == 7) {
                    blackCanCastleKingSide = false;
                }
            }
        }

        if (movedPiece.getType() == Type.PAWN && (move.to.rank == 0 || move.to.rank == 7)) {
            board.setPiece(move.from, null);
            board.setPiece(move.to, whiteTurn ? Piece.WhiteQueen : Piece.BlackQueen);
        } else {
            board.makeMove(move);
        }

        whiteTurn = !whiteTurn;
    }

    public void undoMove(Move move) {
        // undo a move that was made with makeMove
        // (use move and previouslyMoved)

        board.setPiece(move.from, previouslyMoved.pop());
        board.setPiece(move.to, previouslyCaptured.pop());
        whiteTurn = !whiteTurn;
    }

    public String toString() {
        return board.toString();
    }

    public int searchVariations(short depth) {
        if (depth == 0) {
            return 1;
        }

        int total = 0;

        for (Move m:legalMoves()) {
            makeMove(m);
            if (whiteTurn ? board.blackKingInCheck() : board.whiteKingInCheck()) {
                // Illegal move
                undoMove(m);
                continue;
            }
            total += searchVariations((short) (depth - 1));
            undoMove(m);
        }

        return total;
    }

    private boolean hasValidMove() {
        for (Move m : legalMoves()) {
            makeMove(m);

            // use other color because makeMove changes color
            boolean stillInCheck = whiteTurn ? board.blackKingInCheck() : board.whiteKingInCheck();

            undoMove(m);
            if (!stillInCheck) {
                // valid move to move out of check
                return true;
            }
        }

        return false;
    }

    public boolean isCheckmate() {
        boolean isInCheck = whiteTurn ? board.whiteKingInCheck() : board.blackKingInCheck();
        return isInCheck && !hasValidMove();
    }

    public boolean isDraw() {
        boolean isInCheck = whiteTurn ? board.whiteKingInCheck() : board.blackKingInCheck();
        return !isInCheck && !hasValidMove();
    }

    public GameResult gameResult() {
        boolean isInCheck = whiteTurn ? board.whiteKingInCheck() : board.blackKingInCheck();
        boolean hasValidMove = hasValidMove();

        if (isInCheck && !hasValidMove) {
            return whiteTurn ? GameResult.BLACK_WINS : GameResult.WHITE_WINS;
        } else if (!isInCheck && !hasValidMove) {
            return GameResult.DRAW;
        } else {
            return GameResult.IN_PROGRESS;
        }
    }

    public static Game boardFromFen(String fen){
        Game g = new Game();
        Board board = Board.loadFromFen(fen);
        g.board = board;
        g.whiteTurn = fen.split(" ")[1].equals("w");
        return g;
    }
}

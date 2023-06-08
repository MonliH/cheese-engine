package Game;

import java.util.ArrayList;

public class Game {
    public Board board;
    private boolean whiteTurn;

    private boolean whiteCanCastle;
    private boolean blackCanCastle;

    public Game() {
        whiteTurn = true;
        board = new Board();
        whiteCanCastle = true;
        blackCanCastle = true;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public ArrayList<Move> legalMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int rank=0;rank<8;rank++) {
            for (int file=0;file<8;file++) {
                Piece p = board.getPiece(rank, file);

                if (p == null || p.isWhite() != whiteTurn) {
                    continue;
                }
                switch (p.getType()) {
                    case PAWN: {
                        int direction = p.isWhite() ? 1 : -1;
                        if (board.getPiece(rank + direction, file) == null) {
                            moves.add(new Move(rank, file, rank + direction, file));
                        }
                        if (rank == 1 && p.isWhite() || rank == 6 && !p.isWhite()) {
                            if (board.getPiece(rank + direction, file) == null && board.getPiece(rank + 2 * direction, file) == null) {
                                moves.add(new Move(rank, file, rank + 2 * direction, file));
                            }
                        }
                        if (board.getPiece(rank + direction, file + 1) != null && board.getPiece(rank + direction, file + 1).isWhite() != p.isWhite()) {
                            moves.add(new Move(rank, file, rank + direction, file + 1));
                        }
                        if (board.getPiece(rank + direction, file - 1) != null && board.getPiece(rank + direction, file - 1).isWhite() != p.isWhite()) {
                            moves.add(new Move(rank, file, rank + direction, file - 1));
                        }
                        break;
                    }
                }
            }
        }

        return moves;
    }
}

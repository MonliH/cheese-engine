package Engine;

import Game.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Engine {
    Game currentGame;
    HashMap<Long, TranspositionEntry> transpositionTable = new HashMap();

    public void setCurrentGame(Game game) {
        currentGame = game;
    }

    private void sortMoves(ArrayList<Move> moves) {
        Collections.sort(moves, (o1, o2) ->
                Evaluation.eval[currentGame.board.getPiece(o2.from).ordinal()]
                        - Evaluation.eval[currentGame.board.getPiece(o1.from).ordinal()]);
    }

    private int quiesce(int alpha, int beta) {
        int currentEval = Evaluation.evaluate(currentGame);
        if (currentEval >= beta) {
            return currentEval;
        }
        if (alpha < currentEval) {
            alpha = currentEval;
        }

        ArrayList<Move> moves = currentGame.legalMoves();
        sortMoves(moves);
        for (Move move: moves) {
            if (currentGame.board.getPiece(move.to) != null) {
                currentGame.makeMove(move);
                boolean isIllegal = currentGame.isWhiteTurn() ? currentGame.board.blackKingInCheck() : currentGame.board.whiteKingInCheck();
                if (isIllegal) {
                    currentGame.undoMove(move);
                    continue;
                }
                int score = -quiesce(-beta, -alpha);
                currentGame.undoMove(move);

                if (score >= beta) {
                    return beta;
                }
                if (score > alpha) {
                    alpha=score;
                }
            }
        }

        return alpha;
    }

    private int search(int alpha, int beta, int depth) {
        long hash = BoardHash.hashBoard(currentGame.board);
        int origAlpha = alpha;
        /*if (transpositionTable.containsKey(hash)) {
            TranspositionEntry e = transpositionTable.get(hash);
            if (e.depth >= depth) {
                switch (e.flag) {
                    case EXACT:
                        return e.eval;
                    case LOWER_BOUND:
                        if (e.eval > alpha) {
                            alpha = e.eval;
                        }
                        break;
                    case UPPER_BOUND:
                        if (e.eval > beta) {
                            beta = e.eval;
                        }
                        break;
                }

                if (alpha >= beta) {
                    return e.eval;
                }
            }
        }*/

        if (depth == 0) {
            return quiesce(alpha, beta);
        }

        int bestScore = Integer.MIN_VALUE;

        ArrayList<Move> moves = currentGame.legalMoves();
        sortMoves(moves);
        for (Move move : moves) {
            currentGame.makeMove(move);
            boolean isIllegal = currentGame.isWhiteTurn() ? currentGame.board.blackKingInCheck() : currentGame.board.whiteKingInCheck();
            if (isIllegal) {
                currentGame.undoMove(move);
                continue;
            }
            int score = -search(-beta, -alpha, depth-1);
            currentGame.undoMove(move);

            if (score >= beta) {
                return score;
            }
            if (score > bestScore) {
                bestScore = score;

                if (score > alpha) {
                    alpha = score;
                }
            }
        }

        /*
        TranspositionEntry.Flag flag = alpha <= origAlpha ? (TranspositionEntry.Flag.UPPER_BOUND) :
                (alpha >= beta ? (TranspositionEntry.Flag.LOWER_BOUND) : (TranspositionEntry.Flag.EXACT));
        if (transpositionTable.containsKey(hash)) {
            TranspositionEntry entry = transpositionTable.get(hash);
            entry.flag = flag;
            entry.depth = depth;
            entry.eval = bestScore;
        } else {
            transpositionTable.put(hash, new TranspositionEntry(depth, bestScore, flag));
        }*/

        return bestScore;
    }

    private Move bestMoveInner(Game game, int depth) {
        currentGame = game;

        Move bestMove = null;
        int bestEval = Integer.MIN_VALUE;

        ArrayList<Move> moves = currentGame.legalMoves();
        sortMoves(moves);
        for (Move move : moves) {
            currentGame.makeMove(move);
            boolean isIllegal = currentGame.isWhiteTurn() ? currentGame.board.blackKingInCheck() : currentGame.board.whiteKingInCheck();
            if (isIllegal) {
                currentGame.undoMove(move);
                continue;
            }
            int score = -search(Integer.MIN_VALUE, Integer.MAX_VALUE, depth);

            currentGame.undoMove(move);

            if (score > bestEval) {
                bestEval = score;
                bestMove = move;
            }
        }

        System.out.println("Best eval at depth " + depth + ": " + bestEval);

        return bestMove;
    }

    public Move bestMove(Game game, int depth) {
        Move bestMove = null;
        for (int i=1;i<=depth;i++) {
            System.out.println("Depth: " + i);
            bestMove = bestMoveInner(game, i);
        }

        return bestMove;
    }

    public Move bestMove(Game game) {
        return bestMove(game, 4);
    }
}

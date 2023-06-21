package Engine;

import Game.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Chess engine class that contains the search algorithm.
 * The search algorithm is a negamax search with alpha-beta pruning.
 */
public class Engine {
    private Game currentGame;

    /**
     * Set the current game that the engine is playing.
     *
     * @param game
     */
    public void setCurrentGame(Game game) {
        currentGame = game;
    }

    /**
     * Get the game that the engine is playing.
     *
     * @return the current game
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * Sort the moves (in place) by their weight, so that the engine can search the moves that are likely to
     * be good first.
     *
     * @param moves
     */
    private void sortMoves(ArrayList<Move> moves) {
        Collections.sort(moves, (o1, o2) ->
                Evaluation.eval[currentGame.board.getPiece(o2.from).ordinal()]
                        - Evaluation.eval[currentGame.board.getPiece(o1.from).ordinal()]);
    }

    /**
     * Determine the evaluation of the board after perfect play to a depth.
     *
     * @param alpha Alpha value (best score for white)
     * @param beta Beta value (best score for black)
     * @param depth Depth to search to
     * @return
     */
    private int search(int alpha, int beta, int depth) {
        if (depth == 0 || currentGame.isCheckmate()) {
            // At depth 0, perform a "quiescence search"
            // Essentially, we do not want to end the search on a capture chain because then the engine might
            // miss that it's opponent can capture a piece back, meaning it may take random pieces
            return quiesce(alpha, beta);
        }

        int bestScore = Integer.MIN_VALUE;  // At the start, assume the best score is the worst possible

        ArrayList<Move> moves = currentGame.legalMoves();
        sortMoves(moves);  // Sort moves to make search more efficient

        for (Move move : moves) {
            currentGame.makeMove(move);
            boolean isIllegal = currentGame.isWhiteTurn() ? currentGame.board.blackKingInCheck() : currentGame.board.whiteKingInCheck();
            if (isIllegal) {
                // If the move is illegal, undo it and continue
                currentGame.undoMove(move);
                continue;
            }
            // Get the evaluation after making this move
            int score = -search(-beta, -alpha, depth-1);
            currentGame.undoMove(move);


            if (score >= beta) {
                // Beta cutoff
                // Essentially, if the score is worse than the best score, we can stop searching because the opponent
                // will never allow us to get to this position
                return score;
            }
            if (score > bestScore) {
                bestScore = score;

                if (score > alpha) {
                    alpha = score;
                }
            }
        }

        return bestScore;
    }

    /**
     * Performs a quiescence search to avoid the horizon effect, where the engine might make a bad move because
     * it doesn't see.
     */
    private int quiesce(int alpha, int beta) {
        //
        int currentEval = Evaluation.evaluate(currentGame);
        if (currentEval >= beta) {
            return beta;
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
                    alpha = score;
                }
            }
        }

        return alpha;
    }


    /**
     * Returns the best move for the current game state.
     *
     * @param game The current game state.
     * @param depth The depth to search to.
     * @return The best move.
     */
    public Move bestMove(Game game, int depth) {
        currentGame = game;

        // Store the best move
        Move bestMove = null;

        int beta = 999999999;  // Very large value
        int alpha = -999999999;  // Very small value

        ArrayList<Move> moves = currentGame.legalMoves();

        for (Move move : moves) {
            currentGame.makeMove(move);
            boolean isIllegal = currentGame.isWhiteTurn() ?
                    currentGame.board.blackKingInCheck() : currentGame.board.whiteKingInCheck();
            if (isIllegal) {
                // If the move is illegal, undo it and continue
                currentGame.undoMove(move);
                continue;
            }

            // Determine the evaluation after making this move
            // we call a different function here because we don't need to save the best move in the search (improving
            // performance)
            int score = -search(-beta, -alpha, depth-1);

            currentGame.undoMove(move);

            // If the evaluation is better than the previous best evaluation, save it as the best move
            if (score > alpha) {
                alpha = score;
                bestMove = move;
            }
        }

        // Print info for universal chess interface (depth and score)
        System.out.println("info depth " + depth + " score cp " + alpha);

        return bestMove;
    }
}

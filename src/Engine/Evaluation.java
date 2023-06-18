package Engine;

import Game.*;

public class Evaluation {
    private static final int QUEEN_WEIGHT = 900;
    private static final int ROOK_WEIGHT = 500;
    private static final int BISHOP_WEIGHT = 300;
    private static final int KNIGHT_WEIGHT = 300;
    private static final int PAWN_WEIGHT = 100;

    public static final int[] eval = {PAWN_WEIGHT, ROOK_WEIGHT, KNIGHT_WEIGHT, BISHOP_WEIGHT, QUEEN_WEIGHT, 1000};

    public static int evaluate(Game game) {
        int sideToMove = game.isWhiteTurn() ? 1 : -1;

        int score = QUEEN_WEIGHT * (game.board.queenDelta())
                + ROOK_WEIGHT * (game.board.rookDelta())
                + BISHOP_WEIGHT * (game.board.bishopDelta())
                + KNIGHT_WEIGHT * (game.board.knightDelta())
                + PAWN_WEIGHT * (game.board.pawnDelta());

        return score * sideToMove + game.legalMoves().size() / 2;
    }
}

package Engine;

import Game.*;
import java.util.concurrent.ThreadLocalRandom;

public class BoardHash {
    public static long[][][] zobristTable = new long[6][2][64];

    private static final BoardHash _instance = new BoardHash();

    private BoardHash() {
        initZobristTable();
    }

    private static void initZobristTable() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 64; k++) {
                    zobristTable[i][j][k] = ThreadLocalRandom.current().nextLong();
                }
            }
        }
    }

    public static long hashBoard(Board board) {
        long hash = 0L;
        for (int rank=0; rank<8; rank++) {
            for (int file=0; file<8; file++) {
                Piece piece = board.getPiece(rank, file);
                if (piece != null) {
                    hash ^= zobristTable[piece.ordinal()][piece.isWhite() ? 1 : 0][rank * 8 + file];
                }
            }
        }

        return hash;
    }
}

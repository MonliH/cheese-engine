package Game;

public class LookUpTables {
    // Helper lookup classes to prevent unnecessary object construction during search.
    public static LookUpTables instance = new LookUpTables();

    public final long[][] moveMasks;

    // Pre-existing for every square and move possible (to prevent unnecessary object allocations)
    public final Square[] squares;
    public final Move[][] moves;

    /**
     * Cardinal directions on the board.
     */
    public enum Direction {
        NORTH(0),
        EAST(1),
        SOUTH(2),
        WEST(3),
        NORTH_EAST(4),
        SOUTH_EAST(5),
        NORTH_WEST(6),
        SOUTH_WEST(7);

        public int id;

        Direction(int id) {
            this.id = id;
        }
    }

    LookUpTables() {
        // Build lookup for
        moveMasks = new long[8][65];
        squares = new Square[64];

        // Initialize squares
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squares[rank * 8 + file] = new Square(rank, file);
            }
        }

        // Initialize moves
        moves = new Move[64][64];
        for (int from = 0; from < 64; from++) {
            for (int to = 0; to < 64; to++) {
                moves[from][to] = new Move(squares[from], squares[to]);
            }
        }

        // Move masks for each direction (used for move generation)
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                long mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank + i;
                    int f = file + i;
                    if (r < 8 && f < 8) {
                        mask |= 1L << (r * 8 + f);
                    }
                }
                moveMasks[Direction.NORTH_EAST.id][rank * 8 + file] = mask;

                mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank - i;
                    int f = file + i;
                    if (r >= 0 && f < 8) {
                        mask |= 1L << (r * 8 + f);
                    }
                }
                moveMasks[Direction.SOUTH_EAST.id][rank * 8 + file] = mask;

                mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank + i;
                    int f = file - i;
                    if (r < 8 && f >= 0) {
                        mask |= 1L << (r * 8 + f);
                    }
                }
                moveMasks[Direction.NORTH_WEST.id][rank * 8 + file] = mask;

                mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank - i;
                    int f = file - i;
                    if (r >= 0 && f >= 0) {
                        mask |= 1L << (r * 8 + f);
                    }
                }
                moveMasks[Direction.SOUTH_WEST.id][rank * 8 + file] = mask;

                mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank + i;
                    int f = file;
                    if (r < 8) {
                        mask |= 1L << (r * 8 + f);
                    }
                }
                moveMasks[Direction.NORTH.id][rank * 8 + file] = mask;

                mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank - i;
                    int f = file;
                    if (r >= 0) {
                        mask |= 1L << (r * 8 + f);
                    }
                }

                moveMasks[Direction.SOUTH.id][rank * 8 + file] = mask;

                mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank;
                    int f = file + i;
                    if (f < 8) {
                        mask |= 1L << (r * 8 + f);
                    }
                }
                moveMasks[Direction.EAST.id][rank * 8 + file] = mask;

                mask = 0;
                for (int i = 0; i < 8; i++) {
                    int r = rank;
                    int f = file - i;
                    if (f >= 0) {
                        mask |= 1L << (r * 8 + f);
                    }
                }
                moveMasks[Direction.WEST.id][rank * 8 + file] = mask;
            }
        }
    }
}

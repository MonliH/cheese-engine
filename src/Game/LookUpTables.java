package Game;

public class LookUpTables {
    public static LookUpTables instance = new LookUpTables();

    public long[][] moveMasks;

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
        // build a table
        moveMasks = new long[8][65];

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

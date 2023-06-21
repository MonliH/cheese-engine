package Game;

public class Rank {
    // Bitmasks for each rank (row) on the board

    public static final long ONE = 0b11111111L;
    public static final long TWO = ONE << 8;
    public static final long THREE = TWO << 8;
    public static final long FOUR = THREE << 8;
    public static final long FIVE = FOUR << 8;
    public static final long SIX = FIVE << 8;
    public static final long SEVEN = SIX << 8;
    public static final long EIGHT = SEVEN << 8;
}

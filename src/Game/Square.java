package Game;

public class Square {
    public int rank;
    public int file;

    public Square(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public long toBitmask() {
        return 1L << (rank * 8 + file);
    }
}

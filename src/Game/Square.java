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
    public String toString() {
        return Character.toString((char)('a' + file)) + (rank + 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Square)) return false;
        Square s = (Square) other;
        return s.rank == rank && s.file == file;
    }
}

package Game;

/**
 * Represents a move in the game. A move is a pair of squares, the square the piece is moving from and the square the
 * piece is moving to.
 */
public class Move {
    public Square from;
    public Square to;

    /**
     * Creates a move from one square to another.
     */
    public Move(Square from, Square to) {
        this.from = from;
        this.to = to;
    }

    public Move(int fromRank, int fromFile, int toRank, int toFile) {
        this.from = new Square(fromRank, fromFile);
        this.to = new Square(toRank, toFile);
    }

    public void apply(Board board) {
        board.setPiece(to, board.getPiece(from));
        board.setPiece(from, null);
    }

    public String toString() {
        return from + " -> " + to;
    }

    public String toStringWithoutArrow() {
        return "" + from + to;
    }

    /**
     * Parses a move from a string. The string should be in the format "a1b2" or "a1-b2" (case insensitive).
     *
     * @param stringMove the string to parse in the
     * @return parsed move, or null if the string is not a valid move
     */
    public static Move parseMove(String stringMove) {
        stringMove = stringMove.toLowerCase();
        stringMove  = stringMove.replaceAll("\\s+|-","");

        if (stringMove.length() != 4) {
            return null;
        }
        int fromFile = stringMove.charAt(0) - 'a';
        int fromRank = stringMove.charAt(1) - '1';
        int toFile = stringMove.charAt(2) - 'a';
        int toRank = stringMove.charAt(3) - '1';
        return new Move(fromRank, fromFile, toRank, toFile);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Move)) return false;
        Move m = (Move) o;
        return m.from.equals(from) && m.to.equals(to);
    }
}

package Game;

public class Move {
    public Square from;
    public Square to;

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
}

import Game.*;

public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        System.out.println(b);
        System.out.println(Board.printBitboard(b.getMoves(Piece.WhiteBishop, new Square(4, 4))));
        System.out.println(Board.printBitboard(b.getMoves(Piece.WhiteQueen, new Square(4, 0))));
    }
}
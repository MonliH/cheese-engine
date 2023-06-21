import Game.*;
import UI.CLIGame;
import UI.EnginePlayer;
import UI.HumanPlayer;

public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        b.clearBoard();
        b.setPiece(new Square(3, 4), Piece.WhiteBishop);
        b.setPiece(new Square(1, 2), Piece.BlackPawn);
        System.out.println(b);
        System.out.println(b.generatePieceMoves(Piece.WhiteBishop, 0L));
        //CLIGame.start(new HumanPlayer(), new EnginePlayer());
    }
}
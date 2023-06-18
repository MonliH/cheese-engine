import Engine.Engine;
import Engine.Evaluation;

import Game.*;
import UI.CLIGame;
import UI.EnginePlayer;
import UI.HumanPlayer;

public class Main {
    public static void main(String[] args) {
        //System.out.println(Board.loadFromFen("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"));
        CLIGame.start(new HumanPlayer(), new EnginePlayer());
    }

    public static void testFriedLiver() {
        Game g = new Game();
        Engine e = new Engine();

        // fried liver
        g.makeMove(Move.parseMove("e2e4"));
        g.makeMove(Move.parseMove("e7e5"));
        g.makeMove(Move.parseMove("g1f3"));
        g.makeMove(Move.parseMove("b8c6"));
        g.makeMove(Move.parseMove("f1c4"));
        g.makeMove(Move.parseMove("g8f6"));
        g.makeMove(Move.parseMove("f3g5"));
        g.makeMove(Move.parseMove("a7a6"));
        //g.makeMove(Move.parseMove("g5f7"));

        e.setCurrentGame(g);

        long time = System.currentTimeMillis();
        Move move = e.bestMove(g, 3);
        System.out.println("Time taken (s): " + (float)(System.currentTimeMillis() - time)/1000.0f);
        g.makeMove(move);
        System.out.println(g);
        //CLIGame.start(new HumanPlayer(), new HumanPlayer());
    }
}
package UI;

import Engine.Engine;
import Game.Game;
import Game.Move;

/**
 * A player that uses the engine to make moves.
 */
public class EnginePlayer extends Player {
    Engine engine;
    public EnginePlayer() {
        engine = new Engine();
    }

    public Move getMove(Game game) {
        System.out.println("Engine is thinking...");
        return engine.bestMove(game, 5, false);
    }

    public String getName() {
        return "Engine";
    }
}

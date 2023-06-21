package UI;

import Engine.Engine;
import Game.Game;
import Game.Move;

public class EnginePlayer extends Player {
    Engine engine;
    public EnginePlayer() {
        engine = new Engine();
    }

    public Move getMove(Game game) {
        return engine.bestMove(game, 5);
    }

    public String getName() {
        return "Engine";
    }
}

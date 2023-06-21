import Engine.Engine;
import Engine.Evaluation;

import Game.*;
import UI.CLIGame;
import UI.EnginePlayer;
import UI.HumanPlayer;

public class Main {
    public static void main(String[] args) {
        CLIGame.start(new HumanPlayer(), new EnginePlayer());
    }
}
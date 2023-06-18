package UI;

import Game.Game;
import Game.Move;

public abstract class Player {
    public abstract Move getMove(Game game);
    public abstract String getName();
}

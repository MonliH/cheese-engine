package UI;

import Game.Game;
import Game.Move;

// A player in the game.
public abstract class Player {
    /**
     * Get the next move for this player.
     */
    public abstract Move getMove(Game game);
    
    /**
     * Get the name of this player.
     */
    public abstract String getName();
}

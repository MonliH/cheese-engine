package UI;
import Game.Game;
import Game.Move;
import Game.GameResult;

import java.util.Scanner;

public class CLIGame {
    public static void start(Player white, Player black) {
        System.out.println("Welcome to Chess!");

        Game game = new Game();
        game: while (true) {
            System.out.println(game.board);

            GameResult result = game.gameResult();
            switch (result) {
                case WHITE_WINS:
                    System.out.println("White wins!");
                    break game;
                case BLACK_WINS:
                    System.out.println("Black wins!");
                    break game;
                case DRAW:
                    System.out.println("Draw!");
                    break game;
            }

            Move move = game.isWhiteTurn() ? white.getMove(game) : black.getMove(game);
            game.makeMove(move);
        }
    }
}

import Game.*;
import UI.CLIGame;
import UI.EnginePlayer;
import UI.HumanPlayer;
import UI.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);

        System.out.println("Welcome to chess!");
        System.out.print("Do you want to play against the chess engine (the computer)? (y/n): ");
        boolean wantsToPlayEngine = inp.nextLine().toLowerCase().charAt(0) == 'y';

        if (wantsToPlayEngine) {
            System.out.print("Do you want to play as white or black against the engine? (w/b): ");
            boolean wantsToPlayWhite = inp.nextLine().toLowerCase().charAt(0) == 'w';
            Player white = wantsToPlayWhite ? new HumanPlayer() : new EnginePlayer();
            Player black = wantsToPlayWhite ? new EnginePlayer() : new HumanPlayer();
            CLIGame.start(white, black);
        } else {
            CLIGame.start(new HumanPlayer(), new HumanPlayer());
        }
    }
}
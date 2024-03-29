package UI;

import Game.Game;
import Game.Move;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A player that makes moves based on user input.
 */
public class HumanPlayer extends Player {
    public Move getMove(Game game) {
        Scanner inp = new Scanner(System.in);
        Move move = null;
        while (true) {
            String turnText = game.isWhiteTurn() ? "white" : "black";

            System.out.print("Your turn ("+turnText+")! Enter a move (e.g., a2a3): ");
            String command = inp.nextLine();

            if (command.equals("quit")) {
                break;
            }
            move = Move.parseMove(command);
            if (move == null) continue;

            try {
                // Get all legal moves and check if the move is in this list (if not, it's illegal)
                ArrayList<Move> m = game.legalMoves();

                if (!m.contains(move)) {
                    // Illegal move
                    System.out.println("Illegal move!");
                    continue;
                }

                game.makeMove(move);
                // If the move puts the player in check, it's illegal
                boolean isIllegal = game.isWhiteTurn() ? game.board.blackKingInCheck() : game.board.whiteKingInCheck();
                game.undoMove(move);

                if (isIllegal) {
                    // Illegal move
                    System.out.println("Illegal move!");
                    continue;
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return move;
    }

    public String getName() {
        return "Human";
    }
}

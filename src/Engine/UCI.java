package Engine;

import java.io.PrintWriter;
import java.util.Scanner;
import Game.*;

public class UCI {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Scanner scanner = new Scanner(System.in);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("logs.txt", "UTF-8");
        } catch (Exception e) {
            System.out.println("Error writing to file");
        }

        while (true) {
            String line = scanner.nextLine();
            // write to file
            if (writer != null) {
                writer.println(line);
            }

            if (line.equals("uci")) {
                System.out.println("id name Cheese Engine");
                System.out.println("uciok");
            } else if (line.equals("isready")) {
                System.out.println("readyok");
            } else if (line.equals("ucinewgame")) {
                engine = new Engine();
            } else if (line.startsWith("position")) {
                String[] tokens = line.split(" ");
                if (tokens[1].equals("startpos")) {
                    engine.setCurrentGame(new Game());
                    if (tokens.length > 2 && tokens[2].equals("moves")) {
                        for (int i = 3; i < tokens.length; i++) {
                            engine.getCurrentGame().makeMove(Move.parseMove(tokens[i]));
                        }
                    }
                } else if (tokens[1].equals("fen")) {
                    StringBuilder fen = new StringBuilder();
                    for (int i = 2; i < tokens.length; i++) {
                        fen.append(tokens[i]).append(" ");
                    }
                    engine.setCurrentGame(Game.boardFromFen(fen.toString()));
                }
            } else if (line.startsWith("go")) {
                String[] tokens = line.split(" ");
                int depth = 4;
                if (tokens.length > 1 && tokens[1].equals("depth")) {
                    depth = Integer.parseInt(tokens[2]);
                }
                Move move = engine.bestMove(engine.getCurrentGame(), depth);
                if (move == null) {
                    System.out.println("bestmove null");
                    continue;
                }
                System.out.println("bestmove " + move.toStringWithoutArrow());
            } else if (line.equals("quit")) {
                break;
            }
        }

        if (writer != null) writer.close();
    }
}

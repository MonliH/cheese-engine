package Game;

import java.util.ArrayList;

public class Piece {
    public enum Color {
        WHITE,
        BLACK
    }

    public enum Type {
        PAWN,
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING
    }

    private Color color;
    private Type type;

    public static Piece WhitePawn = new Piece(Color.WHITE, Type.PAWN);
    public static Piece WhiteRook = new Piece(Color.WHITE, Type.ROOK);
    public static Piece WhiteKnight = new Piece(Color.WHITE, Type.KNIGHT);
    public static Piece WhiteBishop = new Piece(Color.WHITE, Type.BISHOP);
    public static Piece WhiteQueen = new Piece(Color.WHITE, Type.QUEEN);
    public static Piece WhiteKing = new Piece(Color.WHITE, Type.KING);

    public static Piece BlackPawn = new Piece(Color.BLACK, Type.PAWN);
    public static Piece BlackRook = new Piece(Color.BLACK, Type.ROOK);
    public static Piece BlackKnight = new Piece(Color.BLACK, Type.KNIGHT);
    public static Piece BlackBishop = new Piece(Color.BLACK, Type.BISHOP);
    public static Piece BlackQueen = new Piece(Color.BLACK, Type.QUEEN);
    public static Piece BlackKing = new Piece(Color.BLACK, Type.KING);

    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public int ordinal() {
        switch (type) {
            case PAWN:
                return 0;
            case ROOK:
                return 1;
            case KNIGHT:
                return 2;
            case BISHOP:
                return 3;
            case QUEEN:
                return 4;
            case KING:
                return 5;
            default:
                return -1;
        }
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public boolean isWhite() {
        return color == Color.WHITE;
    }

    /**
     * Convert a piece to a character. Returns '-' if the piece is null.
     */
    public char toCharPiece() {
        // above using switch statement
        switch (type) {
            case PAWN:
                return 'P';
            case ROOK:
                return 'R';
            case KNIGHT:
                return 'N';
            case BISHOP:
                return 'B';
            case QUEEN:
                return 'Q';
            case KING:
                return 'K';
            default:
                return '-';
        }
    }

    public char toChar() {
        if (color == Color.WHITE) return Character.toLowerCase(toCharPiece());
        return toCharPiece();
    }

    public String toString() {
        return toChar() + "";
    }

    /**
     * Convert a character to a piece. Returns null if the character is not a valid piece.
     * Capital letters are white and lowercase letters are black.
     */
    public static Piece fromChar(char c) {
        switch (c) {
            case 'P':
                return WhitePawn;
            case 'R':
                return WhiteRook;
            case 'N':
                return WhiteKnight;
            case 'B':
                return WhiteBishop;
            case 'Q':
                return WhiteQueen;
            case 'K':
                return WhiteKing;
            case 'p':
                return BlackPawn;
            case 'r':
                return BlackRook;
            case 'n':
                return BlackKnight;
            case 'b':
                return BlackBishop;
            case 'q':
                return BlackQueen;
            case 'k':
                return BlackKing;
            default:
                return null;
        }
    }
}

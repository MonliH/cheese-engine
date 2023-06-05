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

    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public char toCharPiece() {
        if (type == Type.PAWN) return 'P';
        if (type == Type.ROOK) return 'R';
        if (type == Type.KNIGHT) return 'N';
        if (type == Type.BISHOP) return 'B';
        if (type == Type.QUEEN) return 'Q';
        if (type == Type.KING) return 'K';
        return '-';
    }

    public char toChar() {
        if (color == Color.WHITE) return Character.toLowerCase(toCharPiece());
        return toCharPiece();
    }
}

package Game;

public class Board {
    private Piece[][] pieces;

    public Piece[][] getPieces() {
        return pieces;
    }

    public Board(){
        pieces = new Piece[8][8];

        // White pieces
        pieces[0][0] = new Piece(Piece.Color.WHITE, Piece.Type.ROOK);
        pieces[0][1] = new Piece(Piece.Color.WHITE, Piece.Type.KNIGHT);
        pieces[0][2] = new Piece(Piece.Color.WHITE, Piece.Type.BISHOP);
        pieces[0][3] = new Piece(Piece.Color.WHITE, Piece.Type.QUEEN);
        pieces[0][4] = new Piece(Piece.Color.WHITE, Piece.Type.KING);
        pieces[0][5] = new Piece(Piece.Color.WHITE, Piece.Type.BISHOP);
        pieces[0][6] = new Piece(Piece.Color.WHITE, Piece.Type.KNIGHT);
        pieces[0][7] = new Piece(Piece.Color.WHITE, Piece.Type.ROOK);

        // Black pieces
        pieces[7][0] = new Piece(Piece.Color.BLACK, Piece.Type.ROOK);
        pieces[7][1] = new Piece(Piece.Color.BLACK, Piece.Type.KNIGHT);
        pieces[7][2] = new Piece(Piece.Color.BLACK, Piece.Type.BISHOP);
        pieces[7][3] = new Piece(Piece.Color.BLACK, Piece.Type.QUEEN);
        pieces[7][4] = new Piece(Piece.Color.BLACK, Piece.Type.KING);
        pieces[7][5] = new Piece(Piece.Color.BLACK, Piece.Type.BISHOP);
        pieces[7][6] = new Piece(Piece.Color.BLACK, Piece.Type.KNIGHT);
        pieces[7][7] = new Piece(Piece.Color.BLACK, Piece.Type.ROOK);

        // Pawns
        for (int i = 0; i < 8; i++) {
            pieces[1][i] = new Piece(Piece.Color.WHITE, Piece.Type.PAWN);
            pieces[6][i] = new Piece(Piece.Color.BLACK, Piece.Type.PAWN);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            result.append(i+1);
            result.append(" | ");
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] == null) {
                    result.append("-");
                } else {
                    result.append(pieces[i][j].toChar());
                }
                result.append(" ");
            }
            result.append("\n");
        }

        result.append("-------------------\n");
        result.append("   ");
        for (int i = 0; i < 8; i++) {
            result.append(" ");
            result.append((char)('a'+i));
        }
        return result.toString();
    }
}

package Game;

import java.util.ArrayList;

public class Board {
    private long blackPawns;
    private long blackRooks;
    private long blackKnights;
    private long blackBishops;
    private long blackQueens;
    private long blackKing;

    private long whitePawns;
    private long whiteRooks;
    private long whiteKnights;
    private long whiteBishops;
    private long whiteQueens;
    private long whiteKing;

    public Board(){
        setPiece(new Square(0, 0), Piece.WhiteRook);
        setPiece(new Square(0, 1), Piece.WhiteKnight);
        setPiece(new Square(0, 2), Piece.WhiteBishop);
        setPiece(new Square(0, 3), Piece.WhiteQueen);
        setPiece(new Square(0, 4), Piece.WhiteKing);
        setPiece(new Square(0, 5), Piece.WhiteBishop);
        setPiece(new Square(0, 6), Piece.WhiteKnight);
        setPiece(new Square(0, 7), Piece.WhiteRook);

        setPiece(new Square(7, 0), Piece.BlackRook);
        setPiece(new Square(7, 1), Piece.BlackKnight);
        setPiece(new Square(7, 2), Piece.BlackBishop);
        setPiece(new Square(7, 3), Piece.BlackQueen);
        setPiece(new Square(7, 4), Piece.BlackKing);
        setPiece(new Square(7, 5), Piece.BlackBishop);
        setPiece(new Square(7, 6), Piece.BlackKnight);
        setPiece(new Square(7, 7), Piece.BlackRook);

        for (int i = 0; i < 8; i++) {
            setPiece(new Square(1, i), Piece.WhitePawn);
            setPiece(new Square(6, i), Piece.BlackPawn);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            result.append(i+1);
            result.append(" | ");
            for (int j = 0; j < 8; j++) {
                Piece p = getPiece(i, j);
                if (p == null) {
                    result.append("-");
                } else {
                    result.append(p.toChar());
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

    public Piece getPiece(Square square) {
        return bitmaskToPiece(square.toBitmask());
    }

    public Piece getPiece(int rank, int file) {
        return bitmaskToPiece(1L << (rank * 8 + file));
    }

    public void makeMove(Move move) {
        Piece piece = getPiece(move.from);
        setPiece(move.from, null);
        setPiece(move.to, piece);
    }

    public void clearBoard() {
        blackPawns = 0;
        blackRooks = 0;
        blackKnights = 0;
        blackBishops = 0;
        blackQueens = 0;
        blackKing = 0;

        whitePawns = 0;
        whiteRooks = 0;
        whiteKnights = 0;
        whiteBishops = 0;
        whiteQueens = 0;
        whiteKing = 0;
    }

    private long getBitmaskForPiece(Piece piece) {
        if (piece.isWhite()) {
            switch (piece.getType()) {
                case KING -> {return whiteKing;}
                case QUEEN ->{ return whiteQueens;}
                case ROOK ->{ return whiteRooks;}
                case BISHOP ->{ return whiteBishops;}
                case KNIGHT ->{ return whiteKnights;}
                case PAWN ->{ return whitePawns;}
            }
        } else {
            switch (piece.getType()) {
                case KING -> {
                    return blackKing;
                }
                case QUEEN -> {return blackQueens;}
                case ROOK -> {return blackRooks;}
                case BISHOP -> {return blackBishops;}
                case KNIGHT -> {return blackKnights;}
                case PAWN -> {return blackPawns;}
            }
        }

        return 0;
    }

    public void setPiece(Square square, Piece piece) {
        long bitmask = square.toBitmask();
        whiteKing &= ~bitmask;
        whiteQueens &= ~bitmask;
        whiteRooks &= ~bitmask;
        whiteBishops &= ~bitmask;
        whiteKnights &= ~bitmask;
        whitePawns &= ~bitmask;

        blackKing &= ~bitmask;
        blackQueens &= ~bitmask;
        blackRooks &= ~bitmask;
        blackBishops &= ~bitmask;
        blackKnights &= ~bitmask;
        blackPawns &= ~bitmask;

        if (piece == null) {
            return;
        }

        if (piece.isWhite()) {
            switch (piece.getType()) {
                case KING -> whiteKing |= bitmask;
                case QUEEN -> whiteQueens |= bitmask;
                case ROOK -> whiteRooks |= bitmask;
                case BISHOP -> whiteBishops |= bitmask;
                case KNIGHT -> whiteKnights |= bitmask;
                case PAWN -> whitePawns |= bitmask;
            }
        } else {
            switch (piece.getType()) {
                case KING -> blackKing |= bitmask;
                case QUEEN -> blackQueens |= bitmask;
                case ROOK -> blackRooks |= bitmask;
                case BISHOP -> blackBishops |= bitmask;
                case KNIGHT -> blackKnights |= bitmask;
                case PAWN -> blackPawns |= bitmask;
            }
        }
    }

    private Piece bitmaskToPiece(long bitmask) {
        if ((blackPawns & bitmask) != 0) {
            return Piece.BlackPawn;
        }

        if ((blackRooks & bitmask) != 0) {
            return Piece.BlackRook;
        }

        if ((blackKnights & bitmask) != 0) {
            return Piece.BlackKnight;
        }

        if ((blackBishops & bitmask) != 0) {
            return Piece.BlackBishop;
        }

        if ((blackQueens & bitmask) != 0) {
            return Piece.BlackQueen;
        }

        if ((blackKing & bitmask) != 0) {
            return Piece.BlackKing;
        }

        if ((whitePawns & bitmask) != 0) {
            return Piece.WhitePawn;
        }

        if ((whiteRooks & bitmask) != 0) {
            return Piece.WhiteRook;
        }

        if ((whiteKnights & bitmask) != 0) {
            return Piece.WhiteKnight;
        }

        if ((whiteBishops & bitmask) != 0) {
            return Piece.WhiteBishop;
        }

        if ((whiteQueens & bitmask) != 0) {
            return Piece.WhiteQueen;
        }

        if ((whiteKing & bitmask) != 0) {
            return Piece.WhiteKing;
        }

        return null;
    }

    public long occupied() {
        return blackRooks | blackKnights | blackBishops | blackQueens | blackKing | blackPawns |
                whiteRooks | whiteKnights | whiteBishops | whiteQueens | whiteKing | whitePawns;
    }

    public long occupiedWhite() {
        return whiteRooks | whiteKnights | whiteBishops | whiteQueens | whiteKing | whitePawns;
    }

    public long occupiedBlack() {
        return blackRooks | blackKnights | blackBishops | blackQueens | blackKing | blackPawns;
    }

    public static String printBitboard(long bitboard) {
        StringBuilder result = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            result.append(i+1);
            result.append(" | ");
            for (int j = 0; j < 8; j++) {
                long p = bitboard & (1L << (i * 8 + j));
                if (p == 0) {
                    result.append("0");
                } else {
                    result.append("1");
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

        result.append("\n");
        result.append(Long.toBinaryString(bitboard));
        result.append("L");
        return result.toString();
    }

    public static long moveNorth(long bitboard) {
        return bitboard << 8;
    }

    public static long moveSouth(long bitboard) {
        return bitboard >>> 8;
    }

    public static long moveEast(long bitboard) {
        return (bitboard & ~File.H) << 1;
    }

    public static long moveWest(long bitboard) {
        return (bitboard & ~File.A) >>> 1;
    }

    public long getMoves(Piece piece, Square position) {
        switch (piece.getType()) {
            case PAWN:
                return getPawnMoves(piece.getColor(), position);
            case ROOK:
                return getRookMoves(position);
            case KNIGHT:
                return getKnightMoves(position);
            case BISHOP:
                return getBishopMoves(position);
            case QUEEN:
                return getQueenMoves(position);
            case KING:
                return getKingMoves(position);
            default:
                return 0L;
        }
    }

    private long getPawnMoves(Piece.Color color, Square position) {
        long positionBitmask = position.toBitmask();
        long moves;
        long captures;
        if (color.equals(Piece.Color.WHITE))  {
            boolean canMoveTwo = (positionBitmask & Rank.TWO) != 0;
            moves = moveNorth(positionBitmask) & ~occupied();
            if (canMoveTwo) {
                moves |= moveNorth(moves) & ~occupied();
            }
            // capture
            captures = moveNorth(moveEast(positionBitmask)) & occupiedBlack();
            captures |= moveNorth(moveWest(positionBitmask)) & occupiedBlack();
        } else {
            boolean canMoveTwo = (positionBitmask & Rank.SEVEN) != 0;
            moves = moveSouth(positionBitmask) & ~occupied();
            if (canMoveTwo) {
                moves |= moveSouth(moves) & ~occupied();
            }
            // capture
            captures = moveSouth(moveEast(positionBitmask)) & occupiedWhite();
            captures |= moveSouth(moveWest(positionBitmask)) & occupiedWhite();
        }
        moves &= ~occupied();
        moves |= captures;

        return moves;
    }
    private long getBishopMoves(Square position) {
        long positionBitmask = position.toBitmask();
        long notAtPosition = ~positionBitmask;
        int location = Long.numberOfTrailingZeros(positionBitmask);

        long attacks = 0L;
        long blockers = occupied();
        // northwest
        long NWAttacks = LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH_WEST.id][location];
        attacks |= NWAttacks;
        if ((NWAttacks & blockers) != 0) {
            int blockerLocation = Long.numberOfTrailingZeros(NWAttacks & blockers & notAtPosition);
            attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH_WEST.id][blockerLocation];
            if (blockerLocation != 64) attacks |= 1L << blockerLocation;
        }

        // northeast
        long NEAttacks = LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH_EAST.id][location];
        attacks |= NEAttacks;
        if ((NEAttacks & blockers) != 0) {
            int blockerLocation = Long.numberOfTrailingZeros(NEAttacks & blockers & notAtPosition);
            attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH_EAST.id][blockerLocation];
            if (blockerLocation != 64) attacks |= 1L << blockerLocation;
        }

        // southwest
        long SWAttacks = LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH_WEST.id][location];
        attacks |= SWAttacks;
        if ((SWAttacks & blockers) != 0) {
            int blockerLocation = 63-Long.numberOfLeadingZeros(SWAttacks & blockers & notAtPosition);
            if (blockerLocation>=0) {
                attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH_WEST.id][blockerLocation];
                attacks |= 1L << blockerLocation;
            }
        }

        // southeast
        long SEAttacks = LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH_EAST.id][location];
        attacks |= SEAttacks;
        if ((SEAttacks & blockers) != 0) {
            int blockerLocation = 63-Long.numberOfLeadingZeros(SEAttacks & blockers & notAtPosition);
            if (blockerLocation>=0) {
                attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH_EAST.id][blockerLocation];
                attacks |= 1L << blockerLocation;
            }
        }

        return attacks;
    }

    private long getRookMoves(Square position) {
        long positionBitmask = position.toBitmask();
        int location = Long.numberOfTrailingZeros(positionBitmask);
        long notAtPosition = ~positionBitmask;

        long attacks = 0L;
        long blockers = occupied();
        // north
        long north = LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH.id][location];
        attacks |= north;
        if ((north & blockers) != 0) {
            int blockerLocation = Long.numberOfTrailingZeros(north & blockers & notAtPosition);
            attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH.id][blockerLocation];
            if (blockerLocation != 64) attacks |= 1L << blockerLocation;
        }

        // east
        long east = LookUpTables.instance.moveMasks[LookUpTables.Direction.EAST.id][location];
        attacks |= east;
        if ((east & blockers) != 0) {
            int blockerLocation = Long.numberOfTrailingZeros(east & blockers & notAtPosition);
            attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.EAST.id][blockerLocation];
            if (blockerLocation != 64) attacks |= 1L << blockerLocation;
        }

        // south
        long south = LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH.id][location];
        attacks |= south;
        if ((south & blockers) != 0) {
            int blockerLocation = 63-Long.numberOfLeadingZeros(south & blockers & notAtPosition);
            if (blockerLocation > 0) {
                attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH.id][blockerLocation];
                attacks |= 1L << blockerLocation;
            }
        }

        // west
        long west = LookUpTables.instance.moveMasks[LookUpTables.Direction.WEST.id][location];
        attacks |= west;
        if ((west & blockers) != 0) {
            int blockerLocation = 63-Long.numberOfLeadingZeros(west & blockers & notAtPosition);
            if (blockerLocation > 0) {
                attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.WEST.id][blockerLocation];
                attacks |= 1L << blockerLocation;
            }
        }

        return attacks;
    }

    private long getQueenMoves(Square position) {
        return getBishopMoves(position) | getRookMoves(position);
    }

    private long getKingMoves(Square position) {
        long start = position.toBitmask();
        long east = moveEast(start);
        long west = moveWest(start);
        long north = moveNorth(start);
        long south = moveSouth(start);
        return  east | west | north | south | moveNorth(east) | moveNorth(west) | moveSouth(east) | moveSouth(west);
    }

    private long getKnightMoves(Square position) {
        long start = position.toBitmask();
        return moveEast(moveEast(moveNorth(start))) | moveWest(moveWest(moveNorth(start))) |
                moveEast(moveEast(moveSouth(start))) | moveWest(moveWest(moveSouth(start))) |
                moveNorth(moveNorth(moveEast(start))) | moveNorth(moveNorth(moveWest(start))) |
                moveSouth(moveSouth(moveEast(start))) | moveSouth(moveSouth(moveWest(start)));
    }

    public ArrayList<Move> generatePieceMoves(Piece piece, long illegalSquares) {
        ArrayList<Move> moves = new ArrayList<>();
        long bitboard = getBitmaskForPiece(piece);

        int index = 0;
        while (bitboard != 0) {
            if ((bitboard & 1L) == 1L) {
                // there is a piece here at index
                Square from = LookUpTables.instance.squares[index];
                long toBitboard = getMoves(piece, from) & ~illegalSquares;
                int toIndex = 0;
                while (toBitboard != 0) {
                    if ((toBitboard & 1L) == 1L) {
                        moves.add(LookUpTables.instance.moves[index][toIndex]);
                    }
                    toIndex++;
                    toBitboard >>>= 1;
                }
            }
            index++;
            bitboard >>>= 1;
        }
        return moves;
    }

    public long generateAttackedSquares(Piece piece, long illegalSquares) {
        long attacks = 0L;

        long bitboard = getBitmaskForPiece(piece);
        int index = 0;

        while (bitboard != 0) {
            if ((bitboard & 1L) == 1L) {
                // there is a piece here at index
                Square from = LookUpTables.instance.squares[index];
                attacks |= getMoves(piece, from) & ~illegalSquares;
            }
            index++;
            bitboard >>>= 1;
        }

        return attacks;
    }

    public ArrayList<Move> generateMovesWhite() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(generatePieceMoves(Piece.WhitePawn, occupiedWhite()));
        moves.addAll(generatePieceMoves(Piece.WhiteKnight, occupiedWhite()));
        moves.addAll(generatePieceMoves(Piece.WhiteBishop, occupiedWhite()));
        moves.addAll(generatePieceMoves(Piece.WhiteRook, occupiedWhite()));
        moves.addAll(generatePieceMoves(Piece.WhiteQueen, occupiedWhite()));
        moves.addAll(generatePieceMoves(Piece.WhiteKing, occupiedWhite()));
        return moves;
    }

    public ArrayList<Move> generateMovesBlack() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(generatePieceMoves(Piece.BlackPawn, occupiedBlack()));
        moves.addAll(generatePieceMoves(Piece.BlackKnight, occupiedBlack()));
        moves.addAll(generatePieceMoves(Piece.BlackBishop, occupiedBlack()));
        moves.addAll(generatePieceMoves(Piece.BlackRook, occupiedBlack()));
        moves.addAll(generatePieceMoves(Piece.BlackQueen, occupiedBlack()));
        moves.addAll(generatePieceMoves(Piece.BlackKing, occupiedBlack()));
        return moves;
    }

    public long blackAttackingSquares() {
        long attacks = 0L;
        attacks |= generateAttackedSquares(Piece.BlackPawn, occupiedBlack());
        attacks |= generateAttackedSquares(Piece.BlackKnight, occupiedBlack());
        attacks |= generateAttackedSquares(Piece.BlackBishop, occupiedBlack());
        attacks |= generateAttackedSquares(Piece.BlackRook, occupiedBlack());
        attacks |= generateAttackedSquares(Piece.BlackQueen, occupiedBlack());
        attacks |= generateAttackedSquares(Piece.BlackKing, occupiedBlack());
        return attacks;
    }

    public long whiteAttackingSquares() {
        long attacks = 0L;
        attacks |= generateAttackedSquares(Piece.WhitePawn, occupiedWhite());
        attacks |= generateAttackedSquares(Piece.WhiteKnight, occupiedWhite());
        attacks |= generateAttackedSquares(Piece.WhiteBishop, occupiedWhite());
        attacks |= generateAttackedSquares(Piece.WhiteRook, occupiedWhite());
        attacks |= generateAttackedSquares(Piece.WhiteQueen, occupiedWhite());
        attacks |= generateAttackedSquares(Piece.WhiteKing, occupiedWhite());
        return attacks;
    }

    public boolean whiteKingInCheck() {
        return (blackAttackingSquares() & whiteKing) != 0L;
    }

    public boolean blackKingInCheck() {
        return (whiteAttackingSquares() & blackKing) != 0L;
    }

    public int queenDelta() {
        int numWhiteQueens = Long.bitCount(whiteQueens) - Long.bitCount(blackQueens);
        return numWhiteQueens;
    }

    public int rookDelta() {
        int numWhiteRooks = Long.bitCount(whiteRooks) - Long.bitCount(blackRooks);
        return numWhiteRooks;
    }

    public int bishopDelta() {
        int numWhiteBishops = Long.bitCount(whiteBishops) - Long.bitCount(blackBishops);
        return numWhiteBishops;
    }

    public int knightDelta() {
        int numWhiteKnights = Long.bitCount(whiteKnights) - Long.bitCount(blackKnights);
        return numWhiteKnights;
    }

    public int pawnDelta() {
        int numWhitePawns = Long.bitCount(whitePawns) - Long.bitCount(blackPawns);
        return numWhitePawns;
    }

    public static Board loadFromFen(String fen) {
        Board board = new Board();
        board.clearBoard();
        String boardPosition = fen.split(" ")[0];
        int rank = 7;
        for (String line : boardPosition.split("/")) {
            int file = 0;
            for (int i=0;i<line.length();i++) {
                char piece = line.charAt(i);
                if (Character.isDigit(piece)) {
                    file += Character.getNumericValue(piece);
                } else {
                    Piece pieceType = Piece.fromChar(piece);
                    System.out.println(file + " rank: " + rank + " file: " + file + " value: " + piece);
                    Square square = LookUpTables.instance.squares[rank * 8 + file];
                    board.setPiece(square, pieceType);
                    file++;
                }
            }
            rank--;
        }
        return board;
    }
}

package Game;

import java.util.ArrayList;

public class Board {
    /*
     We store the board as a bitboard, which is a 64-bit integer (long) that has a 1 where there is a
     piece and 0 where there is not. We use bitboards here for performance reasons.

     The least significant bit of the long is the bottom left square, and the most significant bit is the top right
     square. For example, the integer 00100100 00000000 00000000 00000000 00000000 00000000 00000000 01000010 translates to:

     8 | 0 0 1 0 0 1 0 0
     7 | 0 0 0 0 0 0 0 0
     6 | 0 0 0 0 0 0 0 0
     5 | 0 0 0 0 0 0 0 0
     4 | 0 0 0 0 0 0 0 0
     3 | 0 0 0 0 0 0 0 0
     2 | 0 0 0 0 0 0 0 0
     1 | 0 1 0 0 0 0 1 0
     -------------------
         a b c d e f g h

     Because we can only store a 1 or 0 for each spot, we need to use 12 longs to store the board, one for each
     piece and color.
    */
    private long blackPawns;
    private long blackRooks;
    private long blackKnights;
    public long blackBishops;
    private long blackQueens;
    private long blackKing;

    private long whitePawns;
    private long whiteRooks;
    public long whiteKnights;
    private long whiteBishops;
    private long whiteQueens;
    private long whiteKing;

    public Board(){
        // Initialize the board to the starting position
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

        // Pawns
        for (int i = 0; i < 8; i++) {
            setPiece(new Square(1, i), Piece.WhitePawn);
            setPiece(new Square(6, i), Piece.BlackPawn);
        }
    }

    /**
     * Convert the board to a string. Capital letters are black pieces, lowercase letters are white pieces.
     *
     * @return the board as a string
     */
    public String toString() {
        StringBuilder result = new StringBuilder();

        // Row-major ordering
        for (int i = 7; i >= 0; i--) {
            result.append(i+1);
            result.append(" | ");
            for (int j = 0; j < 8; j++) {
                Piece p = getPiece(i, j);
                if (p == null) {
                    // No piece
                    result.append("-");
                } else {
                    result.append(p.toChar());
                }
                result.append(" ");
            }
            result.append("\n");
        }

        // Add rank indicators to bottom
        result.append("-------------------\n");
        result.append("   ");
        for (int i = 0; i < 8; i++) {
            result.append(" ");
            result.append((char)('a'+i));
        }
        return result.toString();
    }

    /**
     * Get the piece at a square
     * @param square piece position
     * @return the piece at the square
     */
    public Piece getPiece(Square square) {
        return bitmaskToPiece(square.toBitmask());
    }

    /**
     * Get the piece at a square
     * @param rank rank of the square
     * @param file file of the square
     * @return the piece at the square
     */
    public Piece getPiece(int rank, int file) {
        return bitmaskToPiece(1L << (rank * 8 + file));
    }

    /**
     * Make a move
     * @param move move to make
     */
    public void makeMove(Move move) {
        Piece piece = getPiece(move.from);
        setPiece(move.from, null);
        setPiece(move.to, piece);
    }

    /**
     * Reset the board
     */
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

    /**
     * Get the corresponding bitmask for a piece
     * @param piece to get bitmask for
     * @return bitmask for the piece
     */
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

    /**
     * Set a piece at a square
     * @param square square to set piece at
     * @param piece piece to set
     */
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

    /**
     * Get the piece at a bitmask
     * @param bitmask bitmask to get piece at
     * @return the piece at the bitmask
     */
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

    /**
     * Get the bitmask of all occupied squares
     */
    public long occupied() {
        return blackRooks | blackKnights | blackBishops | blackQueens | blackKing | blackPawns |
                whiteRooks | whiteKnights | whiteBishops | whiteQueens | whiteKing | whitePawns;
    }

    /**
     * Get the bitmask of all squares occupied by white
     */
    public long occupiedWhite() {
        return whiteRooks | whiteKnights | whiteBishops | whiteQueens | whiteKing | whitePawns;
    }

    /**
     * Get the bitmask of all squares occupied by black
     */
    public long occupiedBlack() {
        return blackRooks | blackKnights | blackBishops | blackQueens | blackKing | blackPawns;
    }

    /**
     * Convert a bitboard to a string representation (for debugging purposes)
     */
    public static String bitboardToString(long bitboard) {
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

    /**
     * Shift all bits in a bitboard one square north (up). Does not wrap.
     */
    public static long moveNorth(long bitboard) {
        return bitboard << 8;
    }

    /**
     * Shift all bits in a bitboard one square south (down). Does not wrap.
     */
    public static long moveSouth(long bitboard) {
        return bitboard >>> 8;
    }

    /**
     * Shift all bits in a bitboard one square east (right). Does not wrap.
     */
    public static long moveEast(long bitboard) {
        return (bitboard & ~File.H) << 1;
    }

    /**
     * Shift all bits in a bitboard one square west (left). Does not wrap.
     */
    public static long moveWest(long bitboard) {
        return (bitboard & ~File.A) >>> 1;
    }

    /**
     * Get the legal moves for a piece at a square as a bitmask.
     */
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

    /**
     * Get legal moves for a pawn at a square as a bitmask.
     */
    private long getPawnMoves(Piece.Color color, Square position) {
        long positionBitmask = position.toBitmask();
        long moves;
        long captures;
        if (color.equals(Piece.Color.WHITE))  {
            // White pawn
            boolean canMoveTwo = (positionBitmask & Rank.TWO) != 0; // Make sure pawn is on second rank
            moves = moveNorth(positionBitmask) & ~occupied(); // Pawn can move up if square is not occupied
            if (canMoveTwo) {
                moves |= moveNorth(moves) & ~occupied(); // Pawn can move two squares up if both squares are not occupied
            }
            // Capture only if there is a piece occupied there of the opposite color
            captures = moveNorth(moveEast(positionBitmask)) & occupiedBlack();
            captures |= moveNorth(moveWest(positionBitmask)) & occupiedBlack();
        } else {
            // Black pawn
            boolean canMoveTwo = (positionBitmask & Rank.SEVEN) != 0; // Make sure pawn is on seventh rank
            moves = moveSouth(positionBitmask) & ~occupied();
            if (canMoveTwo) {
                moves |= moveSouth(moves) & ~occupied();
            }
            // Capture only if there is a piece occupied there of the opposite color
            captures = moveSouth(moveEast(positionBitmask)) & occupiedWhite();
            captures |= moveSouth(moveWest(positionBitmask)) & occupiedWhite();
        }
        moves &= ~occupied();  // Remove moves that are blocked by other pieces
        moves |= captures;  // Add captures

        return moves;
    }

    /**
     * Get legal moves for a bishop at a square as a bitmask.
     */
    private long getBishopMoves(Square position) {
        /*
         First, we get all possible moves in all directions, then we remove moves that are blocked by other pieces.
         We have a lookup table that stores the possible moves of a bishop for each square in each direction. When
         we encounter a piece, we remove all moves in that direction after that piece (also using the lookup table).

         For example, consider the board position:
         8 | - - - - - - - -
         7 | - - - - - - - -
         6 | - - - - - - - -
         5 | - - - - - - - -
         4 | - - - - b - - -
         3 | - - - - - - - -
         2 | - - P - - - - -
         1 | - - - - - - - -
         -------------------
             a b c d e f g h

         For the southwest direction, we build an attacks bitboard like this:
         8 | 0 0 0 0 0 0 0 0      8 | 0 0 0 0 0 0 0 0     8 | 0 0 0 0 0 0 0 0
         7 | 0 0 0 0 0 0 0 0      7 | 0 0 0 0 0 0 0 0     7 | 0 0 0 0 0 0 0 0
         6 | 0 0 0 0 0 0 0 0      6 | 0 0 0 0 0 0 0 0     6 | 0 0 0 0 0 0 0 0
         5 | 0 0 0 0 0 0 0 0      5 | 0 0 0 0 0 0 0 0     5 | 0 0 0 0 0 0 0 0
         4 | 0 0 0 0 1 0 0 0  &~  4 | 0 0 0 0 0 0 0 0  =  4 | 0 0 0 0 1 0 0 0
         3 | 0 0 0 1 0 0 0 0      3 | 0 0 0 0 0 0 0 0     3 | 0 0 0 1 0 0 0 0
         2 | 0 0 1 0 0 0 0 0      2 | 0 0 1 0 0 0 0 0     2 | 0 0 0 0 0 0 0 0
         1 | 0 1 0 0 0 0 0 0      1 | 0 1 0 0 0 0 0 0     1 | 0 0 0 0 0 0 0 0
         -------------------      -------------------     -------------------
             a b c d e f g h          a b c d e f g h         a b c d e f g h
        */
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
            // Remove all moves after the blocker
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


    /**
     * Get legal moves for a rook at a square as a bitmask.
     */
    private long getRookMoves(Square position) {
        // Internally, this works the same way as getBishopMoves but the directions are NESW instead of diagonals
        long positionBitmask = position.toBitmask();
        int location = Long.numberOfTrailingZeros(positionBitmask); // Position of the piece
        long notAtPosition = ~positionBitmask;

        long attacks = 0L;
        long blockers = occupied();

        // North
        long north = LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH.id][location];
        attacks |= north;
        if ((north & blockers) != 0) {
            int blockerLocation = Long.numberOfTrailingZeros(north & blockers & notAtPosition);
            attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.NORTH.id][blockerLocation];
            if (blockerLocation != 64) attacks |= 1L << blockerLocation;
        }

        // East
        long east = LookUpTables.instance.moveMasks[LookUpTables.Direction.EAST.id][location];
        attacks |= east;
        if ((east & blockers) != 0) {
            int blockerLocation = Long.numberOfTrailingZeros(east & blockers & notAtPosition);
            attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.EAST.id][blockerLocation];
            if (blockerLocation != 64) attacks |= 1L << blockerLocation;
        }

        // South
        long south = LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH.id][location];
        attacks |= south;
        if ((south & blockers) != 0) {
            int blockerLocation = 63-Long.numberOfLeadingZeros(south & blockers & notAtPosition);
            if (blockerLocation > 0) {
                attacks &= ~LookUpTables.instance.moveMasks[LookUpTables.Direction.SOUTH.id][blockerLocation];
                attacks |= 1L << blockerLocation;
            }
        }

        // West
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

    /**
     * Get legal moves for a queen at a square as a bitmask.
     */
    private long getQueenMoves(Square position) {
        // Queen moves are the union of bishop and rook moves
        return getBishopMoves(position) | getRookMoves(position);
    }

    /**
     * Get legal moves for a king at a square as a bitmask.
     */
    private long getKingMoves(Square position) {
        long start = position.toBitmask();
        long east = moveEast(start);
        long west = moveWest(start);
        long north = moveNorth(start);
        long south = moveSouth(start);

        // All eight directions
        return  east | west | north | south | moveNorth(east) | moveNorth(west) | moveSouth(east) | moveSouth(west);
    }

    /**
     * Get legal moves for a knight at a square as a bitmask.
     */
    private long getKnightMoves(Square position) {
        long start = position.toBitmask();
        // All eight directions
        return moveEast(moveEast(moveNorth(start))) | moveWest(moveWest(moveNorth(start))) |
                moveEast(moveEast(moveSouth(start))) | moveWest(moveWest(moveSouth(start))) |
                moveNorth(moveNorth(moveEast(start))) | moveNorth(moveNorth(moveWest(start))) |
                moveSouth(moveSouth(moveEast(start))) | moveSouth(moveSouth(moveWest(start)));
    }

    /**
     * Get all the legal moves for a piece and color. Ignores checks. Can remove illegal moves by passing in a bitmask.
     *
     * @param piece type of piece to consider
     * @param illegalSquares bitmask of illegal squares to move to (ones for illegal squares)
     * @return list of legal moves
     */
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

    /**
     * Get all attacked squares for a piece and color. Ignores checks. Can remove illegal squares by passing in a bitmask.
     *
     * @param piece
     * @param illegalSquares
     * @return list of attacked squares
     */
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

    /**
     * Get all legal moves for white
     *
     * @return list of legal moves
     */
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

    /**
     * Get all legal moves for black
     *
     * @return list of legal moves
     */
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

    /**
     * Get all attacked squares for white
     *
     * @return bitboard of attacked squares
     */
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

    /**
     * Get all attacked squares for black
     *
     * @return bitboard of attacked squares
     */
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

    public boolean whiteKingInCheck() {
        return (blackAttackingSquares() & whiteKing) != 0L;
    }

    public boolean blackKingInCheck() {
        return (whiteAttackingSquares() & blackKing) != 0L;
    }

    /**
     * Difference between the number of queens white has and the number of queens black has.
     */
    public int queenDelta() {
        int numWhiteQueens = Long.bitCount(whiteQueens) - Long.bitCount(blackQueens);
        return numWhiteQueens;
    }

    /**
     * Difference between the number of rooks white has and the number of rooks black has.
     */
    public int rookDelta() {
        int numWhiteRooks = Long.bitCount(whiteRooks) - Long.bitCount(blackRooks);
        return numWhiteRooks;
    }

    /**
     * Difference between the number of bishops white has and the number of bishops black has.
     */
    public int bishopDelta() {
        int numWhiteBishops = Long.bitCount(whiteBishops) - Long.bitCount(blackBishops);
        return numWhiteBishops;
    }

    /**
     * Difference between the number of knights white has and the number of knights black has.
     */
    public int knightDelta() {
        int numWhiteKnights = Long.bitCount(whiteKnights) - Long.bitCount(blackKnights);
        return numWhiteKnights;
    }

    /**
     * Difference between the number of pawns white has and the number of pawns black has.
     */
    public int pawnDelta() {
        int numWhitePawns = Long.bitCount(whitePawns) - Long.bitCount(blackPawns);
        return numWhitePawns;
    }

    /**
     * Load a board position from an FEN string. Useful for importing positions from other chess software.
     *
     * Example fen a string for the starting position:
     * rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
     * |______________ Position _________________| | |_________ Game state (castling, en passant, number of moves)
     *                                             |_________ Turn (w or b)
     *
     */
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

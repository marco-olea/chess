package chess;

import java.util.List;
import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Pawn;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * A singleton class for representing a standard 8x8 chess board.
 * 
 * @author Marco Olea
 * @version 1.0
 * @see chess.pieces.Piece
 */
public class Board {

    /**
     * A square on a chess board.
     * 
     * @author Marco Olea
     * @version 1.0
     */
    private class Square {

        private Piece piece;

        /**
         * Determines if this square contains a piece or not.
         * 
         * @return <code>true</code> if this square is empty
         */
        public boolean isEmpty() {
            return piece == null;
        }

        /**
         * Gets the piece on this square.
         * 
         * @return a reference to the piece or <code>null</code> if this square is empty
         */
        public Piece getPiece() {
            return piece;
        }

        /**
         * Sets this square's piece.
         * 
         * @param piece the piece to assign this square; can be <code>null</code>
         */
        public void setPiece(Piece piece) {
            this.piece = piece;
        }

    }

    private static final Board INSTANCE = new Board();

    private Square[][] squares;
    private List<Piece> liveWhitePieces;
    private List<Piece> liveBlackPieces;
    private boolean currentPlayerHasLegalMoves;
    private Color turn;
    private King whiteKing;
    private King blackKing;

    /**
     * Creates a board populated with the initial sixteen white and sixteen black pieces.
     */
    private Board() {
        squares = new Square[8][8];
        liveWhitePieces = new java.util.LinkedList<>();
        liveBlackPieces = new java.util.LinkedList<>();
        currentPlayerHasLegalMoves = true;
        turn = Color.WHITE;                                 
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square();
                if (i >= 2 && i <= 5) {
                    continue;
                }
                Color color = (i == 6 || i == 7) ? Color.WHITE : Color.BLACK;
                Piece piece = (i == 1 || i == 6) ? new Pawn(color) : switch (j) {
                    case 0, 7 -> new Rook(color);
                    case 1, 6 -> new Knight(color);
                    case 2, 5 -> new Bishop(color);
                    case 3    -> new Queen(color);
                    default   -> {
                        King king = new King(color);
                        if (color == Color.WHITE) {
                            whiteKing = king;
                        } else {
                            blackKing = king;
                        }
                        yield king;
                    }
                };
                setPiece(piece, new Position(i, j));
                (color == Color.WHITE ? liveWhitePieces : liveBlackPieces).add(piece);
            }
        }
    }

    /**
     * Returns this runtime's <code>Board</code> instance.
     * 
     * @return a unique <code>Board</code>
     */
    public static Board getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the color of the current player.
     * 
     * @return the color of the current player
     */
    public Color getTurn() {
        return turn;
    }

    /**
     * Gets the piece on the board in the specified position.
     * 
     * @param position the position of the required piece
     * @return a reference to the piece or <code>null</code> if the square at the
     *         specified position is empty
     */
    public Piece getPiece(Position position) {
        return squares[position.getRank()][position.getFile()].getPiece();
    }

    /**
     * Moves a piece on the board from one position to another.
     * Does nothing if one of the following statements is true: <br> 
     * <ul>
     *   <li><code>piece</code> is <code>null</code>,</li>
     *   <li>the specified piece does not belong to the current player,</li>
     *   <li>the current player is attempting to move a piece to the same position it's already in, or
     *   <li>the move the current player is attempting to make is illegal.
     * </ul>
     * Returns <code>true</code> if the move was made.
     * 
     * @param piece the piece to be moved; can be <code>null</code>
     * @param move  the position of the square to move to the piece to
     * @return <code>true</code> if a piece changed its position on the board
     */
    public boolean movePiece(Piece piece, Position move) {
        if (piece == null 
                || turn != piece.getColor()
                || piece.getPosition().equals(move)
                || !piece.isLegalMove(move)) {
            return false;
        }

        Piece capturedPiece = getPiece(move);

        // En passant
        if (piece.getClass() == Pawn.class && capturedPiece == null) {
            Position pos = new Position(piece.getPosition().getRank(), move.getFile());
            capturedPiece = getPiece(pos);
            setPiece(null, pos);
        }

        // Castle
        int side = piece.getPosition().getFile() - move.getFile();
        if (piece.getClass() == King.class && (int) Math.abs(side) == 2) {
            Piece rook = getPiece(new Position(piece.getPosition().getRank(), side > 0 ? 0 : 7));
            Position newPos = new Position(piece.getPosition().getRank(), side > 0 ? 3 : 5);
            setPiece(null, rook.getPosition());
            setPiece(rook, newPos);
            History.getInstance().submitMove(rook, newPos);
        }

        setPiece(null, piece.getPosition());
        setPiece(piece, move);
        (turn == Color.WHITE ? liveBlackPieces : liveWhitePieces).remove(capturedPiece);
        turn = turn == Color.WHITE ? Color.BLACK : Color.WHITE;
        determineIfCurrentPlayerHasLegalMoves();
        History.getInstance().submitMove(piece, move);
        return true;
    }

    /**
     * Determines if the current player is in check.
     * 
     * @return <code>true</code> if the current player is in check
     */
    public boolean isInCheck() {
        List<Piece> opponentsPieces = turn == Color.WHITE ? liveBlackPieces : liveWhitePieces;
        King king = turn == Color.WHITE ? whiteKing : blackKing;
        for (Piece piece: opponentsPieces) {
            if (piece.getClass() == Pawn.class
                    && ((Pawn) piece).getAttackingMoves().contains(king.getPosition())) {
                return true;
            } else if (piece.getClass() != Pawn.class 
                    && piece.getLegalMoves().contains(king.getPosition())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if moving one of the current player's pieces to some position on the board will
     * put said player in check. The move need not be legal.
     * 
     * @param piece the piece to be moved; can be <code>null</code>
     * @param move  the position of the square to move to the piece to
     * @return <code>false</code> if the piece does not belong to the current player or if the
     *         specified move does not put the current player in check
     */
    public boolean moveCausesCheck(Piece piece, Position move) {
        if (piece == null || turn != piece.getColor()) {
            return false;
        }
        Piece capturedPiece = getPiece(move);
        Position prevPosition = piece.getPosition();
        setPiece(piece, move);
        setPiece(null, prevPosition);
        var opponentsPieces = piece.getColor() == Color.WHITE ? liveBlackPieces : liveWhitePieces;
        boolean wasRemoved = opponentsPieces.remove(capturedPiece);
        boolean causesCheck = isInCheck();
        if (wasRemoved) {
            opponentsPieces.add(capturedPiece);
        }
        setPiece(piece, prevPosition);
        setPiece(capturedPiece, move);
        return causesCheck;
    }

    /**
     * Determines if the current player's king has been checkmated.
     * 
     * @return <code>true</code> if the current player's king has been checkmated
     */
    public boolean isCheckmate() {
        return !currentPlayerHasLegalMoves && isInCheck();
    }

    /**
     * Determines if the current player has been stalemated.
     * 
     * @return <code>true</code> if the current player has been stalemated
     */
    public boolean isStalemate() {
        return !currentPlayerHasLegalMoves && !isInCheck();
    }

    /**
     * Determines if the square in the specified position contains a piece.
     * 
     * @param position the position to check
     * @return <code>true</code> if the square is empty
     */
    public boolean isSquareEmpty(Position position) {
        return squares[position.getRank()][position.getFile()].isEmpty();
    }

    /**
     * Gets the color of the piece on the specified square.
     * 
     * @param position the position of the square
     * @return the piece's color or {@link chess.Color#NONE} if the square in the specified position
     *         is empty
     */
    public Color getPieceColor(Position position) {
        return isSquareEmpty(position) ? Color.NONE : getPiece(position).getColor();
    }

    /**
     * Updates a piece's position on the board.
     * 
     * @param piece the piece to set in the specified position; can be <code>null</code>
     * @param position  the position of the square to set the piece in
     */
    private void setPiece(Piece piece, Position position) {
        if (piece != null) {
            piece.setPosition(position);
        }
        squares[position.getRank()][position.getFile()].setPiece(piece);
    }

    /**
     * Determines if the current player has any legal moves left. Used for detecting checkmate
     * and/or stalemate.
     */
    private void determineIfCurrentPlayerHasLegalMoves() {
        for (Piece piece: turn == Color.WHITE ? liveWhitePieces : liveBlackPieces) {
            if (!piece.getLegalMoves().isEmpty()) {
                currentPlayerHasLegalMoves = true;
                return;
            }
        }
        currentPlayerHasLegalMoves = false;
    }

}
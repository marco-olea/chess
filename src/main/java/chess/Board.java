package chess;

import java.util.List;
import chess.pieces.Piece;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Pawn;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * A standard 8x8 chess board.
 * 
 * The term "current player" will be used throughout this class's documentation to refer to the
 * color of the pieces whose turn it is to act next.
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

    private Square[][] squares;
    private List<Piece> liveWhitePieces;
    private List<Piece> liveBlackPieces;
    private Color turn;
    private King whiteKing;
    private King blackKing;

    /**
     * Creates a board populated with the initial sixteen white and sixteen black pieces.
     */
    public Board() {
        squares = new Square[8][8];
        liveWhitePieces = new java.util.LinkedList<>();
        liveBlackPieces = new java.util.LinkedList<>();
        turn = Color.WHITE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square();
                if (i >= 2 && i <= 5) {
                    continue;
                }
                Color color = (i == 6 || i == 7) ? Color.WHITE : Color.BLACK;
                Piece piece = (i == 1 || i == 6) ? new Pawn(this, color) : switch (j) {
                    case 0, 7 -> new Rook(this, color);
                    case 1, 6 -> new Knight(this, color);
                    case 2, 5 -> new Bishop(this, color);
                    case 3    -> new Queen(this, color);
                    default   -> {
                        King king = new King(this, color);
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
     * Sets a piece on the board in the specified position.
     * 
     * @param piece the piece to set in the specified position; can be <code>null</code>
     * @param position  the position of the square to set the piece in
     */
    public void setPiece(Piece piece, Position position) {
        if (piece != null) {
            piece.setPosition(position);
        }
        squares[position.getRank()][position.getFile()].setPiece(piece);
    }

    /**
     * Moves a piece on the board from one position to another.
     * Does nothing if: <br> 
     * <ul>
     *   <li><code>piece</code> is <code>null</code>,</li>
     *   <li><code>this.getTurn().equals(piece.getColor())</code> is <code>false</code>,</li>
     *   <li>the current player is attempting to move a piece to the same position it's already in,
     *   <li>the move the current player is attempting to make is illegal.
     * </ul>
     * 
     * @param piece the piece to be moved; can be <code>null</code>
     * @param move  the position of the square to move to the piece to
     * @return <code>true</code> if a piece changed its position on the board
     */
    public boolean movePiece(Piece piece, Position move) {
        if (piece == null 
                || !turn.equals(piece.getColor())
                || piece.getPosition().equals(move)
                || !piece.isLegalMove(move)) {
            return false;
        }
        Piece capturedPiece = getPiece(move);
        setPiece(null, piece.getPosition());
        setPiece(piece, move);
        (turn.equals(Color.WHITE) ? liveBlackPieces : liveWhitePieces).remove(capturedPiece);
        turn = turn.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        return true;
    }

    /**
     * Determines if moving a player's piece to some position on the board will put that player in
     * check.
     * 
     * @param piece the piece to be moved; can be <code>null</code>
     * @param move  the position of the square to move to the piece to
     * @return <code>true</code> if the specified move puts the player in check
     */
    public boolean moveCausesCheck(Piece piece, Position move) {
        Piece capturedPiece = getPiece(move);
        Position prevPosition = piece.getPosition();
        setPiece(piece, move);
        setPiece(null, prevPosition);
        var opponentLivePieces = piece.getColor().equals(Color.WHITE) ?
                liveBlackPieces : liveWhitePieces;
        boolean wasRemoved = opponentLivePieces.remove(capturedPiece);
        boolean causesCheck = false;
        if (isInCheck()) {
            causesCheck = true;
        }
        if (wasRemoved) {
            opponentLivePieces.add(capturedPiece);
        }
        setPiece(piece, prevPosition);
        setPiece(capturedPiece, move);
        return causesCheck;
    }

    /**
     * Determines if the current player is in check.
     * 
     * @return <code>true</code> if the current player is in check
     */
    public boolean isInCheck() {
        List<Piece> opponentLivePieces;
        King king;
        if (turn.equals(Color.WHITE)) {
            opponentLivePieces = liveBlackPieces;
            king = whiteKing;
        } else {
            opponentLivePieces = liveWhitePieces;
            king = blackKing;
        }
        for (Piece piece: opponentLivePieces) {
            if (king.isInPath(piece)) {
                return true;
            }
        }
        return false;
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
     * Gets the color of the piece on some square.
     * 
     * @param position the position of the square
     * @return the piece's color or {@link chess.Color#NONE} if the square in the specified position
     *         is empty
     */
    public Color getSquarePieceColor(Position position) {
        return isSquareEmpty(position) ? Color.NONE : getPiece(position).getColor();
    }

}
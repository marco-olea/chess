package chess;

import chess.pieces.Piece;

/**
 * A square on a chess board.
 * @author Marco Olea
 * @version 1.0
 */
public class Square {

    private Piece piece;

    /**
     * Determines if this square contains a piece or not.
     * @return <code>true</code> if this square is empty
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Gets the piece on this square.
     * @return a reference to the piece or <code>null</code> if this square is empty
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Sets this square's piece.
     * @param piece the piece to assign this square; can be <code>null</code>
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}
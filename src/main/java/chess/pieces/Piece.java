package chess.pieces;

import java.util.List;
import chess.Board;

/**
 * Represents a piece in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 * @see chess.Board
 */
public abstract class Piece {

    private Color color;
    private Position position;

    /**
     * Creates a piece of the specified color.
     * 
     * @param color the color of this piece
     */
    public Piece(Color color) {
        this.color = color;
    }

    /**
     * Returns a list of all the positions on the board that this piece can legally move to.
     * 
     * @return the legal moves for this piece
     */
    public abstract List<Position> getLegalMoves();

    /**
     * Determines if the specified move is legal for this piece.
     * 
     * @param move the move to check
     * @return <code>true</code> if this piece can legally move to the specified position
     */
    public boolean isLegalMove(Position move) {
        return getLegalMoves().contains(move);
    }

    /**
     * Returns the color of this piece.
     * 
     * @return the color of this piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the position of this piece on its board.
     * 
     * @return the position of this piece
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the new position for this piece on its board.
     * 
     * @param position the new position for this piece
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Compares the specified object with this piece for equality. Returns
     * <code>true</code> if and only if the specified object is also a piece, both
     * pieces are of the same subtype (i.e. both are pawns), both pieces are of
     * the same color, and they're both in the same position.
     * 
     * @param obj the object to be compared for equality with this piece
     * @return <code>true</code> if the specified object is equal to this piece
     * @see Object#hashCode()
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Piece other = (Piece) obj;
        return color == other.color && position.equals(other.getPosition());
    }

    /**
     * Returns "{@literal <c> <p>}", where {@literal <c>} is the color of this piece and {@literal <p>}
     * is its simple name. For example, "BLACK Rook" or "WHITE Pawn".
     *
     * @return a string representation of this piece
     */
    @Override
    public String toString() {
        return color.name() + " " + getClass().getSimpleName();
    }

    /**
     * Adds (<code>rank</code>, <code>file</code>) to the specified list of moves if both of these 
     * conditions hold:
     * <ul>
     *   <li>The square does not have a friendly piece, and</li>
     *   <li>This piece belongs to the current player and moving it to the square does not put
     *       said player in check.</li> 
     * </ul>
     * This is a utility method used by getLegalMoves() for breaking out of loops when a non-empty
     * square is reached.
     * 
     * @param rank       the rank to check
     * @param file       the file to check
     * @param legalMoves the list to add the specified position to if it is a legal move
     * @return <code>true</code> if the square in the specified position was not empty
     */
    protected boolean addMoveIfLegal(int rank, int file, List<Position> legalMoves) {
        Position move = new Position(rank, file);
        Color squareColor = Board.getInstance().getPieceColor(move);
        if (squareColor != color && !Board.getInstance().moveCausesCheck(this, move)) {
            legalMoves.add(move);
        }
        return squareColor != Color.NONE;
    }

}
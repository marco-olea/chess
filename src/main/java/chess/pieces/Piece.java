package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;
import chess.Color;

/**
 * Represents a piece in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 * @see chess.Board
 */
public abstract class Piece {

    private Board board;
    private Position position;
    private Color color;

    /**
     * Creates a piece of the specified color to be set on the specified board.
     * 
     * @param board the board this piece is on
     * @param color the color of this piece
     */
    public Piece(Board board, Color color) {
        this.board = board;
        this.color = color;
    }

    public abstract List<Position> getPaths();

    public boolean isInPath(Piece piece) {
        return piece.getPaths().contains(position);
    }

    /**
     * Returns a list of all the positions on the board that this piece can legally move to.
     * 
     * @return the legal moves for this piece
     */
    public List<Position> getLegalMoves() {
        var moves = getPaths();
        moves.removeIf(move -> {
            return board.causesCheck(position.getRank(), position.getFile(), 
                                     move.getRank(), move.getFile());
        });
        return moves;
    }

    /**
     * Determines if this piece can move to the specified rank and file.
     * 
     * @param rank the rank to check
     * @param file the file to check
     * @return <code>true</code> if this piece can legally move to the specified
     *         position
     */
    public boolean isLegalMove(int rank, int file) {
        return getLegalMoves().contains(new Position(rank, file));
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
     * @param rank the new rank for this piece
     * @param file the new file for this piece
     */
    public void setPosition(int rank, int file) {
        position = new Position(rank, file);
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
     * Returns the hash code value for this piece, which is the following
     * calculation:
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;
     * <code>java.util.Objects.hash(getClass().getSimpleName(), color)</code>
     * </p>
     * This ensures that <code>piece1.equals(piece2)</code> implies that
     * <code>piece1.hashCode()==piece2.hashCode()</code> for any two pieces,
     * <code>piece1</code> and <code>piece2</code>, as required by the general
     * contract of <code>Object.hashCode()</code>.
     * Note that this calculation also ensures that unequal concrete implementations of this class
     * (i.e. two pawns in different positions) will return the same hash code.
     * 
     * @return the hash code value for this piece
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(getClass().getSimpleName(), color);
    }

    /**
     * Returns a reference to the board that this piece is on.
     * 
     * @return the board this piece is on
     */
    protected Board getBoard() {
        return board;
    }

}
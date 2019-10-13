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
    private Color color;
    private Position position;

    /**
     * Creates a piece of the specified color to be set on the specified board.
     * 
     * @param board the board this piece is on
     * @param color the color of this piece
     */
    public Piece(Board board, Color color, Position position) {
        this.board = board;
        this.color = color;
        this.position = position;
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
     * Returns a reference to the board that this piece is on.
     * 
     * @return the board this piece is on
     */
    public Board getBoard() {
        return board;
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
     * Returns the hash code value for this piece. The value is calculated as follows:
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
     * Returns <code>true</code> if this piece does not belong to the current player or the move
     * puts said player in check.
     * 
     * @param move
     * @return <code>true</code> if the specified move puts the player in check 
     */
    protected boolean moveCausesCheck(Position move) {
        return board.moveCausesCheck(this, move);
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
        Color squareColor = board.getSquarePieceColor(move);
        if (!squareColor.equals(getColor()) && !moveCausesCheck(move)) {
            legalMoves.add(move);
        }
        return !squareColor.equals(Color.NONE);
    }

}
package chess.pieces;

import java.util.List;
import chess.Board;

public abstract class Piece {

    /**
     * A position (rank, file) on a chess board.
     * @author Marco Olea
     * @version 1.0
     */
    protected class Position {

        private int rank;
        private int file;

        /**
         * Creates a position that represents the specified rank and file.
         * @param rank the rank
         * @param file the file
         */
        public Position(int rank, int file) {
            this.rank = rank;
            this.file = file;
        }

        /**
         * Returns the rank.
         * @return the rank associated with this position
         */
        public int getRank() {
            return rank;
        }

        /**
         * Returns the file.
         * @return the file associated with this position
         */
        public int getFile() {
            return file;
        }

        /**
         * Compares the specified object with this position for equality. Returns <code>true</code> if
         * and only if the specified object is also a position and both positions refer to the same rank
         * and file.
         * @param obj the object to be compared for equality with this position
         * @return <code>true</code> if the specified object is equal to this position
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
            Position other = (Position) obj;
            return rank == other.rank && file == other.file;
        }

        /**
         * Returns the hash code value for this position, which is the following calculation:
         * <p>
         * &nbsp;&nbsp;&nbsp;&nbsp;<code>java.util.Objects.hash(rank, file)</code>
         * </p>
         * This ensures that <code>pos1.equals(pos2)</code> implies that 
         * <code>pos1.hashCode()==pos2.hashCode()</code> for any two positions, <code>pos1</code> and
         * <code>pos2</code>, as required by the general contract of <code>Object.hashCode()</code>.
         * @return the hash code value for this position
         * @see Object#equals(Object)
         * @see #equals(Object)
         */
        @Override
        public int hashCode() {
            return java.util.Objects.hash(rank, file);
        }

    }

    /** Denotes a white piece. */
    public static final int WHITE = 0;

    /** Denotes a black piece. */
    public static final int BLACK = 1;

    protected Board board;
    protected Position position;
    protected int color;

    public Piece(Board board, int color) {
        this.board = board;
        this.color = color;
    }

    public boolean isLegalMove(int rank, int file) {
        return getLegalMoves().contains(new Position(rank, file));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int rank, int file) {
        position = new Position(rank, file);
    }

    public int getColor() {
        return color;
    }

    /**
     * Compares the specified object with this piece for equality. Returns <code>true</code> if
     * and only if the specified object is also a piece, both pieces are of the same subtype
     * (i.e. both are pawns) and both pieces are of the same color.
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
        return color == other.color;
    }

    /**
     * Returns the hash code value for this piece, which is the following calculation:
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;
     * <code>java.util.Objects.hash(getClass().getSimpleName(), color)</code>
     * </p>
     * This ensures that <code>piece1.equals(piece2)</code> implies that 
     * <code>piece1.hashCode()==piece2.hashCode()</code> for any two pieces, <code>piece1</code> and
     * <code>piece2</code>, as required by the general contract of <code>Object.hashCode()</code>.
     * @return the hash code value for this piece
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(getClass().getSimpleName(), color);
    }

    protected abstract List<Position> getLegalMoves();

    protected boolean moveCapturesOrSquareHasFriendlyPiece(int rank, 
                                                           int file, 
                                                           List<Position> legalMoves) {
        var squareColor = board.getSquarePieceColor(rank, file);
        if (squareColor != color) {
            legalMoves.add(new Position(rank, file));
        }
        return squareColor != -1;
    }

}
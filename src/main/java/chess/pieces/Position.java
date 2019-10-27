package chess.pieces;

/**
 * A position (rank, file) of a piece on a chess board.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Position {

    private int rank;
    private int file;

    /**
     * Creates a position that represents the specified rank and file.
     * 
     * @param rank the rank
     * @param file the file
     */
    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    /**
     * Returns the rank.
     * 
     * @return the rank associated with this position
     */
    public int getRank() {
        return rank;
    }

    /**
     * Returns the file.
     * 
     * @return the file associated with this position
     */
    public int getFile() {
        return file;
    }

    /**
     * Compares the specified object with this position for equality. Returns
     * <code>true</code> if and only if the specified object is also a position and
     * both positions refer to the same rank and file.
     * 
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
     * Returns "(&lt;r&gt;, &lt;f&gt;)", where &lt;r&gt; is the rank and &lt;f&gt; is the file.
     *
     * @return a string representation of this position
     */
    @Override
    public String toString() {
        return "(" + rank + ", " + file + ")";
    }

}

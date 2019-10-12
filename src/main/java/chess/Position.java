package chess;

/**
 * A position (rank, file) on a chess board.
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
     * Returns the hash code value for this position, which is the following
     * calculation:
     * <p>
     * &nbsp;&nbsp;&nbsp;&nbsp;<code>java.util.Objects.hash(rank, file)</code>
     * </p>
     * This ensures that <code>pos1.equals(pos2)</code> implies that
     * <code>pos1.hashCode()==pos2.hashCode()</code> for any two positions,
     * <code>pos1</code> and <code>pos2</code>, as required by the general contract
     * of <code>Object.hashCode()</code>.
     * 
     * @return the hash code value for this position
     * @see Object#equals(Object)
     * @see #equals(Object)
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(rank, file);
    }

}

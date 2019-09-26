package chess;

public class Position {
    private int rank;
    private int file;

    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

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

    @Override
    public int hashCode() {
        return java.util.Objects.hash(rank, file);
    }
}
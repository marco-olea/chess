package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;

public class King extends Piece {

    public King(Board board, int color) {
        super(board, color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = position.getRank(), file = position.getFile(); 
        if (rank - 1 >= 0 && file - 1 >= 0
                && board.getSquarePieceColor(rank - 1, file - 1) != color) {
            positions.add(new Position(rank - 1, file - 1));
        }
        if (rank - 1 >= 0
                && board.getSquarePieceColor(rank - 1, file) != color) {
            positions.add(new Position(rank - 1, file));
        }
        if (rank - 1 >= 0 && file + 1 <= 7
                && board.getSquarePieceColor(rank - 1, file + 1) != color) {
            positions.add(new Position(rank - 1, file + 1));
        }
        if (file + 1 <= 7
                && board.getSquarePieceColor(rank, file + 1) != color) {
            positions.add(new Position(rank, file + 1));
        }
        if (rank + 1 <= 7 && file + 1 <= 7
                && board.getSquarePieceColor(rank + 1, file + 1) != color) {
            positions.add(new Position(rank + 1, file + 1));
        }
        if (rank + 1 <= 7
                && board.getSquarePieceColor(rank + 1, file) != color) {
            positions.add(new Position(rank + 1, file));
        }
        if (rank + 1 <= 7 && file - 1 >= 0
                && board.getSquarePieceColor(rank + 1, file - 1) != color) {
            positions.add(new Position(rank + 1, file - 1));
        }
        if (file - 1 >= 0
                && board.getSquarePieceColor(rank, file - 1) != color) {
            positions.add(new Position(rank, file - 1));
        }
        return positions;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        King other = (King) obj;
        return color == other.color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(color);
    }

}
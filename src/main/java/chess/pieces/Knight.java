package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;

public class Knight extends Piece {

    public Knight(Board board, int color) {
        super(board, color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = position.getRank(), file = position.getFile(); 
        if (rank - 1 >= 0 && file - 2 >= 0
                && board.getSquarePieceColor(rank - 1, file - 2) != color) {
            positions.add(new Position(rank - 1, file - 2));
        }
        if (rank - 2 >= 0 && file - 1 >= 0
                && board.getSquarePieceColor(rank - 2, file - 1) != color) {
            positions.add(new Position(rank - 2, file - 1));
        }
        if (rank - 2 >= 0 && file + 1 <= 7
                && board.getSquarePieceColor(rank - 2, file + 1) != color) {
            positions.add(new Position(rank - 2, file + 1));
        }
        if (rank - 1  >= 0 && file + 2 <= 7
                && board.getSquarePieceColor(rank - 1, file + 2) != color) {
            positions.add(new Position(rank - 1, file + 2));
        }
        if (rank + 1 <= 7 && file + 2 <= 7
                && board.getSquarePieceColor(rank + 1, file + 2) != color) {
            positions.add(new Position(rank + 1, file + 2));
        }
        if (rank + 2 <= 7 && file + 1 <= 7
                && board.getSquarePieceColor(rank + 2, file + 1) != color) {
            positions.add(new Position(rank + 2, file + 1));
        }
        if (rank + 2 <= 7 && file - 1 >= 0
                && board.getSquarePieceColor(rank + 2, file - 1) != color) {
            positions.add(new Position(rank + 2, file - 1));
        }
        if (rank + 1 <= 7 && file - 2 >= 0
                && board.getSquarePieceColor(rank + 1, file - 2) != color) {
            positions.add(new Position(rank + 1, file - 2));
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
        Knight other = (Knight) obj;
        return color == other.color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(color);
    }

}
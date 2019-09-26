package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;

public class Pawn extends Piece {
	
    public Pawn(Board board, int color) {
        super(board, color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = position.getRank(), file = position.getFile();
        if (color == WHITE) {
            if (board.isSquareEmpty(rank - 1, file)) {
                positions.add(new Position(rank - 1, file));
            }
            if (rank == 6
                    && board.isSquareEmpty(rank - 1, file)
                    && board.isSquareEmpty(rank - 2, file)) {
                positions.add(new Position(rank - 2, file));
            }
            if (file > 0 && board.getSquarePieceColor(rank - 1, file - 1) == BLACK) {
                positions.add(new Position(rank - 1, file - 1));
            }
            if (file < 7 && board.getSquarePieceColor(rank - 1, file + 1) == BLACK) {
                positions.add(new Position(rank - 1, file + 1));
            }
        } else {
            if (board.isSquareEmpty(rank + 1, file)) {
                positions.add(new Position(rank + 1, file));
            }
            if (rank == 1 
                    && board.isSquareEmpty(rank + 1, file)
                    && board.isSquareEmpty(rank + 2, file)) {
                positions.add(new Position(rank + 2, file));
            }
            if (file > 0 && board.getSquarePieceColor(rank + 1, file - 1) == WHITE) {
                positions.add(new Position(rank + 1, file - 1));
            }
            if (file < 7 && board.getSquarePieceColor(rank + 1, file + 1) == WHITE) {
                positions.add(new Position(rank + 1, file + 1));
            }
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
        Pawn other = (Pawn) obj;
        return color == other.color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(color);
    }

}
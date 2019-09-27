package chess.pieces;

import java.util.List;
import chess.Board;

public class Knight extends Piece {

    public Knight(Board board, int color) {
        super(board, color);
    }

    @Override
    protected List<Position> getLegalMoves() {
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

}
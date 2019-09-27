package chess.pieces;

import java.util.List;
import chess.Board;

/**
 * Represents a knight in chess.
 * @author Marco Olea
 * @version 1.0
 */
public class Knight extends Piece {

    /**
     * Creates a knight of the specified color to be set on the specified board.
     * @param board the board this knight is on
     * @param color the color of this knight
     */
    public Knight(Board board, int color) {
        super(board, color);
    }

    @Override
    protected List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        var board = getBoard();
        var color = getColor();
        int rank = getPosition().getRank(), file = getPosition().getFile();
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
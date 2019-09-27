package chess.pieces;

import java.util.List;
import chess.Board;

/**
 * Represents a king in chess.
 * @author Marco Olea
 * @version 1.0
 */
public class King extends Piece {

    /**
     * Creates a king of the specified color to be set on the specified board.
     * @param board the board this king is on
     * @param color the color of this king
     */
    public King(Board board, int color) {
        super(board, color);
    }

    @Override
    protected List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        var board = getBoard();
        var color = getColor();
        int rank = getPosition().getRank(), file = getPosition().getFile();
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

}
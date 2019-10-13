package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;
import chess.Color;

/**
 * Represents a knight in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Knight extends Piece {

    /**
     * Creates a knight of the specified color to be set on the specified board.
     * 
     * @param board the board this knight is on
     * @param color the color of this knight
     */
    public Knight(Board board, Color color, Position position) {
        super(board, color, position);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (rank - 1 >= 0 && file - 2 >= 0) {
            addMoveIfLegal(rank - 1, file - 2, positions);
        }
        if (rank - 2 >= 0 && file - 1 >= 0) {
            addMoveIfLegal(rank - 2, file - 1, positions);
        }
        if (rank - 2 >= 0 && file + 1 <= 7) {
            addMoveIfLegal(rank - 2, file + 1, positions);
        }
        if (rank - 1 >= 0 && file + 2 <= 7) {
            addMoveIfLegal(rank - 1, file + 2, positions);
        }
        if (rank + 1 <= 7 && file + 2 <= 7) {
            addMoveIfLegal(rank + 1, file + 2, positions);
        }
        if (rank + 2 <= 7 && file + 1 <= 7) {
            addMoveIfLegal(rank + 2, file + 1, positions);
        }
        if (rank + 2 <= 7 && file - 1 >= 0) {
            addMoveIfLegal(rank + 2, file - 1, positions);
        }
        if (rank + 1 <= 7 && file - 2 >= 0) {
            addMoveIfLegal(rank + 1, file - 2, positions);
        }
        
        return positions;
    }

}
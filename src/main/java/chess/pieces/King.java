package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;
import chess.Color;

/**
 * Represents a king in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class King extends Piece {

    /**
     * Creates a king of the specified color to be set on the specified board.
     * 
     * @param board the board this king is on
     * @param color the color of this king
     */
    public King(Board board, Color color, Position position) {
        super(board, color, position);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (rank - 1 >= 0 && file - 1 >= 0) {
            addMoveIfLegal(rank - 1, file - 1, positions);
        }
        if (rank - 1 >= 0) {
            addMoveIfLegal(rank - 1, file, positions);
        }
        if (rank - 1 >= 0 && file + 1 <= 7) {
            addMoveIfLegal(rank - 1, file + 1, positions);
        }
        if (file + 1 <= 7) {
            addMoveIfLegal(rank, file + 1, positions);
        }
        if (rank + 1 <= 7 && file + 1 <= 7) {
            addMoveIfLegal(rank + 1, file + 1, positions);
        }
        if (rank + 1 <= 7) {
            addMoveIfLegal(rank + 1, file, positions);
        }
        if (rank + 1 <= 7 && file - 1 >= 0) {
            addMoveIfLegal(rank + 1, file - 1, positions);
        }
        if (file - 1 >= 0) {
            addMoveIfLegal(rank, file - 1, positions);
        }
        
        return positions;
    }

}
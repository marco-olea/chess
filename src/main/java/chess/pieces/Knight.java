package chess.pieces;

import java.util.List;
import chess.Position;
import chess.pieces.Color;

/**
 * Represents a knight in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Knight extends Piece {

    /**
     * Creates a knight of the specified color.
     * 
     * @param color the color of this knight
     */
    public Knight(Color color) {
        super(color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (rank > 0 && file > 1) {
            addMoveIfLegal(rank - 1, file - 2, positions);
        }
        if (rank > 1 && file > 0) {
            addMoveIfLegal(rank - 2, file - 1, positions);
        }
        if (rank > 1 && file < 7) {
            addMoveIfLegal(rank - 2, file + 1, positions);
        }
        if (rank > 0 && file < 6) {
            addMoveIfLegal(rank - 1, file + 2, positions);
        }
        if (rank < 7 && file < 6) {
            addMoveIfLegal(rank + 1, file + 2, positions);
        }
        if (rank < 6 && file < 7) {
            addMoveIfLegal(rank + 2, file + 1, positions);
        }
        if (rank < 6 && file > 0) {
            addMoveIfLegal(rank + 2, file - 1, positions);
        }
        if (rank < 7 && file > 1) {
            addMoveIfLegal(rank + 1, file - 2, positions);
        }
        
        return positions;
    }

}
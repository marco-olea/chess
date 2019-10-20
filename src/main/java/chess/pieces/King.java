package chess.pieces;

import java.util.List;
import chess.Position;
import chess.pieces.Color;

/**
 * Represents a king in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class King extends Piece {

    /**
     * Creates a king of the specified color.
     * 
     * @param color the color of this king
     */
    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (rank > 0 && file > 0) {
            addMoveIfLegal(rank - 1, file - 1, positions);
        }
        if (rank > 0) {
            addMoveIfLegal(rank - 1, file, positions);
        }
        if (rank > 0 && file < 7) {
            addMoveIfLegal(rank - 1, file + 1, positions);
        }
        if (file < 7) {
            addMoveIfLegal(rank, file + 1, positions);
        }
        if (rank < 7 && file < 7) {
            addMoveIfLegal(rank + 1, file + 1, positions);
        }
        if (rank < 7) {
            addMoveIfLegal(rank + 1, file, positions);
        }
        if (rank < 7 && file > 0) {
            addMoveIfLegal(rank + 1, file - 1, positions);
        }
        if (file > 0) {
            addMoveIfLegal(rank, file - 1, positions);
        }
        
        return positions;
    }

}
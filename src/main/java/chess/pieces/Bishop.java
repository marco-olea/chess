package chess.pieces;

import java.util.List;
import chess.Position;
import chess.pieces.Color;

/**
 * Represents a bishop in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Bishop extends Piece {

    /**
     * Creates a bishop of the specified color.
     * 
     * @param color the color of this bishop
     */
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        for (int i = rank - 1, j = file - 1; i >= 0 && j >= 0; i--, j--) {
            if (addMoveIfLegal(i, j, positions)) {
                break;
            }
        }
        for (int i = rank - 1, j = file + 1; i >= 0 && j <= 7; i--, j++) {
            if (addMoveIfLegal(i, j, positions)) {
                break;
            }
        }
        for (int i = rank + 1, j = file + 1; i <= 7 && j <= 7; i++, j++) {
            if (addMoveIfLegal(i, j, positions)) {
                break;
            }
        }
        for (int i = rank + 1, j = file - 1; i <= 7 && j >= 0; i++, j--) {
            if (addMoveIfLegal(i, j, positions)) {
                break;
            }
        }
        
        return positions;
    }

}
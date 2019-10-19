package chess.pieces;

import java.util.List;
import chess.Position;
import chess.Color;

/**
 * Represents a rook in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Rook extends Piece {

    /**
     * Creates a rook of the specified color to be set on the specified board.
     * 
     * @param color the color of this rook
     */
    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        for (int i = rank - 1; i >= 0; i--) {
            if (addMoveIfLegal(i, file, positions)) {
                break;
            }
        }
        for (int j = file + 1; j <= 7; j++) {
            if (addMoveIfLegal(rank, j, positions)) {
                break;
            }
        }
        for (int i = rank + 1; i <= 7; i++) {
            if (addMoveIfLegal(i, file, positions)) {
                break;
            }
        }
        for (int j = file - 1; j >= 0; j--) {
            if (addMoveIfLegal(rank, j, positions)) {
                break;
            }
        }

        return positions;
    } 

}
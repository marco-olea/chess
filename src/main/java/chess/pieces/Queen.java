package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;
import chess.Color;

/**
 * Represents a queen in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Queen extends Piece {

    /**
     * Creates a queen of the specified color to be set on the specified board.
     * 
     * @param board the board this queen is on
     * @param color the color of this queen
     */
    public Queen(Board board, Color color, Position position) {
        super(board, color, position);
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
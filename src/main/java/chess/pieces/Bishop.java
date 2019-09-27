package chess.pieces;

import java.util.List;
import chess.Board;

public class Bishop extends Piece {

    public Bishop(Board board, int color) {
        super(board, color);
    }

    @Override
    protected List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = position.getRank(), file = position.getFile(); 
        for (int i = rank - 1, j = file - 1; i >= 0 && j >= 0; i--, j--) {
            if (moveCapturesOrSquareHasFriendlyPiece(i, j, positions)) {
                break;
            } 
        }
        for (int i = rank - 1, j = file + 1; i >= 0 && j <= 7; i--, j++) {
            if (moveCapturesOrSquareHasFriendlyPiece(i, j, positions)) {
                break;
            }
        }
        for (int i = rank + 1, j = file + 1; i <= 7 && j <= 7; i++, j++) {
            if (moveCapturesOrSquareHasFriendlyPiece(i, j, positions)) {
                break;
            }
        }
        for (int i = rank + 1, j = file - 1; i <= 7 && j >= 0; i++, j--) {
            if (moveCapturesOrSquareHasFriendlyPiece(i, j, positions)) {
                break;
            }
        }
        return positions;
    }

}
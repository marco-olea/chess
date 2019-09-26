package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;

public class Rook extends Piece {

    public Rook(Board board, int color) {
        super(board, color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = position.getRank(), file = position.getFile(); 
        for (int i = rank - 1; i >= 0; i--) {
            if (moveCapturesOrSquareHasFriendlyPiece(i, file, positions)) {
                break;
            }
        }
        for (int j = file + 1; j <= 7; j++) {
            if (moveCapturesOrSquareHasFriendlyPiece(rank, j, positions)) {
                break;
            }
        }
        for (int i = rank + 1; i <= 7; i++) {
            if (moveCapturesOrSquareHasFriendlyPiece(i, file, positions)) {
                break;
            }
        }
        for (int j = file - 1; j >= 0; j--) {
            if (moveCapturesOrSquareHasFriendlyPiece(rank, j, positions)) {
                break;
            }
        }
        return positions;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Rook other = (Rook) obj;
        return color == other.color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(color);
    }

}
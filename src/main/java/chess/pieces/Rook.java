package chess.pieces;

import java.util.List;
import chess.Board;

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
     * @param board the board this rook is on
     * @param color the color of this rook
     */
    public Rook(Board board, int color) {
        super(board, color);
    }

    @Override
    protected List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

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

    /**
     * Determines if a given position is a legal move (square is empty or contains
     * an opponent's piece).
     * 
     * @param rank       the rank to check
     * @param file       the file to check
     * @param legalMoves the list to add the specified position to if it is a legal
     *                   move
     * @return <code>true</code> if the square in the specified position was not
     *         empty
     */
    private boolean moveCapturesOrSquareHasFriendlyPiece(int rank, 
                                                         int file, 
                                                         List<Position> legalMoves) {
        var squareColor = getBoard().getSquarePieceColor(rank, file);
        if (squareColor != getColor()) {
            legalMoves.add(new Position(rank, file));
        }
        return squareColor != -1;
    }

}
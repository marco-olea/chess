package chess.pieces;

import java.util.List;
import chess.Board;

/**
 * Represents a bishop in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Bishop extends Piece {

    /**
     * Creates a bishop of the specified color to be set on the specified board.
     * 
     * @param board the board this bishop is on
     * @param color the color of this bishop
     */
    public Bishop(Board board, int color) {
        super(board, color);
    }

    @Override
    protected List<Position> getLegalMoves() {
        var positions = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

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
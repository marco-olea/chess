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
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public List<Position> getPaths() {
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
        Color squareColor = getBoard().getSquarePieceColor(rank, file);
        if (!squareColor.equals(getColor())) {
            legalMoves.add(new Position(rank, file));
        }
        return !squareColor.equals(Color.NONE);
    }

}
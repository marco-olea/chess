package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;
import chess.Color;

/**
 * Represents a pawn in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Pawn extends Piece {

    /**
     * Creates a pawn of the specified color.
     * 
     * @param color the color of this pawn
     */
    public Pawn(Color color) {
        super(color);
    }

    /**
     * Returns a list of the positions on the board that this pawn can move to to capture an 
     * opponent's piece.
     * 
     * @return a list of this pawn's offensive moves
     */
    public List<Position> getAttackingMoves() {
        var moves = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (getColor() == Color.WHITE) {
            if (file > 0 && squareIsEmpty(rank - 1, file - 1)) {
                addMoveIfLegal(rank - 1, file - 1, moves);
            }
            if (file < 7 && squareIsEmpty(rank - 1, file + 1)) {
                addMoveIfLegal(rank - 1, file + 1, moves);
            }
        } else {
            if (file > 0 && squareIsEmpty(rank + 1, file - 1)) {
                addMoveIfLegal(rank + 1, file - 1, moves);
            }
            if (file < 7 && squareIsEmpty(rank + 1, file + 1)) {
                addMoveIfLegal(rank + 1, file + 1, moves);
            }
        }

        return moves;
    }

    @Override
    public List<Position> getLegalMoves() {
        var moves = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (getColor() == Color.WHITE) {
            if (squareIsEmpty(rank - 1, file)) {
                addMoveIfLegal(rank - 1, file, moves);
            }
            if (rank == 6 && squareIsEmpty(rank - 1, file) && squareIsEmpty(rank - 2, file)) {
                addMoveIfLegal(rank - 2, file, moves);
            }
            if (file > 0 && squareHasOpponent(rank - 1, file - 1)) {
                addMoveIfLegal(rank - 1, file - 1, moves);
            }
            if (file < 7 && squareHasOpponent(rank - 1, file + 1)) {
                addMoveIfLegal(rank - 1, file + 1, moves);
            }
        } else {
            if (squareIsEmpty(rank + 1, file)) {
                addMoveIfLegal(rank + 1, file, moves);
            }
            if (rank == 1 && squareIsEmpty(rank + 1, file) && squareIsEmpty(rank + 2, file)) {
                addMoveIfLegal(rank + 2, file, moves);
            }
            if (file > 0 && squareHasOpponent(rank + 1, file - 1)) {
                addMoveIfLegal(rank + 1, file - 1, moves);
            }
            if (file < 7 && squareHasOpponent(rank + 1, file + 1)) {
                addMoveIfLegal(rank + 1, file + 1, moves);
            }
        }

        return moves;
    }

    /**
     * Determines if the square in the given position is empty.
     * 
     * @param rank the square's rank
     * @param file the square's file
     * @return <code>true</code> if the square is empty
     */
    private boolean squareIsEmpty(int rank, int file) {
        return Board.getInstance().isSquareEmpty(new Position(rank, file));
    }

    /**
     * Determines if the square in the given position has an opponent's piece.
     * 
     * @param rank the square's rank
     * @param file the square's file
     * @return <code>true</code> if the square has an opponent's piece
     */
    private boolean squareHasOpponent(int rank, int file) {
        Color opponentColor = getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
        return Board.getInstance().getPieceColor(new Position(rank, file)) == opponentColor;
    }

}
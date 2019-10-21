package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;
import chess.pieces.Color;

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
            if (file > 0 && enPassantOrSquareHasOpponent(rank - 1, file - 1)) {
                addMoveIfLegal(rank - 1, file - 1, moves);
            }
            if (file < 7 && enPassantOrSquareHasOpponent(rank - 1, file + 1)) {
                addMoveIfLegal(rank - 1, file + 1, moves);
            }
        } else {
            if (squareIsEmpty(rank + 1, file)) {
                addMoveIfLegal(rank + 1, file, moves);
            }
            if (rank == 1 && squareIsEmpty(rank + 1, file) && squareIsEmpty(rank + 2, file)) {
                addMoveIfLegal(rank + 2, file, moves);
            }
            if (file > 0 && enPassantOrSquareHasOpponent(rank + 1, file - 1)) {
                addMoveIfLegal(rank + 1, file - 1, moves);
            }
            if (file < 7 && enPassantOrSquareHasOpponent(rank + 1, file + 1)) {
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
     * Determines if the square in the given position has an opponent's piece or this pawn can
     * apply the "en passant" rule.
     * 
     * @param rank the square's rank
     * @param file the square's file
     * @return <code>true</code> if the square has an opponent's piece
     */
    private boolean enPassantOrSquareHasOpponent(int rank, int file) {
        Board board = Board.getInstance();
        Color opponentColor = getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
        if (board.getPieceColor(new Position(rank, file)) == opponentColor) {
            return true;
        }
        var history = chess.History.getInstance();
        Piece pawn = board.getPiece(new Position(getPosition().getRank(), file));
        return pawn != null && pawn.getClass() == Pawn.class
            && pawn.getColor() == opponentColor 
            && history.getLastMovedPiece() == pawn
            && history.getMoveCount(pawn) == 1;
    }

}
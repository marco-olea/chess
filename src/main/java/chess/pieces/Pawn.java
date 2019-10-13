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
     * Creates a pawn of the specified color to be set on the specified board.
     * 
     * @param board the board this pawn is on
     * @param color the color of this pawn
     */
    public Pawn(Board board, Color color, Position position) {
        super(board, color, position);
    }

    public List<Position> getAttackingMoves() {
        var moves = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (getColor().equals(Color.WHITE)) {
            if (file > 0 && squareIsEmptyAndMoveDoesNotCauseCheck(rank - 1, file - 1)) {
                moves.add(new Position(rank - 1, file - 1));
            }
            if (file < 7 && squareIsEmptyAndMoveDoesNotCauseCheck(rank - 1, file + 1)) {
                moves.add(new Position(rank - 1, file + 1));
            }
        } else {
            if (file > 0 && squareIsEmptyAndMoveDoesNotCauseCheck(rank + 1, file - 1)) {
                moves.add(new Position(rank + 1, file - 1));
            }
            if (file < 7 && squareIsEmptyAndMoveDoesNotCauseCheck(rank + 1, file + 1)) {
                moves.add(new Position(rank + 1, file + 1));
            }
        }

        return moves;
    }

    @Override
    public List<Position> getLegalMoves() {
        var moves = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (getColor().equals(Color.WHITE)) {
            if (squareIsEmptyAndMoveDoesNotCauseCheck(rank - 1, file)) {
                moves.add(new Position(rank - 1, file));
            }
            if (rank == 6 
                    && squareIsEmptyAndMoveDoesNotCauseCheck(rank - 1, file)
                    && squareIsEmptyAndMoveDoesNotCauseCheck(rank - 2, file)) {
                moves.add(new Position(rank - 2, file));
            }
            if (file > 0 && squareHasOpponentAndMoveDoesNotCauseCheck(rank - 1, file - 1)) {
                moves.add(new Position(rank - 1, file - 1));
            }
            if (file < 7 && squareHasOpponentAndMoveDoesNotCauseCheck(rank - 1, file + 1)) {
                moves.add(new Position(rank - 1, file + 1));
            }
        } else {
            if (squareIsEmptyAndMoveDoesNotCauseCheck(rank + 1, file)) {
                moves.add(new Position(rank + 1, file));
            }
            if (rank == 1 
                    && squareIsEmptyAndMoveDoesNotCauseCheck(rank + 1, file)
                    && squareIsEmptyAndMoveDoesNotCauseCheck(rank + 2, file)) {
                moves.add(new Position(rank + 2, file));
            }
            if (file > 0 && squareHasOpponentAndMoveDoesNotCauseCheck(rank + 1, file - 1)) {
                moves.add(new Position(rank + 1, file - 1));
            }
            if (file < 7 && squareHasOpponentAndMoveDoesNotCauseCheck(rank + 1, file + 1)) {
                moves.add(new Position(rank + 1, file + 1));
            }
        }

        return moves;
    }

    private boolean squareIsEmptyAndMoveDoesNotCauseCheck(int rank, int file) {
        Position move = new Position(rank, file);
        return getBoard().isSquareEmpty(move) && !moveCausesCheck(move);
    }

    private boolean squareHasOpponentAndMoveDoesNotCauseCheck(int rank, int file) {
        Position move = new Position(rank, file);
        Color opponentColor = getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
        return getBoard().getSquarePieceColor(move).equals(opponentColor) && !moveCausesCheck(move);
    }

}
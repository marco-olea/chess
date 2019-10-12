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
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public List<Position> getPaths() {
        var positions = new java.util.LinkedList<Position>();
        var board = getBoard();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (getColor().equals(Color.WHITE)) {
            if (file > 0 && board.getSquarePieceColor(rank - 1, file - 1).equals(Color.WHITE)) {
                positions.add(new Position(rank - 1, file - 1));
            }
            if (file < 7 && board.getSquarePieceColor(rank - 1, file + 1).equals(Color.WHITE)) {
                positions.add(new Position(rank - 1, file + 1));
            }
        } else {
            if (file > 0 && board.getSquarePieceColor(rank + 1, file - 1).equals(Color.BLACK)) {
                positions.add(new Position(rank + 1, file - 1));
            }
            if (file < 7 && board.getSquarePieceColor(rank + 1, file + 1).equals(Color.BLACK)) {
                positions.add(new Position(rank + 1, file + 1));
            }
        }
        
        return positions;
    }

    @Override
    public List<Position> getLegalMoves() {
        var moves = new java.util.LinkedList<Position>();
        var board = getBoard();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (getColor().equals(Color.WHITE)) {
            if (board.isSquareEmpty(rank - 1, file)) {
                moves.add(new Position(rank - 1, file));
            }
            if (rank == 6 
                    && board.isSquareEmpty(rank - 1, file) 
                    && board.isSquareEmpty(rank - 2, file)) {
                moves.add(new Position(rank - 2, file));
            }
            if (file > 0 && board.getSquarePieceColor(rank - 1, file - 1).equals(Color.BLACK)) {
                moves.add(new Position(rank - 1, file - 1));
            }
            if (file < 7 && board.getSquarePieceColor(rank - 1, file + 1).equals(Color.BLACK)) {
                moves.add(new Position(rank - 1, file + 1));
            }
        } else {
            if (board.isSquareEmpty(rank + 1, file)) {
                moves.add(new Position(rank + 1, file));
            }
            if (rank == 1 
                    && board.isSquareEmpty(rank + 1, file) 
                    && board.isSquareEmpty(rank + 2, file)) {
                moves.add(new Position(rank + 2, file));
            }
            if (file > 0 && board.getSquarePieceColor(rank + 1, file - 1).equals(Color.WHITE)) {
                moves.add(new Position(rank + 1, file - 1));
            }
            if (file < 7 && board.getSquarePieceColor(rank + 1, file + 1).equals(Color.WHITE)) {
                moves.add(new Position(rank + 1, file + 1));
            }
        }

        moves.removeIf(move -> {
            return board.causesCheck(rank, file, move.getRank(), move.getFile());
        });

        return moves;
    }

}
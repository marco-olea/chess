package chess.pieces;

import java.util.List;
import chess.Position;
import chess.pieces.Color;

/**
 * Represents a king in chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class King extends Piece {

    /**
     * Creates a king of the specified color.
     * 
     * @param color the color of this king
     */
    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> getLegalMoves() {
        var moves = new java.util.LinkedList<Position>();
        int rank = getPosition().getRank(), file = getPosition().getFile();

        if (rank > 0 && file > 0) {
            addMoveIfLegal(rank - 1, file - 1, moves);
        }
        if (rank > 0) {
            addMoveIfLegal(rank - 1, file, moves);
        }
        if (rank > 0 && file < 7) {
            addMoveIfLegal(rank - 1, file + 1, moves);
        }
        if (file < 7) {
            addMoveIfLegal(rank, file + 1, moves);
        }
        if (rank < 7 && file < 7) {
            addMoveIfLegal(rank + 1, file + 1, moves);
        }
        if (rank < 7) {
            addMoveIfLegal(rank + 1, file, moves);
        }
        if (rank < 7 && file > 0) {
            addMoveIfLegal(rank + 1, file - 1, moves);
        }
        if (file > 0) {
            addMoveIfLegal(rank, file - 1, moves);
        }

        if (canCastleKingside()) {
            moves.add(new Position(rank, file + 2));
        }
        if (canCastleQueenside()) {
            moves.add(new Position(rank, file - 2));
        }
        
        return moves;
    }

    /**
     * 
     * 
     * @return
     */
    public boolean canCastleKingside() {
        int rank = getPosition().getRank();
        Position move = new Position(rank, 6);
        Position rookPosition = new Position(rank, 7);
        Position sq1 = new Position(rank, 5);
        return canCastle(move, rookPosition, sq1, sq1);
    }

    /**
     * 
     * 
     * @return
     */
    public boolean canCastleQueenside() {
        int rank = getPosition().getRank();
        Position move = new Position(rank, 2);
        Position rookPosition = new Position(rank, 0);
        Position sq1 = new Position(rank, 3), sq2 = new Position(rank, 1);
        return canCastle(move, rookPosition, sq1, sq2);
    }

    /**
     * 
     * 
     * @param move
     * @param sq1
     * @param sq2
     * @param rookPosition
     * @return
     */
    private boolean canCastle(Position move, Position rookPosition, Position sq1, Position sq2) {
        var board = chess.Board.getInstance();
        var history = chess.History.getInstance();
        Piece rook = board.getPiece(rookPosition);
        return history.getMoveCount(this) == 0
            && rook != null 
            && rook.getClass() == Rook.class
            && rook.getColor() == getColor()
            && history.getMoveCount(rook) == 0
            && board.isSquareEmpty(sq1) 
            && board.isSquareEmpty(move)
            && board.isSquareEmpty(sq2)
            && board.getTurn() == getColor() 
            && !board.isInCheck()
            && !board.moveCausesCheck(this, sq1)
            && !board.moveCausesCheck(this, move);
    }

}
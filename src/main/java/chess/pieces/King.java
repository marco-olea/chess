package chess.pieces;

import java.util.List;

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
     * Determines if this king can castle kingside.
     * 
     * @return <code>true</code> if this king can castle kingside
     */
    public boolean canCastleKingside() {
        int rank = getPosition().getRank();
        Position sq1 = new Position(rank, 5);
        return canCastle(new Position(rank, 6), new Position(rank, 7), sq1, sq1);
    }

    /**
     * Determines if this king can castle queenside.
     * 
     * @return <code>true</code> if this king can castle queenside
     */
    public boolean canCastleQueenside() {
        int rank = getPosition().getRank();
        Position sq1 = new Position(rank, 3), sq2 = new Position(rank, 1);
        return canCastle(new Position(rank, 2), new Position(rank, 0), sq1, sq2);
    }

    /**
     * Determines if the king can castle. Returns <code>true</code> if all of the following 
     * statements are true: this king belongs to the current player, this king has never moved,
     * <code>rookPosition</code> contains a friendly rook which has never moved, the squares
     * between this king and the rook are empty, and moving this king to <code>sq1</code> or
     * <code>move</code> would not put the current player in check.
     * This is an auxiliary method for removing duplicate code for canCastleKingside() and
     * canCastleQueenside().
     * 
     * @param move         the position the king will move to
     * @param rookPosition the position of the rook that the king will castle with
     * @param sq1          the first square in between the king and the rook
     * @param sq2          the second square in between the king and the rook (for a queenside castle)
     * @return <code>true</code> if this king can legally move to <code>move</code>
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
package chess;

import java.util.Map;
import java.util.List;
import chess.pieces.Piece;

/**
 * A singleton class for maintaining a record of all the moves made in a chess game.
 * 
 * @author Marco Olea
 * @version 1.0
 * @see chess.Board
 */
public class History {

    private static final History INSTANCE = new History();

    private List<String> moveHistory;
    private Map<Piece, Integer> moveCounts;
    private Piece lastMoved;

    /**
     * Creates an empty record set.
     */
    private History() {
        moveHistory = new java.util.LinkedList<>();
        moveCounts = new java.util.IdentityHashMap<>();
    }

    /**
     * Returns this runtime's <code>History</code> instance.
     *
     * @return a unique <code>History</code>
     */
    public static History getInstance() {
        return INSTANCE;
    }

    /**
     * Records the specified move for the specified piece. Does nothing if either parameter is null.
     *
     * @param piece the piece that was moved
     * @param move  the position on the board that <code>piece</code> was moved to
     */
    public void submitMove(Piece piece, int toRank, int toFile) {
        if (piece != null) {
            moveCounts.put(piece, moveCounts.getOrDefault(piece, 0) + 1);
            lastMoved = piece;
        }
    }

    /**
     * Returns the amount of times the specified piece has moved.
     *
     * @param piece the piece to query
     * @return the move count for <code>piece</code>
     */
    public int getMoveCount(Piece piece) {
        return moveCounts.getOrDefault(piece, 0);
    }

    /**
     * Returns the last non-null piece with which a move was submitted via 
     * {@link History#submitMove(Piece, Position)}.
     *
     * @return the last moved piece; <code>null</code> if no pieces have been moved
     */
    public Piece getLastMovedPiece() {
        return lastMoved;
    }

}
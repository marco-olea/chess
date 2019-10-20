package chess;

import java.util.IdentityHashMap;
import java.util.LinkedList;
import chess.pieces.Piece;

public class History {

    private static final History INSTANCE = new History();

    private LinkedList<String> moveHistory;
    private IdentityHashMap<Piece, Integer> moveCounts;
    private Piece lastMoved;

    private History() {
        moveHistory = new LinkedList<>();
        moveCounts = new IdentityHashMap<>();
    }

    public static History getInstance() {
        return INSTANCE;
    }

    public void submitMove(Piece piece, Position move) {
        moveCounts.put(piece, moveCounts.getOrDefault(piece, 0) + 1);
        lastMoved = piece;
    }

    public int getMoveCount(Piece piece) {
        return moveCounts.getOrDefault(piece, 0);
    }

    public Piece getLastMovedPiece() {
        return lastMoved;
    }

}
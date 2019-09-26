package chess.pieces;

import java.util.List;
import chess.Board;
import chess.Position;

public abstract class Piece {

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    protected Board board;
    protected Position position;
    protected int color;

    public Piece(Board board, int color) {
        this.board = board;
        this.color = color;
    }

    public abstract List<Position> getLegalMoves();

    public boolean isLegalMove(int rank, int file) {
        return getLegalMoves().contains(new Position(rank, file));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int rank, int file) {
        position = new Position(rank, file);
    }

    public int getColor() {
        return color;
    }

    protected boolean moveCapturesOrSquareHasFriendlyPiece(int rank, 
                                                           int file, 
                                                           List<Position> legalMoves) {
        var squareColor = board.getSquarePieceColor(rank, file);
        if (squareColor != color) {
            legalMoves.add(new Position(rank, file));
        }
        return squareColor != -1;
    }

}
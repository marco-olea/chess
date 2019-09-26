package chess;

import chess.pieces.Piece;

public class Square {

    private Piece piece;

    public Square() {
        this(null);
    }

    public Square(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}
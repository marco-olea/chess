package chess;

import chess.pieces.Piece;

public class Board {

    private Square[][] squares;

    public Board() {
        squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square();
            }
        }
    }

    public void setPiece(Piece piece, int rank, int file) {
        if (piece != null) {
            piece.setPosition(rank, file);
        }
        squares[rank][file].setPiece(piece);
    }

    public Square getSquare(int rank, int file) {
        return squares[rank][file];
    }

    public boolean movePiece(int prevRank, int prevFile, int nextRank, int nextFile) {
        Square square = getSquare(prevRank, prevFile);
        if (!square.isEmpty() 
                && square.getPiece().isLegalMove(nextRank, nextFile)) {
            setPiece(square.getPiece(), nextRank, nextFile);
            setPiece(null, prevRank, prevFile);
            return true;
        }
        return false;
    }

    public boolean isSquareEmpty(int rank, int file) {
        return getSquare(rank, file).isEmpty();
    }

    public int getSquarePieceColor(int rank, int file) {
        return isSquareEmpty(rank, file) 
            ? -1 : getSquare(rank, file).getPiece().getColor();
    }

}
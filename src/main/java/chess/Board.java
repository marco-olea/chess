package chess;

import java.util.List;
import chess.pieces.Piece;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Pawn;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * A standard 8x8 chess board.
 * 
 * @author Marco Olea
 * @version 1.0
 * @see chess.pieces.Piece
 */
public class Board {

    /**
     * A square on a chess board.
     * 
     * @author Marco Olea
     * @version 1.0
     */
    private class Square {

        private Piece piece;

        /**
         * Determines if this square contains a piece or not.
         * 
         * @return <code>true</code> if this square is empty
         */
        public boolean isEmpty() {
            return piece == null;
        }

        /**
         * Gets the piece on this square.
         * 
         * @return a reference to the piece or <code>null</code> if this square is empty
         */
        public Piece getPiece() {
            return piece;
        }

        /**
         * Sets this square's piece.
         * 
         * @param piece the piece to assign this square; can be <code>null</code>
         */
        public void setPiece(Piece piece) {
            this.piece = piece;
        }

    }

    private Square[][] squares;
    private List<Piece> liveWhitePieces;
    private List<Piece> liveBlackPieces;
    private Color turn;
    private King whiteKing;
    private King blackKing;

    /**
     * Creates a board with empty squares (no pieces).
     */
    public Board() {
        squares = new Square[8][8];
        liveWhitePieces = new java.util.LinkedList<>();
        liveBlackPieces = new java.util.LinkedList<>();
        turn = Color.WHITE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square();
                if (i >= 2 && i <= 5) {
                    continue;
                }
                Color color = (i == 6 || i == 7) ? Color.WHITE : Color.BLACK;
                Piece piece = (i == 1 || i == 6) ? new Pawn(this, color) : switch (j) {
                    case 0, 7 -> new Rook(this, color);
                    case 1, 6 -> new Knight(this, color);
                    case 2, 5 -> new Bishop(this, color);
                    case 3    -> new Queen(this, color);
                    default   -> {
                        King king = new King(this, color);
                        if (color == Color.WHITE) {
                            whiteKing = king;
                        } else {
                            blackKing = king;
                        }
                        yield king;
                    }
                };
                setPiece(piece, i, j);
                (color == Color.WHITE ? liveWhitePieces : liveBlackPieces).add(piece);
            }
        }
    }

    /**
     * Returns the color of the player whose turn it is.
     * 
     * @return the color of the player whose turn it is
     */
    public Color getTurn() {
        return turn;
    }

    /**
     * Gets the piece on the board in the specified position.
     * 
     * @param rank the rank of the desired piece
     * @param file the file of the desired piece
     * @return a reference to the piece or <code>null</code> if the square at the
     *         specified position is empty
     */
    public Piece getPiece(int rank, int file) {
        return squares[rank][file].getPiece();
    }

    /**
     * Sets a piece on the board in the specified position.
     * 
     * @param piece the piece to set in the specified position; can be
     *              <code>null</code>
     * @param rank  the rank to set the piece in
     * @param file  the file to set the piece in
     */
    public void setPiece(Piece piece, int rank, int file) {
        if (piece != null) {
            piece.setPosition(rank, file);
        }
        squares[rank][file].setPiece(piece);
    }

    /**
     * Moves a piece on the board from one position to another.
     * 
     * @param prevRank the rank of the piece to be moved
     * @param prevFile the file of the piece to be moved
     * @param nextRank the rank of the square to move to the piece to
     * @param nextFile the file of the square to move to the piece to
     * @return <code>true</code> if a piece changed its position on the board
     */
    public void movePiece(int prevRank, int prevFile, int nextRank, int nextFile) {
        Square square = squares[prevRank][prevFile];
        if (!square.isEmpty() && (prevRank != nextRank || prevFile != nextFile)
                && square.getPiece().isLegalMove(nextRank, nextFile)) {
            Piece capturedPiece = getPiece(nextRank, nextFile);
            setPiece(square.getPiece(), nextRank, nextFile);
            setPiece(null, prevRank, prevFile);
            var opponentLivePieces = turn == Color.WHITE ? liveBlackPieces : liveWhitePieces;
            if (capturedPiece != null) {
                opponentLivePieces.remove(capturedPiece);
            }
            turn = turn == Color.WHITE ? Color.BLACK : Color.WHITE;
        }
    }

    /**
     * Determines if the square in the specified position contains a piece.
     * 
     * @param rank the rank to check
     * @param file the file to check
     * @return <code>true</code> if the square is empty
     */
    public boolean isSquareEmpty(int rank, int file) {
        return squares[rank][file].isEmpty();
    }

    /**
     * Gets the color of the piece on some square. Returns <code>-1</code> if the
     * specified square is empty.
     * 
     * @param rank the rank of the square
     * @param file the file of the square
     * @return the piece's color or null if the square in the specified position is
     *         empty
     * @see chess.Color#WHITE
     * @see chess.Color#BLACK
     */
    public Color getSquarePieceColor(int rank, int file) {
        return isSquareEmpty(rank, file) ? Color.NONE : squares[rank][file].getPiece().getColor();
    }

    /**
     * Determines if the player whose turn it is is in check.
     * 
     * @return <code>true</code> if it's in check
     */
    public boolean isInCheck() {
        List<Piece> opponentLivePieces;
        King king;
        if (turn.equals(Color.WHITE)) {
            opponentLivePieces = liveBlackPieces;
            king = whiteKing;
        } else {
            opponentLivePieces = liveWhitePieces;
            king = blackKing;
        }
        for (Piece piece: opponentLivePieces) {
            if (king.isInPath(piece)) {
                return true;
            }
        }
        return false;
    }

    public boolean causesCheck(int prevRank, int prevFile, int nextRank, int nextFile) {
        Piece prevPiece = getPiece(prevRank, prevFile), nextPiece = getPiece(nextRank, nextFile);
        setPiece(prevPiece, nextRank, nextFile);
        setPiece(null, prevRank, prevFile);
        var opponentLivePieces = prevPiece.getColor() == Color.WHITE ?
                liveBlackPieces : liveWhitePieces;
        int index = opponentLivePieces.indexOf(nextPiece);
        if (index != -1) {
            opponentLivePieces.remove(nextPiece);
        }
        boolean causesCheck = false;
        if (isInCheck()) {
            causesCheck = true;
        }
        if (index != -1) {
            opponentLivePieces.add(nextPiece);
        }
        setPiece(prevPiece, prevRank, prevFile);
        setPiece(nextPiece, nextRank, nextFile);
        return causesCheck;
    }

}
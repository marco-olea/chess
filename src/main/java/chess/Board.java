package chess;

import chess.pieces.Piece;
import chess.pieces.King;
import java.util.ArrayList;

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
    private int turn;
    private int initialLivePieceCount; // Used for adding all initial 32 live pieces.
    private ArrayList<Piece> liveWhitePieces;
    private ArrayList<Piece> liveBlackPieces;
    private boolean canUndo;      // Used for undoing a move.
    private Piece lastMoveCapturedPiece; // Used for undoing a move.
    private int lastMovePrevRank; // Used for undoing a move.
    private int lastMovePrevFile; // Used for undoing a move.
    private int lastMoveNextRank; // Used for undoing a move.
    private int lastMoveNextFile; // Used for undoing a move.

    /**
     * Creates a board with empty squares (no pieces).
     */
    public Board() {
        squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square();
            }
        }
        turn = Piece.WHITE;
        liveWhitePieces = new ArrayList<>();
        liveBlackPieces = new ArrayList<>();
    }

    public int getTurn() {
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
            if (initialLivePieceCount < 32) {
                (piece.getColor() == Piece.WHITE ? liveWhitePieces : liveBlackPieces).add(piece);
                initialLivePieceCount++;
            }
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
            var opponentLivePieces = turn == Piece.WHITE ? liveBlackPieces : liveWhitePieces;
            if (capturedPiece != null) {
                System.out.println(opponentLivePieces.indexOf(capturedPiece));
                opponentLivePieces.remove(opponentLivePieces.indexOf(capturedPiece));
            }
            turn = turn == Piece.WHITE ? Piece.BLACK : Piece.WHITE;
            lastMovePrevRank = prevRank;
            lastMovePrevFile = prevFile;
            lastMoveNextRank = nextRank;
            lastMoveNextFile = nextFile;
            lastMoveCapturedPiece = capturedPiece;
            canUndo = true;
        }
    }

    public void undoLastMove() {
        if (canUndo) {
            turn = turn == Piece.WHITE ? Piece.BLACK : Piece.BLACK;
            if (lastMoveCapturedPiece != null) {
                (turn == Piece.WHITE ? liveBlackPieces : liveWhitePieces).add(lastMoveCapturedPiece);
            }
            movePiece(lastMoveNextRank, lastMoveNextFile, lastMovePrevRank, lastMovePrevFile);
            setPiece(lastMoveCapturedPiece, lastMoveNextRank, lastMoveNextFile);
            canUndo = false;
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
     * @return the piece's color or -1 if the square in the specified position is
     *         empty
     * @see chess.pieces.Piece#WHITE
     * @see chess.pieces.Piece#BLACK
     */
    public int getSquarePieceColor(int rank, int file) {
        return isSquareEmpty(rank, file) ? -1 : squares[rank][file].getPiece().getColor();
    }

    public boolean isInCheck() {
        return isInCheck(turn);
    }

    public boolean isInCheck(int color) {
        var colorLivePieces = color == Piece.WHITE ? liveWhitePieces : liveBlackPieces;
        var opponentLivePieces = color == Piece.WHITE ? liveBlackPieces : liveWhitePieces;
        Piece king = colorLivePieces.get(colorLivePieces.indexOf(new King(this, color)));
        for (var piece: opponentLivePieces) {
            for (var position: piece.getAllMoves()) {
                if (position.getRank() == king.getPosition().getRank()
                        && position.getFile() == king.getPosition().getFile()) {
                    System.out.printf("%s, %s -> %s, %s",
                            position.getRank(), position.getFile(), king.getPosition().getRank(),
                            king.getPosition().getFile());
                    return true;
                }
            }
        }
        return false;
    }

    public boolean causesCheck(int prevRank, int prevFile, int nextRank, int nextFile) {
        Piece prevPiece = getPiece(prevRank, prevFile), nextPiece = getPiece(nextRank, nextFile);
        setPiece(prevPiece, nextRank, nextFile);
        setPiece(null, prevRank, prevFile);
        boolean wasRemoved = 
            (turn == Piece.WHITE ? liveBlackPieces : liveWhitePieces).remove(nextPiece);
        boolean causesCheck = false;
        if (isInCheck(prevPiece.getColor())) {
            causesCheck = true;
            if (wasRemoved) {
                (turn == Piece.WHITE ? liveBlackPieces : liveWhitePieces).add(nextPiece);
            }
        }
        setPiece(prevPiece, prevRank, prevFile);
        setPiece(nextPiece, nextRank, nextFile);
        return causesCheck;
    }

}
package app;

import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import chess.Board;
import chess.pieces.Position;
import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.Pawn;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Rook;
import chess.pieces.King;
import chess.pieces.Queen;

/**
 * Processing sketch used for playing chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Sketch extends PApplet {

    /**
     * Pop-up dialog used for announcing when the game is over.
     * 
     * @author Marco Olea
     * @version 1.0
     */
    private class Dialog extends PApplet {

        private static final String WINDOW_TITLE = "Game Over";
        private String message;

        /**
         * Creates a dialog that will display the specified message.
         *
         * @param message message to display in the pop-up window
         */
        public Dialog(String message) {
            this.message = message;
        }

        /**
         * Sets the width equal to the board size and height equal to one sixth of the board size.
         */
        @Override
        public void settings() {
            size(Sketch.this.width, Sketch.this.width / 6);
        }

        /**
         * Sets the title and turns off looping.
         */
        @Override
        public void setup() {
            surface.setTitle(Dialog.WINDOW_TITLE);
            noLoop();
        }

        /**
         * Draws the text announcing the game end.
         */
        @Override
        public void draw() {
            textAlign(CENTER, CENTER);
            textSize(height / 2);
            fill(0xFF000000);
            text(message, 0, 0, width, height);
        }

        /**
         * Prevents closing the primary window when this window is closed.
         */
        @Override
        public void exit() {
            dispose();
        }

    }

    private static final String WINDOW_TITLE                  = "Processing 3 Chess";
    private static final String CHECKMATE_MESSAGE             = "Checkmate! %s wins!";
    private static final String STALEMATE_MESSAGE             = "Stalemate! It's a draw!";
    private static final int    SQUARE_BORDER_COLOR           = 0xff000000; // ARGB
    private static final int    SQUARE_BORDER_WIDTH           = 1;
    private static final int    SELECTED_SQUARE_BORDER_COLOR  = 0xff0000ff;
    private static final int    SELECTED_SQUARE_BORDER_WIDTH  = 3;
    private static final int    POTENTIAL_SQUARE_BORDER_COLOR = 0xffff0000;
    private static final int    POTENTIAL_SQUARE_BORDER_WIDTH = 3;
    private static final int    WHITE_SQUARE_FILL             = 0xffdcdcdc;
    private static final int    BLACK_SQUARE_FILL             = 0x64000000;

    // These can only be set after displayHeight is available.
    private static int BOARD_SIZE;
    private static int SQUARE_SIZE;
    private static int IMAGE_SIZE;
    private static int SQUARE_MARGIN;

    private Board board;
    private HashMap<String, PImage> images;
    private Piece selectedPiece;
    private Position selectedPosition;
    private boolean choosingNextMove;
    private boolean gameIsOver;

    /**
     * Sets up the window, turns off looping, loads piece image files, creates board, and assigns
     * images to pieces.
     */
    @Override
    public void setup() {
        BOARD_SIZE = displayHeight * 4 / 5;
        SQUARE_SIZE = BOARD_SIZE / 8;
        IMAGE_SIZE = BOARD_SIZE / 10;
        SQUARE_MARGIN = SQUARE_SIZE / 10;

        surface.setLocation((displayWidth - BOARD_SIZE) / 2, (displayHeight - BOARD_SIZE) / 2);
        surface.setSize(BOARD_SIZE, BOARD_SIZE);
        surface.setTitle(WINDOW_TITLE);
        surface.setResizable(true);
        noLoop();

        board = Board.getInstance();
        images = new HashMap<>(12);
        put(new Pawn(Color.WHITE), "/white-pawn-50.png");
        put(new Bishop(Color.WHITE), "/white-bishop-50.png");
        put(new Knight(Color.WHITE), "/white-knight-50.png");
        put(new Rook(Color.WHITE), "/white-rook-50.png");
        put(new King(Color.WHITE), "/white-king-50.png");
        put(new Queen(Color.WHITE), "/white-queen-50.png");
        put(new Pawn(Color.BLACK), "/black-pawn-50.png");
        put(new Bishop(Color.BLACK), "/black-bishop-50.png");
        put(new Knight(Color.BLACK), "/black-knight-50.png");
        put(new Rook(Color.BLACK), "/black-rook-50.png");
        put(new King(Color.BLACK), "/black-king-50.png");
        put(new Queen(Color.BLACK), "/black-queen-50.png");
    }

    /**
     * Draws the 8x8 chess board, using special predefined colors for the selected piece and all the
     * positions it can legally move to, then draws the images for every piece on the board.
     */
    @Override
    public void draw() {
        // Clear canvas
        background(255);

        // Board
        stroke(SQUARE_BORDER_COLOR);
        strokeWeight(SQUARE_BORDER_WIDTH);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                drawSquare(i, j);
            }
        }

        // Selection
        if (choosingNextMove) {
            stroke(POTENTIAL_SQUARE_BORDER_COLOR);
            strokeWeight(POTENTIAL_SQUARE_BORDER_WIDTH);
            for (Position move: selectedPiece.getLegalMoves()) {
                drawSquare(move.getRank(), move.getFile());
            }
            stroke(SELECTED_SQUARE_BORDER_COLOR);
            strokeWeight(SELECTED_SQUARE_BORDER_WIDTH);
            drawSquare(selectedPosition.getRank(), selectedPosition.getFile());
        }

        // Pieces
        for (Piece piece: board) {
            image(images.get(piece.toString()), 
                  piece.getPosition().getFile() * SQUARE_SIZE + SQUARE_MARGIN,
                  piece.getPosition().getRank() * SQUARE_SIZE + SQUARE_MARGIN,
                  IMAGE_SIZE, IMAGE_SIZE);
        }

        // Checkmate or stalemate
        if (!gameIsOver && board.isCheckmate()) {
            String winner = board.getTurn() == Color.WHITE ? "Black" : "White";
            runSketch(platformNames, new Dialog(String.format(CHECKMATE_MESSAGE, winner)));
            gameIsOver = true;
        } else if (!gameIsOver && board.isStalemate()) {
            runSketch(platformNames, new Dialog(STALEMATE_MESSAGE));
            gameIsOver = true;
        }
    }

    /**
     * Processes an attempt to select or move a piece on the board.
     * 
     * @param event mouse event
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        // Only accept left clicks
        if (event.getButton() != LEFT) {
            return;
        }

        // If a player attempted to move the selected piece
        if (choosingNextMove) {
            board.movePiece(selectedPiece, 
                            new Position(mapMouseCoordinateToRankOrFile(event.getY()),
                                         mapMouseCoordinateToRankOrFile(event.getX())));
            choosingNextMove = false;
            selectedPiece = null;
        } else { // If a player attempted to select a piece
            selectedPosition = new Position(mapMouseCoordinateToRankOrFile(event.getY()),
                                            mapMouseCoordinateToRankOrFile(event.getX()));
            if (board.getTurn() == board.getPieceColor(selectedPosition)) {
                selectedPiece = board.getPiece(selectedPosition);
                choosingNextMove = true;
            }
        }

        redraw();
    }

    /**
     * Maps an x or y coordinate to an integer in the range [0, 7] (a rank or file).
     * 
     * @param c coordinate to map
     * @return 0, 1, 2, 3, 4, 5, 6, or 7
     */
    private int mapMouseCoordinateToRankOrFile(int c) {
        return c / (BOARD_SIZE / 8);
    }

    /**
     * Draws an appropiately-colored square on the board, of width and height equal to 
     * <code>SQUARE_SIZE</code>.
     * 
     * @param i the x-coordinate
     * @param j the y-coordinate
     */
    private void drawSquare(int i, int j) {
        fill(0xffffffff);
        rect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        fill((i + j) % 2 == 0 ? WHITE_SQUARE_FILL : BLACK_SQUARE_FILL);
        rect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Puts the entry <code>piece.toString()=loadImage(path)</code> into the <code>images</code>
     * HashMap.
     *
     * @param piece the piece to put
     * @param path  the path for the image to load
     */
    private void put(Piece piece, String path) {
        images.put(piece.toString(), loadImage(getClass().getResource(path).getPath()));
    }

}
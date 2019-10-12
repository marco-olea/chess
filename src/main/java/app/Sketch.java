package app;

import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import chess.Board;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * Processing sketch used for playing chess.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Sketch extends PApplet {

    private static final String WINDOW_TITLE                  = "Chess";
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
    private HashMap<Piece, PImage> images;
    private Piece selectedPiece;
    private int selectedRank;
    private int selectedFile;
    private boolean choosingNextMove;

    /**
     * Sets up the window, turns off looping, loads piece image files, creates board, and assigns
     * pieces to their initial positions.
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
        noLoop();

        board = new Board();
        images = new HashMap<>(12);

        PImage wPawnImg = loadImage(getClass().getResource("/white-pawn-50.png").getPath());
        PImage wBishopImg = loadImage(getClass().getResource("/white-bishop-50.png").getPath());
        PImage wKnightImg = loadImage(getClass().getResource("/white-knight-50.png").getPath());
        PImage wRookImg = loadImage(getClass().getResource("/white-rook-50.png").getPath());
        PImage wKingImg = loadImage(getClass().getResource("/white-king-50.png").getPath());
        PImage wQueenImg = loadImage(getClass().getResource("/white-queen-50.png").getPath());
        PImage bPawnImg = loadImage(getClass().getResource("/black-pawn-50.png").getPath());
        PImage bBishopImg = loadImage(getClass().getResource("/black-bishop-50.png").getPath());
        PImage bKnightImg = loadImage(getClass().getResource("/black-knight-50.png").getPath());
        PImage bRookImg = loadImage(getClass().getResource("/black-rook-50.png").getPath());
        PImage bKingImg = loadImage(getClass().getResource("/black-king-50.png").getPath());
        PImage bQueenImg = loadImage(getClass().getResource("/black-queen-50.png").getPath());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i >= 2 && i <= 5) {
                    continue;
                }
                Piece piece;
                PImage image;
                int color = (i == 6 || i == 7) ? Piece.WHITE : Piece.BLACK;
                switch (j) {
                case 0, 7 -> {
                    piece = new Rook(board, color);
                    image = color == Piece.WHITE ? wRookImg : bRookImg;
                }
                case 1, 6 -> {
                    piece = new Knight(board, color);
                    image = color == Piece.WHITE ? wKnightImg : bKnightImg;
                }
                case 2, 5 -> {
                    piece = new Bishop(board, color);
                    image = color == Piece.WHITE ? wBishopImg : bBishopImg;
                }
                case 3 -> {
                    piece = new Queen(board, color);
                    image = color == Piece.WHITE ? wQueenImg : bQueenImg;
                }
                default -> {
                    piece = new King(board, color);
                    image = color == Piece.WHITE ? wKingImg : bKingImg;
                }
                }
                if (i == 1 || i == 6) {
                    piece = new Pawn(board, color);
                    image = color == Piece.WHITE ? wPawnImg : bPawnImg;
                }
                images.put(piece, image);
                board.setPiece(piece, i, j);
            }
        }
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (selectedPiece != null && selectedPiece.isLegalMove(i, j)) {
                    stroke(POTENTIAL_SQUARE_BORDER_COLOR);
                    strokeWeight(POTENTIAL_SQUARE_BORDER_WIDTH);
                } else {
                    stroke(SQUARE_BORDER_COLOR);
                    strokeWeight(SQUARE_BORDER_WIDTH);
                }
                fill((i + j) % 2 == 0 ? WHITE_SQUARE_FILL : BLACK_SQUARE_FILL);
                rect(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }

        // Selection
        if (choosingNextMove) {
            stroke(SELECTED_SQUARE_BORDER_COLOR);
            strokeWeight(SELECTED_SQUARE_BORDER_WIDTH);
            fill((selectedRank + selectedFile) % 2 == 0 ? WHITE_SQUARE_FILL : BLACK_SQUARE_FILL);
            rect(selectedFile * SQUARE_SIZE, selectedRank * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }

        // Pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!board.isSquareEmpty(i, j)) {
                    image(images.get(board.getPiece(i, j)), 
                          j * SQUARE_SIZE + SQUARE_MARGIN, i * SQUARE_SIZE + SQUARE_MARGIN,
                          IMAGE_SIZE, IMAGE_SIZE);
                }
            }
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
            board.movePiece(selectedRank,
                            selectedFile,
                            mapMouseCoordinateToRankOrFile(event.getY()),
                            mapMouseCoordinateToRankOrFile(event.getX()));
            choosingNextMove = false;
            selectedPiece = null;
        } else { // If a player attempted to select a piece
            selectedRank = mapMouseCoordinateToRankOrFile(event.getY());
            selectedFile = mapMouseCoordinateToRankOrFile(event.getX());
            if (!board.isSquareEmpty(selectedRank, selectedFile)
                    && board.getTurn() == board.getSquarePieceColor(selectedRank, selectedFile)) {
                selectedPiece = board.getPiece(selectedRank, selectedFile);
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

}
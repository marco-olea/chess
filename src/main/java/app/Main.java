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
 * Chess application using the Processing 3 framework.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Main extends PApplet {

    // Non-static because can't use color() or displayHeight in static context.
    private final String WINDOW_TITLE                   = "Chess";
    private final int    BOARD_SIZE                     = 720;
    private final int    SQUARE_SIZE                    = BOARD_SIZE / 8;
    private final int    SQUARE_MARGIN                  = SQUARE_SIZE / 10;
    private final int    SQUARE_BORDER_COLOR            = color(0, 0, 0); // ARGB
    private final int    SQUARE_BORDER_WIDTH            = 1;
    private final int    SELECTED_SQUARE_BORDER_COLOR   = color(0, 0, 255);
    private final int    SELECTED_SQUARE_BORDER_WIDTH   = 3;
    private final int    POTENTIAL_SQUARE_BORDER_COLOR  = color(255, 0, 0);
    private final int    POTENTIAL_SQUARE_BORDER_WIDTH  = 3;
    private final int    WHITE_SQUARE_FILL              = color(220, 220, 220);
    private final int    BLACK_SQUARE_FILL              = color(0, 100);
    private final int    IMAGE_SIZE                     = BOARD_SIZE / 10;

    private Board board;
    private HashMap<Piece, PImage> images;
    private Piece selectedPiece;
    private int selectedRank;
    private int selectedFile;
    private int turn;
    private boolean choosingNextMove;

    /**
     * Starts this application.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        PApplet.main(Main.class.getName());
    }

    /**
     * Required no-arg constructor.
     */
    public Main() {}

    /**
     * Sets the window dimensions and turns off looping.
     */
    @Override
    public void settings() {
        size(BOARD_SIZE, BOARD_SIZE);
        noLoop();
    }

    /**
     * Load piece image files, create board, and assign pieces to their initial
     * positions.
     */
    @Override
    public void setup() {
        surface.setTitle(WINDOW_TITLE);

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

        turn = Piece.WHITE;
        board = new Board();
        images = new HashMap<>(12);

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
     * Draws the 8x8 chess board, using special predefined colors for the selected
     * piece and all the positions it can legally move to, then draws the images for
     * every piece on the board.
     */
    @Override
    public void draw() { // TODO: Draw special tiles after board.
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
            if (board.movePiece(selectedRank, 
                                selectedFile,
                                mapMouseCoordinateToRankOrFile(event.getY()),
                                mapMouseCoordinateToRankOrFile(event.getX()))) {
                turn = turn == Piece.WHITE ? Piece.BLACK : Piece.WHITE;
            }
            choosingNextMove = false;
            selectedPiece = null;
        } else { // If a player attempted to select a piece
            selectedRank = mapMouseCoordinateToRankOrFile(event.getY());
            selectedFile = mapMouseCoordinateToRankOrFile(event.getX());
            if (!board.isSquareEmpty(selectedRank, selectedFile)
                    && board.getSquarePieceColor(selectedRank, selectedFile) == turn) {
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
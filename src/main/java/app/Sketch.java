package app;

import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import chess.Board;
import chess.Color;
import chess.Position;
import chess.pieces.Piece;

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
    private Position selectedPosition;
    private boolean choosingNextMove;
    private boolean gameIsOver;

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

        images = new HashMap<>(12);
        board = Board.getInstance();

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
                Piece piece = board.getPiece(new Position(i, j));
                Color color = piece.getColor();
                PImage image = switch (j) {
                    case 0, 7 -> color == Color.WHITE ? wRookImg : bRookImg;
                    case 1, 6 -> color == Color.WHITE ? wKnightImg : bKnightImg;
                    case 2, 5 -> color == Color.WHITE ? wBishopImg : bBishopImg;
                    case 3 -> color == Color.WHITE ? wQueenImg : bQueenImg;
                    default -> color == Color.WHITE ? wKingImg : bKingImg;
                };
                if (i == 1 || i == 6) {
                    image = color == Color.WHITE ? wPawnImg : bPawnImg;
                }
                images.put(piece, image);
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position position = new Position(i, j);
                if (!board.isSquareEmpty(position)) {
                    image(images.get(board.getPiece(position)), 
                          j * SQUARE_SIZE + SQUARE_MARGIN, i * SQUARE_SIZE + SQUARE_MARGIN,
                          IMAGE_SIZE, IMAGE_SIZE);
                }
            }
        }

        // Checkmate or stalemate
        if (!gameIsOver && board.isCheckmate()) {
            String winner = board.getTurn() == Color.WHITE ? " Black " : " White ";
            main(Dialog.class, new String[]{width + "", "Checkmate!" + winner + "wins!"});
            gameIsOver = true;
        } else if (!gameIsOver && board.isStalemate()) {
            main(Dialog.class, new String[]{width + "", "Stalemate! It's a draw!"});
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

}
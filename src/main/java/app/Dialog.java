package app;

import processing.core.PApplet;

/**
 * Pop-up dialog used for announcing when the game is over.
 * 
 * @author Marco Olea
 * @version 1.0
 */
public class Dialog extends PApplet {

    /**
     * Sets the width equal to the board size and height equal to one sixth of the board size.
     */
    @Override
    public void settings() {
        int boardSize = Integer.parseInt(args[0]);
        size(boardSize, boardSize / 6);
    }

    /**
     * Sets the title and turns off looping.
     */
    @Override
    public void setup() {
        surface.setTitle("Game over");
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
        text(args[1], 0, 0, width, height);
    }

    /**
     * Prevents closing the primary window when this window is closed.
     */
    @Override
    public void exit() {
        dispose();
    }

}
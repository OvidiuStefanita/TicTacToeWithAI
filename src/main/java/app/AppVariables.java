package app;

import app.graphics.AppCanvas;

import javax.swing.*;

public class AppVariables {

    // named-constants for the game board
    public static final int ROWS = 3;
    public static final int COLUMNS = 3;

    // named-constants of the various dimensions used for graphics drawing
    public static final int CELL_SIZE = 100; // cell width and height (square)
    public static final int CANVAS_WIDTH = CELL_SIZE * COLUMNS;  // the drawing canvas
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 8;                   // grid-line's width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // grid-line's half-width

    // symbols are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
    public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width


    public static AppGameState currentState;  // the current game state
    public static AppBoardSeed currentPlayer;  // the current player

    public static AppBoardSeed[][] board; // game board of ROWS-by-COLUMNS

    public static JLabel statusBar;  // status Bar
    public static AppCanvas canvas; // drawing canvas (JPanel) for the game board
}

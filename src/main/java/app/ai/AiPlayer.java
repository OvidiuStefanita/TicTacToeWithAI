package app.ai;

import app.AppBoardSeed;
import app.AppVariables;
import app.ai.components.AiBoard;
import app.ai.components.AiCell;


/* abstract superclass for implementing AI players */
public abstract class AiPlayer {

    // rows and columns for the AI board
    int ROWS = AppVariables.ROWS;
    int COLS = AppVariables.COLUMNS;

    AiCell[][] AiCells; // the AI_board's rows by columns in an array of cells
    AppBoardSeed ai_seed;    // computer's designated playing symbol
    AppBoardSeed opp_seed;   // opponent's (human) designated playing symbol

    /* constructor which receives a version of the current board in order to populate the cells */
    AiPlayer(AiBoard AIBoard) {

        AiCells = AIBoard.aiCells;
    }

    /* set or change the seed SYMBOL used by computer and opponent */
    public void setSeed(AppBoardSeed seed) {
        this.ai_seed = seed;
        opp_seed = (ai_seed == AppBoardSeed.SYMBOL_X) ? AppBoardSeed.SYMBOL_O : AppBoardSeed.SYMBOL_X;
    }

    /* abstract method to get next AI move. Returns an array of two values representing the coordinates for the next move */
    abstract int[] move();  // to be implemented by subclasses
}
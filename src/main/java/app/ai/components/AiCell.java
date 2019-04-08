package app.ai.components;

import app.AppBoardSeed;


/* the class models each individual cell of the game board for the AI.*/
public class AiCell {

    public AppBoardSeed content; // content of the cell for the player seed SYMBOL

    // row and column of the cell
    private int row, col;

    /* constructor to initialize the cell */
    AiCell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();  // clear content
    }

    /* clear the cell content by setting it to EMPTY */
    private void clear() {

        content = AppBoardSeed.EMPTY;
    }


}

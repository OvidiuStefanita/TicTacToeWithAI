package app.ai.components;


/* the class models the game-board for the AI */
public class AiBoard {

    // named-constants for the AI board
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;

    // a board composed of rows and columns for the AI to use
    public AiCell[][] aiCells;

    /* constructor to initialize the AI game board */
    public AiBoard() {
        aiCells = new AiCell[ROWS][COLUMNS];  // allocating the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLUMNS; ++col) {
                aiCells[row][col] = new AiCell(row, col); // allocate element of the array
            }
        }
    }
}

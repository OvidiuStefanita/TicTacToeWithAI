package app;

import app.graphics.AppFrame;


public class AppMechanics {

    /* initialize the game-board contents and the status */
    public static void initGame() {
        for (int row = 0; row < AppVariables.ROWS; ++row) {
            for (int col = 0; col < AppVariables.COLUMNS; ++col) {
                AppVariables.board[row][col] = AppBoardSeed.EMPTY; // set the cells of the game to empty
                AppFrame.aiBoard.aiCells[row][col].content = AppBoardSeed.EMPTY; // set the AI cells to empty
            }
        }

        AppVariables.currentState = AppGameState.PLAYING; // ready to play
        AppVariables.currentPlayer = AppBoardSeed.SYMBOL_X; // human player plays first
    }

    /*  updating the game through checking if the game is over (there is a winner or a draw) */
    public static void updateGame(AppBoardSeed theAppBoardState, int rowSelected, int colSelected) {
        if (hasWon(theAppBoardState, rowSelected, colSelected)) {  // check for win
            AppVariables.currentState = (theAppBoardState == AppBoardSeed.SYMBOL_X) ? AppGameState.PLAYER_X_WON : AppGameState.PLAYER_O_WON;
        } else if (isDraw()) {  // check for draw
            AppVariables.currentState = AppGameState.DRAW;
        }
        // otherwise, no change to current state (still PLAYING).
    }


    private static boolean isDraw() {
        for (int row = 0; row < AppVariables.ROWS; ++row) {
            for (int col = 0; col < AppVariables.COLUMNS; ++col) {
                if (AppVariables.board[row][col] == AppBoardSeed.EMPTY) {
                    return false; // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no more empty cell, it's a draw
    }


     /* returns true if the current player has won, after placing at (rowSelected, colSelected */
    private static boolean hasWon(AppBoardSeed theAppBoardState, int rowSelected, int colSelected) {
        return ((AppVariables.board[rowSelected][0] == theAppBoardState  // 3-in-the-row
                && AppVariables.board[rowSelected][1] == theAppBoardState
                && AppVariables.board[rowSelected][2] == theAppBoardState)

                || (AppVariables.board[0][colSelected] == theAppBoardState      // 3-in-the-column
                && AppVariables.board[1][colSelected] == theAppBoardState
                && AppVariables.board[2][colSelected] == theAppBoardState)

                || (rowSelected == colSelected            // 3-in-the-diagonal
                && AppVariables.board[0][0] == theAppBoardState
                && AppVariables.board[1][1] == theAppBoardState
                && AppVariables.board[2][2] == theAppBoardState)

                || (rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
                && AppVariables.board[0][2] == theAppBoardState
                && AppVariables.board[1][1] == theAppBoardState
                && AppVariables.board[2][0] == theAppBoardState));
    }
}

package app.graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import app.AppBoardSeed;
import app.AppMechanics;
import app.AppVariables;
import app.ai.AiMinimaxAlgorithm;
import app.ai.components.AiBoard;
import app.AppGameState;


public class AppFrame extends JFrame {

    // variables for selecting of rows and columns for the player move
    private int colSelected;
    private int rowSelected;

    // creating a static board object for the AI
    public static AiBoard aiBoard = new AiBoard();

    // instantiating the AI and feeding the board
    private AiMinimaxAlgorithm aiMM = new AiMinimaxAlgorithm(aiBoard);


    /* constructor to setup the game and the GUI components */
    public AppFrame() {
        AppVariables.canvas = new AppCanvas(); // construct a drawing canvas (a JPanel)
        AppVariables.canvas.setPreferredSize(new Dimension(AppVariables.CANVAS_WIDTH, AppVariables.CANVAS_HEIGHT));

        // adding the mouse listener
        mouseListener();

        // setup the status bar (JLabel) to display status message
        AppVariables.statusBar = new JLabel("  ");
        AppVariables.statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        AppVariables.statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        //setting up the container
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(AppVariables.canvas, BorderLayout.CENTER);
        cp.add(AppVariables.statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();  // pack all the components in this JFrame
        setTitle("Tic Tac Toe");
        setVisible(true);  // show this JFrame

        AppVariables.board = new AppBoardSeed[AppVariables.ROWS][AppVariables.COLUMNS]; // allocate array
        AppMechanics.initGame(); // initialize the game board contents and game variables
    }


    private void mouseListener() {

        AppVariables.canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler

                int mouseX = e.getX();
                int mouseY = e.getY();

                // get the row and column clicked
                rowSelected = mouseY / AppVariables.CELL_SIZE;
                colSelected = mouseX / AppVariables.CELL_SIZE;

                if (AppVariables.currentState == AppGameState.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < AppVariables.ROWS && colSelected >= 0
                            && colSelected < AppVariables.COLUMNS && AppVariables.board[rowSelected][colSelected] == AppBoardSeed.EMPTY) {

                        // human player makes the first move
                        AppVariables.board[rowSelected][colSelected] = AppVariables.currentPlayer;

                        AppMechanics.updateGame(AppVariables.currentPlayer, rowSelected, colSelected); // update game state
                        aiBoard.aiCells[rowSelected][colSelected].content = AppBoardSeed.SYMBOL_X; // update Ai board

                        // switch player to AI
                        AppVariables.currentPlayer = (AppVariables.currentPlayer == AppBoardSeed.SYMBOL_X) ? AppBoardSeed.SYMBOL_O : AppBoardSeed.SYMBOL_X;

                        //call the AI player
                        callAiPlayerMinimax();

                        // change player back to human
                        AppVariables.currentPlayer = (AppVariables.currentPlayer == AppBoardSeed.SYMBOL_X) ? AppBoardSeed.SYMBOL_O : AppBoardSeed.SYMBOL_X;

                    }
                } else {       // game is over
                    AppMechanics.initGame(); // restart the game
                }

                // refresh the drawing canvas
                repaint();  // call-back on paintComponent()

            }
        });
    }


    private void callAiPlayerMinimax() {

        //set AI playing symbol
        aiMM.setSeed(AppBoardSeed.SYMBOL_O);

        // getting and setting the AI move to the corresponding rows and columns
        int[] ai_move = aiMM.move();
        if (ai_move != null) {
            rowSelected = ai_move[0];
            colSelected = ai_move[1];

            //to be executed only if the game is still in progress
            if (AppVariables.currentState == AppGameState.PLAYING) {
                AppVariables.board[rowSelected][colSelected] = AppVariables.currentPlayer; // AI player makes the move
                AppMechanics.updateGame(AppVariables.currentPlayer, rowSelected, colSelected); //game is updated
                aiBoard.aiCells[rowSelected][colSelected].content = AppBoardSeed.SYMBOL_O; //populating the AI board with the AI seed
            }

        }
    }


}

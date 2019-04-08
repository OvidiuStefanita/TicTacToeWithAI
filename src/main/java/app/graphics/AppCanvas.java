package app.graphics;

import app.AppBoardSeed;
import app.AppVariables;
import app.AppGameState;

import javax.swing.*;
import java.awt.*;


public class AppCanvas extends JPanel {

    @Override
    public void paintComponent(Graphics g) {  // invoked via repaint()
        super.paintComponent(g);    // fill the background
        setBackground(Color.GRAY); // set the background color

        // draw the grid-lines and set their color
        g.setColor(Color.LIGHT_GRAY);
        for (int row = 1; row < AppVariables.ROWS; ++row) {
            g.fillRoundRect(0, AppVariables.CELL_SIZE * row - AppVariables.GRID_WIDTH_HALF,
                    AppVariables.CANVAS_WIDTH - 1, AppVariables.GRID_WIDTH, AppVariables.GRID_WIDTH, AppVariables.GRID_WIDTH);
        }
        for (int col = 1; col < AppVariables.COLUMNS; ++col) {
            g.fillRoundRect(AppVariables.CELL_SIZE * col - AppVariables.GRID_WIDTH_HALF, 0,
                    AppVariables.GRID_WIDTH, AppVariables.CANVAS_HEIGHT - 1, AppVariables.GRID_WIDTH, AppVariables.GRID_WIDTH);
        }


        // Graphics2D allows to set the pen's stroke
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(AppVariables.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (int row = 0; row < AppVariables.ROWS; ++row) {
            for (int col = 0; col < AppVariables.COLUMNS; ++col) {
                int x1 = col * AppVariables.CELL_SIZE + AppVariables.CELL_PADDING;
                int y1 = row * AppVariables.CELL_SIZE + AppVariables.CELL_PADDING;

                // drawing and setting the color for player with board seed SYMBOL_X
                if (AppVariables.board[row][col] == AppBoardSeed.SYMBOL_X) {
                    g2d.setColor(Color.RED);
                    int x2 = (col + 1) * AppVariables.CELL_SIZE - AppVariables.CELL_PADDING;
                    int y2 = (row + 1) * AppVariables.CELL_SIZE - AppVariables.CELL_PADDING;
                    g2d.drawLine(x1, y1, x2, y2);
                    g2d.drawLine(x2, y1, x1, y2);

                    // drawing and setting the color for player with board seed SYMBOL_O
                } else if (AppVariables.board[row][col] == AppBoardSeed.SYMBOL_O) {
                    g2d.setColor(Color.GREEN);
                    g2d.drawOval(x1, y1, AppVariables.SYMBOL_SIZE, AppVariables.SYMBOL_SIZE);
                }
            }
        }

        // print the status-bar message
        if (AppVariables.currentState == AppGameState.PLAYING) {
            AppVariables.statusBar.setForeground(Color.BLACK);
            if (AppVariables.currentPlayer == AppBoardSeed.SYMBOL_X) {
                AppVariables.statusBar.setText("X's Turn");
            } else {
                AppVariables.statusBar.setText("O's Turn");
            }
        } else if (AppVariables.currentState == AppGameState.DRAW) {
            AppVariables.statusBar.setForeground(Color.RED);
            AppVariables.statusBar.setText("It's a Draw! Click to play again.");
        } else if (AppVariables.currentState == AppGameState.PLAYER_X_WON) {
            AppVariables.statusBar.setForeground(Color.RED);
            AppVariables.statusBar.setText("'X' Won! Click to play again.");
        } else if (AppVariables.currentState == AppGameState.PLAYER_O_WON) {
            AppVariables.statusBar.setForeground(Color.RED);
            AppVariables.statusBar.setText("'O' Won! Click to play again.");
        }
    }
}

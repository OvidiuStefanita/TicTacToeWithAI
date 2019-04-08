package app.ai;

import app.AppBoardSeed;
import app.ai.components.AiBoard;

import java.util.*;


/** AiPlayer using the Minimax algorithm */

public class AiMinimaxAlgorithm extends AiPlayer {

    /** Constructor with the given game AiBoard */
    public AiMinimaxAlgorithm(AiBoard AIBoard) {
        super(AIBoard);
    }

    /** Get next best move for computer. Returns int[2] of {row, col} */
    @Override
    public int[] move() {
        int[] result = minimax(2, ai_seed); // depth, max turn
        return new int[] {result[1], result[2]};   // row, col
    }

    /** Recursive minimax at level of depth for either maximizing or minimizing player.
     Returns int[3] of {score, row, col}  */
    private int[] minimax(int depth, AppBoardSeed player) {

        // Generate possible next moves in a List of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // ai_seed is maximizing; while opp_seed is minimizing
        int bestScore = (player == ai_seed) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {

            // Game is over or depth reached, evaluate score
            bestScore = evaluate();

        } else {
            for (int[] move : nextMoves) {

                // Try this move for the current "player"
                AiCells[move[0]][move[1]].content = player;

                if (player == ai_seed) {  // ai_seed is maximizing player
                    currentScore = minimax(depth - 1, opp_seed)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // opp_seed is minimizing player
                    currentScore = minimax(depth - 1, ai_seed)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // Undo move
                AiCells[move[0]][move[1]].content = AppBoardSeed.EMPTY;
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    /** Find all valid next moves.
     Return List of moves in int[2] of {row, col} or empty list if game is over */
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List

        // If game is over as in no next move
        if (hasWon(ai_seed) || hasWon(opp_seed)) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (AiCells[row][col].content == AppBoardSeed.EMPTY) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }

    /** The heuristic evaluation function for the current board
     Return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
     -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
     0 otherwise   */
    private int evaluate() {
        int score = 0;

        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    /** The heuristic evaluation function for the given line of 3 aiCells
     Return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer.
     -100, -10, -1 for 3-, 2-, 1-in-a-line for opponent.
     0 otherwise */
    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (AiCells[row1][col1].content == ai_seed) {
            score = 1;
        } else if (AiCells[row1][col1].content == opp_seed) {
            score = -1;
        }

        // Second cell
        if (AiCells[row2][col2].content == ai_seed) {
            if (score == 1) {   // cell1 is ai_seed
                score = 10;
            } else if (score == -1) {  // cell1 is opp_seed
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (AiCells[row2][col2].content == opp_seed) {
            if (score == -1) { // cell 1 is opp_seed
                score = -10;
            } else if (score == 1) { // cell 1 is ai_seed
                return 0;
            } else {  // cell 1 is empty
                score = -1;
            }
        }

        // Third cell
        if (AiCells[row3][col3].content == ai_seed) {
            if (score > 0) {  // cell 1 and/or cell 2 is ai_seed
                score *= 10;
            } else if (score < 0) {  // cell 1 and/or cell 2 is opp_seed
                return 0;
            } else {  // cell 1 and cell 2 are empty
                score = 1;
            }
        } else if (AiCells[row3][col3].content == opp_seed) {
            if (score < 0) {  // cell 1 and/or cell 2 is opp_seed
                score *= 10;
            } else if (score > 1) {  // cell 1 and/or cell 2 is ai_seed
                return 0;
            } else {  // cell 1 and cell 2 are empty
                score = -1;
            }
        }
        return score;
    }

    private int[] winningPatterns = {
            0b111000000, 0b000111000, 0b000000111, // rows
            0b100100100, 0b010010010, 0b001001001, // cols
            0b100010001, 0b001010100               // diagonals
    };

    /** Returns true if thePlayer wins */
    private boolean hasWon(AppBoardSeed thePlayer) {
        int pattern = 0b000000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (AiCells[row][col].content == thePlayer) {
                    pattern |= (1 << (row * COLS + col));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) return true;
        }
        return false;
    }
}

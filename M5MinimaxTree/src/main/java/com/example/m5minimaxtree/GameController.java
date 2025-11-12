package com.example.m5minimaxtree;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class GameController {

    @FXML private GridPane gridPane;
    @FXML private Label statusLabel;

    private Button[][] buttons = new Button[3][3];
    private char[][] board;
    private MinimaxAI ai = new MinimaxAI();

    @FXML
    private void initialize() {
        board = TicTacToeGame.createEmptyBoard();
        setupBoard();
        statusLabel.setText("Your turn (X)");
    }

    private void setupBoard() {
        Font font = new Font(36);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button btn = new Button(" ");
                btn.setMinSize(80, 80);
                btn.setFont(font);
                final int rr = r, cc = c;
                btn.setOnAction(e -> handleHumanMove(rr, cc));
                buttons[r][c] = btn;
                gridPane.add(btn, c, r);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
    }

    private void handleHumanMove(int r, int c) {
        if (board[r][c] != TicTacToeGame.EMPTY) return;
        if (isGameOver()) return;

        board[r][c] = TicTacToeGame.HUMAN;
        buttons[r][c].setText(String.valueOf(TicTacToeGame.HUMAN));

        if (isGameOver()) {
            endGame();
            return;
        }


        System.out.println("\n=== AI is thinking... ===");


        Move aiMove = ai.findBestMove(board);


        TreeNode testRoot = new TreeNode(board, null);
        ai.buildGameTree(testRoot, TicTacToeGame.AI, 0);
        ai.printTreeStats(testRoot);

        System.out.println("AI chose: " + aiMove);
        System.out.println("=========================\n");

        if (aiMove != null) {
            board[aiMove.row][aiMove.col] = TicTacToeGame.AI;
            buttons[aiMove.row][aiMove.col].setText(String.valueOf(TicTacToeGame.AI));
        }

        if (isGameOver()) {
            endGame();
        } else {
            statusLabel.setText("Your turn (X)");
        }
    }

    @FXML
    private void onResetClicked() {
        board = TicTacToeGame.createEmptyBoard();
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                buttons[r][c].setText(" ");
        statusLabel.setText("Your turn (X)");
    }

    private boolean isGameOver() {
        return TicTacToeGame.evaluateTerminalScore(board) != null;
    }

    private void endGame() {
        char winner = TicTacToeGame.checkWinner(board);
        if (winner == TicTacToeGame.AI)
            statusLabel.setText("AI (O) wins!");
        else if (winner == TicTacToeGame.HUMAN)
            statusLabel.setText("You (X) win!");
        else
            statusLabel.setText("Draw!");
    }
}
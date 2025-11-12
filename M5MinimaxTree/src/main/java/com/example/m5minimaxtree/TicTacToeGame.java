package com.example.m5minimaxtree;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeGame {
    public static final char EMPTY = ' ';
    public static final char HUMAN = 'X';
    public static final char AI = 'O';


    public static char[][] createEmptyBoard() {
        char[][] b = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                b[i][j] = EMPTY;
        return b;
    }


    public static List<Move> availableMoves(char[][] board) {
        List<Move> moves = new ArrayList<>();
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == EMPTY)
                    moves.add(new Move(r, c));
        return moves;
    }

    public static char[][] applyMove(char[][] board, Move move, char player) {
        char[][] nb = new char[3][3];
        for (int i = 0; i < 3; i++) System.arraycopy(board[i], 0, nb[i], 0, 3);
        nb[move.row][move.col] = player;
        return nb;
    }

    public static Integer evaluateTerminalScore(char[][] board) {
        // rows, cols, diags
        char winner = checkWinner(board);
        if (winner == AI) return +10;
        if (winner == HUMAN) return -10;
        if (isBoardFull(board)) return 0; // draw
        return null; // game not over
    }

    public static char checkWinner(char[][] b) {
        // rows and cols
        for (int i = 0; i < 3; i++) {
            if (b[i][0] != EMPTY && b[i][0] == b[i][1] && b[i][1] == b[i][2]) return b[i][0];
            if (b[0][i] != EMPTY && b[0][i] == b[1][i] && b[1][i] == b[2][i]) return b[0][i];
        }
        // diags
        if (b[0][0] != EMPTY && b[0][0] == b[1][1] && b[1][1] == b[2][2]) return b[0][0];
        if (b[0][2] != EMPTY && b[0][2] == b[1][1] && b[1][1] == b[2][0]) return b[0][2];
        return EMPTY;
    }

    public static boolean isBoardFull(char[][] board) {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == EMPTY) return false;
        return true;
    }
}



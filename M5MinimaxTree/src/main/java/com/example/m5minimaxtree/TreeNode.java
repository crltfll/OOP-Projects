package com.example.m5minimaxtree;
import java.util.ArrayList;
import java.util.List;


public class TreeNode {
    public char[][] board; // 3x3
    public Move move;
    public List<TreeNode> children = new ArrayList<>();

    public TreeNode(char[][] board, Move move) {
        this.board = copyBoard(board);
        this.move = move;
    }

    private char[][] copyBoard(char[][] src) {
        char[][] dst = new char[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(src[i], 0, dst[i], 0, 3);
        return dst;
    }
}


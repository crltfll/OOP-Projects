package com.example.m5minimaxtree;

import java.util.List;

public class MinimaxAI {

    public Move findBestMove(char[][] board) {

        TreeNode root = new TreeNode(board, null);
        buildGameTree(root, TicTacToeGame.AI, 0);


        return searchTree(root);
    }

    public void buildGameTree(TreeNode node, char currentPlayer, int depth) {
        if (TicTacToeGame.evaluateTerminalScore(node.board) != null) {
            return;
        }

        List<Move> moves = TicTacToeGame.availableMoves(node.board);


        for (Move m : moves) {
            char[][] childBoard = TicTacToeGame.applyMove(node.board, m, currentPlayer);
            TreeNode child = new TreeNode(childBoard, m);
            node.children.add(child);


            char nextPlayer = (currentPlayer == TicTacToeGame.AI)
                    ? TicTacToeGame.HUMAN
                    : TicTacToeGame.AI;
            buildGameTree(child, nextPlayer, depth + 1);
        }
    }

    private Move searchTree(TreeNode root) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;


        for (TreeNode child : root.children) {
            int score = minimaxSearch(child, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (score > bestScore) {
                bestScore = score;
                bestMove = child.move;
            }
        }


        if (bestMove == null) {
            List<Move> moves = TicTacToeGame.availableMoves(root.board);
            if (!moves.isEmpty()) return moves.get(0);
        }

        return bestMove;
    }


    private int minimaxSearch(TreeNode node, boolean maximizing, int alpha, int beta) {

        Integer terminalScore = TicTacToeGame.evaluateTerminalScore(node.board);
        if (terminalScore != null) {
            return terminalScore;
        }


        if (node.children.isEmpty()) {
            return 0;
        }

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;


            for (TreeNode child : node.children) {
                int eval = minimaxSearch(child, false, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;

        } else {
            int minEval = Integer.MAX_VALUE;


            for (TreeNode child : node.children) {
                int eval = minimaxSearch(child, true, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }


    public void printTreeStats(TreeNode root) {
        int[] stats = new int[2];
        calculateTreeStats(root, 0, stats);
        System.out.println("Tree Statistics:");
        System.out.println("  Total Nodes: " + stats[0]);
        System.out.println("  Max Depth: " + stats[1]);
    }

    private void calculateTreeStats(TreeNode node, int depth, int[] stats) {
        stats[0]++;
        stats[1] = Math.max(stats[1], depth);

        for (TreeNode child : node.children) {
            calculateTreeStats(child, depth + 1, stats);
        }
    }
}
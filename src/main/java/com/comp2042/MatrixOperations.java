package com.comp2042;

import com.comp2042.util.GameConstants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Matrix operations utility class providing various operations on game matrices.
 * Includes collision detection, matrix copying, merging, and row clearing.
 */
public final class MatrixOperations {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private MatrixOperations() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    /**
     * Checks if a brick collides with the game board.
     * 
     * @param matrix game board matrix
     * @param brick brick matrix
     * @param x brick X coordinate
     * @param y brick Y coordinate
     * @return true if collision occurs, false otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != GameConstants.COLOR_EMPTY && 
                    (checkOutOfBound(matrix, targetX, targetY) || 
                     matrix[targetY][targetX] != GameConstants.COLOR_EMPTY)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if coordinates are out of matrix bounds.
     * 
     * @param matrix matrix
     * @param targetX target X coordinate
     * @param targetY target Y coordinate
     * @return true if out of bounds, false otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        return !(targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length);
    }

    /**
     * Deep copies a two-dimensional array.
     * 
     * @param original original array
     * @return new array copy
     */
    public static int[][] copy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] row = original[i];
            copy[i] = new int[row.length];
            System.arraycopy(row, 0, copy[i], 0, row.length);
        }
        return copy;
    }

    /**
     * Merges a brick into the game board background.
     * 
     * @param filledFields filled game board
     * @param brick brick to merge
     * @param x brick X coordinate
     * @param y brick Y coordinate
     * @return new merged matrix
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] merged = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != GameConstants.COLOR_EMPTY) {
                    merged[targetY][targetX] = brick[j][i];
                }
            }
        }
        return merged;
    }

    /**
     * Checks and removes filled rows.
     * 
     * @param matrix game board matrix
     * @return ClearRow object containing number of cleared rows and new matrix
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] newMatrix = new int[matrix.length][matrix[0].length];
        Deque<int[]> remainingRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        // Check if each row is full
        for (int i = 0; i < matrix.length; i++) {
            int[] row = new int[matrix[i].length];
            boolean isRowFull = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == GameConstants.COLOR_EMPTY) {
                    isRowFull = false;
                }
                row[j] = matrix[i][j];
            }
            if (isRowFull) {
                clearedRows.add(i);
            } else {
                remainingRows.add(row);
            }
        }
        
        // Rearrange remaining rows
        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = remainingRows.pollLast();
            if (row != null) {
                newMatrix[i] = row;
            } else {
                break;
            }
        }
        
        // Calculate score bonus: 50 * (lines removed)^2
        int linesRemoved = clearedRows.size();
        int scoreBonus = GameConstants.BASE_SCORE_PER_LINE * linesRemoved * linesRemoved;
        return new ClearRow(linesRemoved, newMatrix, scoreBonus);
    }

    /**
     * Deep copies a list of matrices.
     * 
     * @param list original list of matrices
     * @return new list of matrix copies
     */
    public static List<int[][]> deepCopyList(List<int[][]> list) {
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }
}

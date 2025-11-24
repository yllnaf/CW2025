package com.comp2042;

/**
 * Data class containing information about cleared rows.
 * Includes number of rows removed, updated matrix, and score bonus.
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;

    /**
     * Constructor to create clear row information.
     *
     * @param linesRemoved number of rows that were cleared
     * @param newMatrix updated board matrix after clearing
     * @param scoreBonus score points awarded for clearing rows
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of rows removed.
     *
     * @return number of cleared rows
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets a copy of the updated board matrix after clearing rows.
     *
     * @return new board matrix
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus for clearing rows.
     *
     * @return score points
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}

package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Score management class using JavaFX property binding for score management.
 * Provides score increment, reset, and property binding functionality.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty linesCleared = new SimpleIntegerProperty(0);

    /**
     * Gets the score property for JavaFX binding.
     * 
     * @return score property
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Gets the cleared lines property for JavaFX binding.
     *
     * @return cleared lines property
     */
    public IntegerProperty linesProperty() {
        return linesCleared;
    }

    /**
     * Adds points to the score.
     * 
     * @param points points to add
     */
    public void add(int points) {
        score.setValue(score.getValue() + points);
    }

    /**
     * Adds cleared lines count.
     *
     * @param lines number of cleared lines
     */
    public void addLines(int lines) {
        linesCleared.setValue(linesCleared.getValue() + lines);
    }

    /**
     * Resets the score to 0.
     */
    public void reset() {
        score.setValue(0);
        linesCleared.setValue(0);
    }
    
    /**
     * Gets the current score value.
     * 
     * @return current score
     */
    public int getValue() {
        return score.getValue();
    }

    /**
     * Gets the cleared lines count.
     *
     * @return cleared lines value
     */
    public int getLinesCleared() {
        return linesCleared.getValue();
    }
}

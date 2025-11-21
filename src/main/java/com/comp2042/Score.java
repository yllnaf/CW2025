package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Score management class using JavaFX property binding for score management.
 * Provides score increment, reset, and property binding functionality.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Gets the score property for JavaFX binding.
     * 
     * @return score property
     */
    public IntegerProperty scoreProperty() {
        return score;
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
     * Resets the score to 0.
     */
    public void reset() {
        score.setValue(0);
    }
    
    /**
     * Gets the current score value.
     * 
     * @return current score
     */
    public int getValue() {
        return score.getValue();
    }
}

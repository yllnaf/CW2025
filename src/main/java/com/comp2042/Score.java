package com.comp2042;

import com.comp2042.util.GameConstants;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Score management class using JavaFX property binding for score management.
 * Provides score increment, reset, and property binding functionality.
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty linesCleared = new SimpleIntegerProperty(0);
    private final IntegerProperty level = new SimpleIntegerProperty(1);

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
     * Gets the level property for JavaFX binding.
     *
     * @return level property
     */
    public IntegerProperty levelProperty() {
        return level;
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
        updateLevel();
    }

    /**
     * Resets the score to 0.
     */
    public void reset() {
        score.setValue(0);
        linesCleared.setValue(0);
        level.setValue(1);
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

    /**
     * Gets the current level.
     *
     * @return current level
     */
    public int getLevel() {
        return level.getValue();
    }

    private void updateLevel() {
        int cleared = linesCleared.getValue();
        int newLevel = Math.max(1, (cleared / GameConstants.LINES_PER_LEVEL) + 1);
        if (newLevel != level.getValue()) {
            level.setValue(newLevel);
        }
    }
}

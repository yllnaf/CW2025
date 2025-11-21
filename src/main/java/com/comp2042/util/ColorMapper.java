package com.comp2042.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Color mapping strategy class using Strategy pattern to manage brick color mapping.
 * Separates color logic from GUI controller to improve maintainability.
 */
public final class ColorMapper {
    
    private ColorMapper() {
        throw new AssertionError("Cannot instantiate utility class");
    }
    
    /**
     * Gets the corresponding Paint object based on color index.
     * 
     * @param colorIndex color index (0-7)
     * @return corresponding Paint object
     */
    public static Paint getColor(int colorIndex) {
        return switch (colorIndex) {
            case GameConstants.COLOR_EMPTY -> Color.TRANSPARENT;
            case GameConstants.COLOR_BRICK_1 -> Color.AQUA;
            case GameConstants.COLOR_BRICK_2 -> Color.BLUEVIOLET;
            case GameConstants.COLOR_BRICK_3 -> Color.DARKGREEN;
            case GameConstants.COLOR_BRICK_4 -> Color.YELLOW;
            case GameConstants.COLOR_BRICK_5 -> Color.RED;
            case GameConstants.COLOR_BRICK_6 -> Color.BEIGE;
            case GameConstants.COLOR_BRICK_7 -> Color.BURLYWOOD;
            default -> Color.WHITE;
        };
    }
}


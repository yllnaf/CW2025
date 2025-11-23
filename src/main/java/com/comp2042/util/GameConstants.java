package com.comp2042.util;

/**
 * Game constants class that centralizes all magic numbers and configuration values in the game.
 * Improves code readability and maintainability.
 */
public final class GameConstants {
    
    // Private constructor to prevent instantiation
    private GameConstants() {
        throw new AssertionError("Cannot instantiate utility class");
    }
    
    // Board dimensions
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 25;
    
    // Initial brick position (top of the board)
    public static final int INITIAL_BRICK_X = 4;
    public static final int INITIAL_BRICK_Y = 0;
    
    // Display related
    public static final int BRICK_SIZE = 20;
    public static final int GAME_WINDOW_WIDTH = 350; // Increased to accommodate score display
    public static final int GAME_WINDOW_HEIGHT = 510;
    public static final int DISPLAY_OFFSET_Y = -42;
    
    // Game loop
    public static final int GAME_LOOP_INTERVAL_MS = 400;
    public static final int GAME_LOOP_INTERVAL_DECREMENT_MS = 30;
    public static final int GAME_LOOP_MIN_INTERVAL_MS = 180;
    
    // Score calculation
    public static final int BASE_SCORE_PER_DOWN = 1;
    public static final int BASE_SCORE_PER_LINE = 50;
    public static final int LINES_PER_LEVEL = 5;
    
    // Color indices (corresponding to brick types)
    public static final int COLOR_EMPTY = 0;
    public static final int COLOR_BRICK_1 = 1;
    public static final int COLOR_BRICK_2 = 2;
    public static final int COLOR_BRICK_3 = 3;
    public static final int COLOR_BRICK_4 = 4;
    public static final int COLOR_BRICK_5 = 5;
    public static final int COLOR_BRICK_6 = 6;
    public static final int COLOR_BRICK_7 = 7;
    
    // Rectangle corner radius
    public static final double RECTANGLE_ARC_HEIGHT = 9.0;
    public static final double RECTANGLE_ARC_WIDTH = 9.0;
}


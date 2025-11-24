package com.comp2042;

import com.comp2042.save.GameSaveMetadata;

import java.util.List;

/**
 * Interface for handling user input events in the game.
 * Implementations coordinate game logic responses to user actions.
 */
public interface InputEventListener {

    /**
     * Handles brick down movement event.
     *
     * @param event movement event containing event type and source
     * @return down data containing cleared row information and updated view data
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Handles brick left movement event.
     *
     * @param event movement event containing event type and source
     * @return updated view data after left movement
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Handles brick right movement event.
     *
     * @param event movement event containing event type and source
     * @return updated view data after right movement
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Handles brick rotation event.
     *
     * @param event movement event containing event type and source
     * @return updated view data after rotation
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Creates a new game, resetting the game state.
     */
    void createNewGame();

    /**
     * Persists the current game state using the provided save name.
     *
     * @param saveName user provided save identifier
     * @return true if saving succeeded
     */
    boolean saveGame(String saveName);

    /**
     * Loads a saved game identified by the provided file-safe name.
     *
     * @param fileSafeName sanitized save identifier
     * @return true if loading succeeded
     */
    boolean loadGame(String fileSafeName);

    /**
     * Lists available saved games for selection.
     *
     * @return list of save metadata
     */
    List<GameSaveMetadata> listSavedGames();

    /**
     * Deletes a saved game identified by the provided file-safe name.
     *
     * @param fileSafeName sanitized save identifier
     * @return true if deletion succeeded
     */
    boolean deleteSave(String fileSafeName);
}

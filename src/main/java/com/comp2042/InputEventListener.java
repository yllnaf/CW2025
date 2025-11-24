package com.comp2042;

import com.comp2042.save.GameSaveMetadata;

import java.util.List;

public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

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
}

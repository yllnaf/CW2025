package com.comp2042;

/**
 * Data class containing information returned from a down movement event.
 * Includes clear row information and updated view data.
 */
public final class DownData {
    private final ClearRow clearRow;
    private final ViewData viewData;

    /**
     * Constructor to create down movement data.
     *
     * @param clearRow information about cleared rows (may be null if no rows were cleared)
     * @param viewData updated view data after movement
     */
    public DownData(ClearRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    /**
     * Gets the clear row information.
     *
     * @return clear row data, or null if no rows were cleared
     */
    public ClearRow getClearRow() {
        return clearRow;
    }

    /**
     * Gets the updated view data.
     *
     * @return view data
     */
    public ViewData getViewData() {
        return viewData;
    }
}

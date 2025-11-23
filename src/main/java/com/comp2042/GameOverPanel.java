package com.comp2042;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameOverPanel extends BorderPane {

    private final Label scoreValueLabel = new Label();
    private final Label highScoreValueLabel = new Label();
    private final Label newRecordLabel = new Label("NEW RECORD!");

    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");

        scoreValueLabel.getStyleClass().add("scoreValueStyle");
        highScoreValueLabel.getStyleClass().add("scoreValueStyle");
        newRecordLabel.getStyleClass().add("newRecordStyle");
        newRecordLabel.setVisible(false);

        VBox scoreBox = new VBox(6);
        scoreBox.setAlignment(Pos.CENTER);
        Label finalScoreLabel = new Label("SCORE");
        finalScoreLabel.getStyleClass().add("scoreLabelStyle");
        Label bestScoreLabel = new Label("BEST");
        bestScoreLabel.getStyleClass().add("scoreLabelStyle");
        scoreBox.getChildren().addAll(gameOverLabel, finalScoreLabel, scoreValueLabel,
                bestScoreLabel, highScoreValueLabel, newRecordLabel);
        setCenter(scoreBox);
    }

    /**
     * Updates score labels with the final score information.
     *
     * @param score final score of the round
     * @param highScore persisted high score
     * @param newRecord true when a new high score is achieved
     */
    public void updateScores(int score, int highScore, boolean newRecord) {
        scoreValueLabel.setText(String.valueOf(score));
        highScoreValueLabel.setText(String.valueOf(highScore));
        newRecordLabel.setVisible(newRecord);
    }
}

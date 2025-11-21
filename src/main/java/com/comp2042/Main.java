package com.comp2042;

import com.comp2042.util.GameConstants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Main application class responsible for launching the JavaFX application.
 * 
 * @author COMP2042 Coursework
 * @version 1.0
 */
public class Main extends Application {

    /**
     * JavaFX application startup method.
     * 
     * @param primaryStage primary stage
     * @throws Exception if FXML loading fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = fxmlLoader.load();
        GuiController guiController = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, GameConstants.GAME_WINDOW_WIDTH, GameConstants.GAME_WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initialize game controller
        new GameController(guiController);
    }

    /**
     * Application entry point.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

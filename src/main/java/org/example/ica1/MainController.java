package org.example.ica1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * MainController class is the entry point for the Chat Viewer application.
 * It extends the JavaFX Application class and sets up the primary stage with
 * the specified FXML layout and an icon.
 */
public class MainController extends Application {

    /**
     * The start method is called after the JavaFX application is launched.
     * It sets up the primary stage with the FXML layout and an icon.
     *
     * @param primaryStage The primary stage for this application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat_viewer.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/icon.png")));
        primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Chat Viewer");
        primaryStage.show();
    }

    /**
     * The main method serves as the entry point for the Java application.
     * It launches the JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

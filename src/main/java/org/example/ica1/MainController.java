package org.example.ica1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController extends Application {

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

    public static void main(String[] args) {
        launch(args);
    }
}

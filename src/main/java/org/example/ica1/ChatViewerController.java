package org.example.ica1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.prefs.Preferences;

public class ChatViewerController {
    private static final String LAST_OPENED_FILE_KEY = "lastOpenedFile";

    @FXML
    private Label filePathLabel;

    @FXML
    private TextFlow chatTextFlow;

    public TextFlow getChatTextFlow() {
        return chatTextFlow;
    }

    private final Preferences preferences;

    ErrorHandler errorHandler = new ErrorHandler();

    private final ChatLoader chatLoader;

    public ChatViewerController() {
        preferences = Preferences.userNodeForPackage(getClass());
        MessageProcessor messageProcessor = new MessageProcessor(this);
        chatLoader = new ChatLoader(this, messageProcessor,errorHandler);
    }

    @FXML
    private void initialize() {
        try {
            // Load the last opened file path from preferences
            String lastOpenedFilePath = preferences.get(LAST_OPENED_FILE_KEY, null);
            if (lastOpenedFilePath != null) {
                filePathLabel.setText(lastOpenedFilePath);
                chatLoader.loadChatAction(new File(lastOpenedFilePath));
            }
        } catch (IOException e) {
            errorHandler.showErrorMessage("Error reading preferences: " + e.getMessage() + "\nThe file may no longer exist, select another file");
        }
    }


    @FXML
    private void openFile() {
        Stage stage = (Stage) filePathLabel.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Message Files", "*"));

        // Set initial directory to the last opened file directory
        String lastOpenedFilePath = preferences.get(LAST_OPENED_FILE_KEY, null);
        if (lastOpenedFilePath != null) {
            File lastOpenedFile = new File(lastOpenedFilePath);
            if (lastOpenedFile.exists()) {
                fileChooser.setInitialDirectory(lastOpenedFile.getParentFile());
            }
        }

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                // Attempt to read the file
                filePathLabel.setText(selectedFile.getAbsolutePath());
                preferences.put(LAST_OPENED_FILE_KEY, selectedFile.getAbsolutePath());
                chatLoader.loadChatAction(selectedFile);
            } catch (SecurityException e) {
                // Handle file permission error
                errorHandler.showErrorMessage("Error: Permission denied. Unable to access the selected file. \nMost likely you do not have access rights to this file.");
            } catch (IOException e) {
                errorHandler.showErrorMessage("Error reading file: " + e.getMessage() + "\nTry selecting a different file.");
            } catch (Exception e) {
                // Handle unexpected exceptions
                errorHandler.showErrorMessage("An unexpected error occurred: " + e.getMessage() + "\nThe application cannot determine what the error is, try selecting a different file.");
            }
        }
    }
}
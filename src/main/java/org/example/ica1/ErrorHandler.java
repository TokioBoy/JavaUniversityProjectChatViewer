package org.example.ica1;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
public class ErrorHandler {

    private static int errorCounter = 0;

    private void showError(String message) {
        // Create a ScrollPane to make the error message scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(400, 100);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Create a Text object to display the error message
        Text errorMessage = new Text(message);
        errorMessage.setWrappingWidth(380);

        // Add the Text object to the ScrollPane
        scrollPane.setContent(errorMessage);

        // Create an Alert and set its content to the ScrollPane
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(scrollPane);
        alert.showAndWait();
        // Provide guidance for resolution
        alert.setOnCloseRequest(event -> {
            Alert guidanceAlert = new Alert(Alert.AlertType.INFORMATION);
            guidanceAlert.setTitle("Resolution Guidance");
            guidanceAlert.setHeaderText(null);
            guidanceAlert.setContentText("Please check the selected file and try again.");
            guidanceAlert.showAndWait();
        });
    }

    private void showErrorAndWarning(String field, int lineNumber) {
        if (errorCounter < 1) {
            showError("Error: Text in the '" + field + "' field is empty at line " + lineNumber);
            showError("Warning: Because '" + field + "' is empty, changed to 'Unknown " + field.toLowerCase() + "' at line " + lineNumber + ", we do not recommend using this file");
            errorCounter++;
            if (errorCounter == 1) {
                showError("Warning: Since the file contains very many errors, we do not recommend using it. In the next moment, the contents of the file you have opened will be displayed. Please note that some data may be missing or incorrect. Wherever it was not possible to determine the relevant information, the fields have been replaced by \"Unknown nickname\" and \"Unknown time\"");
            }
        }
    }


    public static int getErrorCounter() {
        return errorCounter;
    }

    // Getter for showError
    protected void showErrorMessage(String message) {
        showError(message);
    }

    // Getter for showErrorAndWarning
    protected void showErrorMessageAndWarning(String field, int lineNumber) {
        showErrorAndWarning(field, lineNumber);
    }
}

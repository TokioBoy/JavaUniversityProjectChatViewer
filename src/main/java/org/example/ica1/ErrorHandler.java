package org.example.ica1;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

/**
 * ErrorHandler class provides methods to display error and warning messages.
 * It helps in showing detailed error messages in a scrollable pane.
 */
public class ErrorHandler {

    private static int errorCounter = 0;

    /**
     * Displays an error message in an alert dialog.
     * The message is displayed within a scrollable pane to handle long texts.
     *
     * @param message the error message to display.
     */
    private void showError(String message) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(400, 100);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Text errorMessage = new Text(message);
        errorMessage.setWrappingWidth(380);

        scrollPane.setContent(errorMessage);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(scrollPane);
        alert.showAndWait();

        alert.setOnCloseRequest(event -> {
            Alert guidanceAlert = new Alert(Alert.AlertType.INFORMATION);
            guidanceAlert.setTitle("Resolution Guidance");
            guidanceAlert.setHeaderText(null);
            guidanceAlert.setContentText("Please check the selected file and try again.");
            guidanceAlert.showAndWait();
        });
    }

    /**
     * Displays an error message and a warning if a field is empty at a specific line number.
     * It also shows additional warnings if multiple errors are encountered.
     *
     * @param field the name of the field that is empty.
     * @param lineNumber the line number where the error occurred.
     */
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

    /**
     * Returns the current error counter value.
     *
     * @return the number of errors encountered.
     */
    public static int getErrorCounter() {
        return errorCounter;
    }

    /**
     * Displays an error message. This method is exposed for external use.
     *
     * @param message the error message to display.
     */
    protected void showErrorMessage(String message) {
        showError(message);
    }

    /**
     * Displays an error message and a warning for an empty field at a specific line.
     * This method is exposed for external use.
     *
     * @param field the name of the field that is empty.
     * @param lineNumber the line number where the error occurred.
     */
    protected void showErrorMessageAndWarning(String field, int lineNumber) {
        showErrorAndWarning(field, lineNumber);
    }
}

package org.example.ica1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageProcessor {
    private String previousName = "";
    private boolean firstMessage = true;

    ErrorHandler errorHandler = new ErrorHandler();
    private final ChatViewerController chatViewerController;

    public MessageProcessor(ChatViewerController chatViewerController) {
        this.chatViewerController = chatViewerController;
    }

    private void processMessage(String[] messageLines, int lineNumber) {
        // Check if messageLines array has at least three elements and each element starts with the expected prefix
        if (messageLines.length >= 3 && messageLines[0].startsWith("Time:") && messageLines[1].startsWith("Name:") && messageLines[2].startsWith("Message:")) {
            // Extract and check current time
            String currentTime = messageLines[0].substring(5).trim(); // Extract time and trim whitespace
            if (currentTime.isEmpty()) {
                currentTime = "Unknown time";
                errorHandler.showErrorMessageAndWarning("Time", lineNumber);
            }

            // Extract and check current name
            String currentName = messageLines[1].substring(5).trim(); // Extract name and trim whitespace
            if (currentName.isEmpty()) {
                currentName = "Unknown nickname";
                errorHandler.showErrorMessageAndWarning("Name", lineNumber);
            }

            // Prepare time and name texts
            Text timeText = new Text("[" + currentTime + "]");
            Text nameText;
            if (firstMessage) {
                nameText = new Text(currentName + ": ");
                nameText.setStyle("-fx-fill: blue;"); // Set the text color to blue
                firstMessage = false;
            } else if (currentName.equals(previousName)) {
                nameText = new Text("...");
                nameText.setStyle("-fx-fill: blue;"); // Set the text color to blue
            } else {
                nameText = new Text(currentName + ": ");
                nameText.setStyle("-fx-fill: blue;"); // Set the text color to blue
                previousName = currentName;
            }

            // Extract and prepare message text
            Text messageText = new Text(messageLines[2].substring(8));
            messageText.setStyle("-fx-fill: black; -fx-font-weight: bold;"); // Set text color to black and bold

            // Add the texts to the TextFlow
            chatViewerController.getChatTextFlow().getChildren().addAll(timeText, nameText);
            formatAndAddMessage(messageText.getText());
        } else {
            errorHandler.showErrorMessage("Error: Invalid format in the conversation file at line " + lineNumber + ", choose another file");
        }
    }

    private void formatAndAddMessage(String message) {
        // Find emoticons using regular expression
        Pattern pattern = Pattern.compile(":\\)|:\\(");
        Matcher matcher = pattern.matcher(message);
        int lastIndex = 0;

        // Iterate through matches
        while (matcher.find()) {
            // Add text before the match
            String textBefore = message.substring(lastIndex, matcher.start());
            if (!textBefore.isEmpty()) {
                Text textNode = new Text(textBefore);
                textNode.setStyle("-fx-fill: black; -fx-font-weight: bold;");
                chatViewerController.getChatTextFlow().getChildren().add(textNode);
            }

            // Handle emoticon
            String emoticon = matcher.group();
            File file = emoticon.equals(":)") ?
                    new File("src/main/resources/assets/smile_happy.gif") :
                    new File("src/main/resources/assets/smile_sad.gif");
            try {
                Image image = new Image(file.toURI().toString());
                chatViewerController.getChatTextFlow().getChildren().add(new ImageView(image));
            } catch (Exception e) {
                errorHandler.showErrorMessage("Error loading emoticon image: " + e.getMessage());
            }

            // Update last index
            lastIndex = matcher.end();
        }

        // Add remaining text after the last match
        String remainingText = message.substring(lastIndex);
        if (!remainingText.isEmpty()) {
            Text remainingTextNode = new Text(remainingText);
            remainingTextNode.setStyle("-fx-fill: black; -fx-font-weight: bold;");
            chatViewerController.getChatTextFlow().getChildren().add(remainingTextNode);
        }

        // Add a new line after each message
        chatViewerController.getChatTextFlow().getChildren().add(new Text("\n"));
    }

    public void setFirstMessage(boolean firstMessage) {
        this.firstMessage = firstMessage;
    }
    
    protected void processMessageAction(String[] messageLines, int lineNumber) {
        processMessage(messageLines, lineNumber);
    }
}

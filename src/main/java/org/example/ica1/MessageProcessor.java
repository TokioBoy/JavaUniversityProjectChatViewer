package org.example.ica1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MessageProcessor class processes chat messages and formats them for display.
 */
public class MessageProcessor {
    private String previousName = "";
    private boolean firstMessage = true;

    ErrorHandler errorHandler = new ErrorHandler();
    private final ChatViewerController chatViewerController;

    /**
     * Constructs a MessageProcessor with the specified ChatViewerController.
     *
     * @param chatViewerController the controller responsible for managing the chat view.
     */
    public MessageProcessor(ChatViewerController chatViewerController) {
        this.chatViewerController = chatViewerController;
    }

    /**
     * Processes a single message and formats it for display.
     *
     * @param messageLines the lines of the message to process.
     * @param lineNumber the line number of the message in the source file.
     */
    private void processMessage(String[] messageLines, int lineNumber) {
        if (messageLines.length >= 3 && messageLines[0].startsWith("Time:") && messageLines[1].startsWith("Name:") && messageLines[2].startsWith("Message:")) {
            String currentTime = messageLines[0].substring(5).trim();
            if (currentTime.isEmpty()) {
                currentTime = "Unknown time";
                errorHandler.showErrorMessageAndWarning("Time", lineNumber);
            }

            String currentName = messageLines[1].substring(5).trim();
            if (currentName.isEmpty()) {
                currentName = "Unknown nickname";
                errorHandler.showErrorMessageAndWarning("Name", lineNumber);
            }

            Text timeText = new Text("[" + currentTime + "]");
            Text nameText;
            if (firstMessage) {
                nameText = new Text(currentName + ": ");
                nameText.setStyle("-fx-fill: blue;");
                firstMessage = false;
            } else if (currentName.equals(previousName)) {
                nameText = new Text("...");
                nameText.setStyle("-fx-fill: blue;");
            } else {
                nameText = new Text(currentName + ": ");
                nameText.setStyle("-fx-fill: blue;");
                previousName = currentName;
            }

            Text messageText = new Text(messageLines[2].substring(8));
            messageText.setStyle("-fx-fill: black; -fx-font-weight: bold;");

            chatViewerController.getChatTextFlow().getChildren().addAll(timeText, nameText);
            formatAndAddMessage(messageText.getText());
        } else {
            errorHandler.showErrorMessage("Error: Invalid format in the conversation file at line " + lineNumber + ", choose another file");
        }
    }

    /**
     * Formats a message string and adds it to the chat view, replacing emoticons with images.
     *
     * @param message the message string to format and add.
     */
    private void formatAndAddMessage(String message) {
        Pattern pattern = Pattern.compile(":\\)|:\\(");
        Matcher matcher = pattern.matcher(message);
        int lastIndex = 0;

        while (matcher.find()) {
            String textBefore = message.substring(lastIndex, matcher.start());
            if (!textBefore.isEmpty()) {
                Text textNode = new Text(textBefore);
                textNode.setStyle("-fx-fill: black; -fx-font-weight: bold;");
                chatViewerController.getChatTextFlow().getChildren().add(textNode);
            }

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

            lastIndex = matcher.end();
        }

        String remainingText = message.substring(lastIndex);
        if (!remainingText.isEmpty()) {
            Text remainingTextNode = new Text(remainingText);
            remainingTextNode.setStyle("-fx-fill: black; -fx-font-weight: bold;");
            chatViewerController.getChatTextFlow().getChildren().add(remainingTextNode);
        }

        chatViewerController.getChatTextFlow().getChildren().add(new Text("\n"));
    }

    /**
     * Sets the first message flag to indicate if the next message is the first in the conversation.
     *
     * @param firstMessage true if the next message is the first, false otherwise.
     */
    public void setFirstMessage(boolean firstMessage) {
        this.firstMessage = firstMessage;
    }

    /**
     * Processes a message action by invoking the processMessage method.
     *
     * @param messageLines the lines of the message to process.
     * @param lineNumber the line number of the message in the source file.
     */
    protected void processMessageAction(String[] messageLines, int lineNumber) {
        processMessage(messageLines, lineNumber);
    }
}

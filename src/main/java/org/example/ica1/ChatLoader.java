package org.example.ica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ChatLoader {

    private final ChatViewerController chatViewerController;

    private final MessageProcessor messageProcessor;

    private final ErrorHandler errorHandler;

    public ChatLoader(ChatViewerController chatViewerController, MessageProcessor messageProcessor, ErrorHandler errorHandler) {
        this.chatViewerController = chatViewerController;
        this.messageProcessor = messageProcessor;
        this.errorHandler = errorHandler;
    }


    private void loadChat(File file) throws IOException {
        chatViewerController.getChatTextFlow().getChildren().clear();
        messageProcessor.setFirstMessage(true);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1; // String counter
            int messageNumber = 0; // Message counter
            String[] messageLines = new String[3]; // Array for storing strings of one message
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Skip the blank lines
                    if (firstLine) {
                        if (!line.startsWith("Time:")) {
                            errorHandler.showErrorMessage("Error: Invalid format in the conversation file. Expected 'Time:' at the beginning of the first line.");
                            return;
                        }
                        firstLine = false;
                    }
                    messageLines[messageNumber++] = line;
                    if (messageNumber == 3) { // If strings for one message are collected
                        messageProcessor.processMessageAction(messageLines, lineNumber);
                        messageNumber = 0;
                    }
                } else if (messageNumber > 0) {
                    errorHandler.showErrorMessage("Error: Missing lines in the conversation file at line " + lineNumber + ", choose another file");
                    return;
                }
                lineNumber++;
            }

            if (messageNumber > 0) {
                errorHandler.showErrorMessage("Error: Incomplete message at the end of the file.");
            }
        }
    }

    protected void loadChatAction(File file) throws IOException {
        loadChat(file);
    }
}

package org.example.ica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * ChatLoader class is responsible for loading chat messages from a file and
 * processing them for display in the ChatViewerController.
 */
public class ChatLoader {

    private final ChatViewerController chatViewerController;
    private final MessageProcessor messageProcessor;
    private final ErrorHandler errorHandler;

    /**
     * Constructs a ChatLoader with the specified controller, message processor, and error handler.
     *
     * @param chatViewerController the controller responsible for managing the chat view.
     * @param messageProcessor the processor responsible for handling individual messages.
     * @param errorHandler the handler responsible for managing errors.
     */
    public ChatLoader(ChatViewerController chatViewerController, MessageProcessor messageProcessor, ErrorHandler errorHandler) {
        this.chatViewerController = chatViewerController;
        this.messageProcessor = messageProcessor;
        this.errorHandler = errorHandler;
    }

    /**
     * Loads and processes the chat messages from the specified file.
     *
     * @param file the file containing chat messages.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    private void loadChat(File file) throws IOException {
        chatViewerController.getChatTextFlow().getChildren().clear();
        messageProcessor.setFirstMessage(true);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            int messageNumber = 0;
            String[] messageLines = new String[3];
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    if (firstLine) {
                        if (!line.startsWith("Time:")) {
                            errorHandler.showErrorMessage("Error: Invalid format in the conversation file. Expected 'Time:' at the beginning of the first line.");
                            return;
                        }
                        firstLine = false;
                    }
                    messageLines[messageNumber++] = line;
                    if (messageNumber == 3) {
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

    /**
     * Public method to load and process the chat messages from the specified file.
     * This method is exposed for external use.
     *
     * @param file the file containing chat messages.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    protected void loadChatAction(File file) throws IOException {
        loadChat(file);
    }
}

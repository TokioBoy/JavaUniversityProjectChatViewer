package org.example.ica1;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest extends TestBase {
    @Test
    void errorCounterTest() throws ExecutionException, InterruptedException, TimeoutException {
        ErrorHandler errorHandler = new ErrorHandler();

        // Create CompletableFuture to signal when error messages are shown
        CompletableFuture<Void> errorShown = new CompletableFuture<>();

        Platform.runLater(() -> {
            errorHandler.showErrorMessageAndWarning("Test error message", 1);
            // Signal completion when all error messages are shown
            errorShown.complete(null);
        });
        closeCurrentWindow();
        closeCurrentWindow();
        closeCurrentWindow();

        // Wait for the error messages to be processed
        errorShown.get(10, SECONDS);
        assertEquals(1, ErrorHandler.getErrorCounter());
        System.out.println("errorCounterTest passed, errorCounter is 1");
    }

}
package org.example.ica1;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ErrorHandlerTest class contains unit tests for the ErrorHandler class.
 * It extends TestBase to set up and tear down the JavaFX environment for testing.
 */
class ErrorHandlerTest extends TestBase {

    /**
     * Tests the error counter functionality of the ErrorHandler class.
     * It verifies that the error counter is incremented correctly when an error message and warning are shown.
     *
     * @throws ExecutionException if the computation threw an exception.
     * @throws InterruptedException if the current thread was interrupted while waiting.
     * @throws TimeoutException if the wait timed out.
     */
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

        // Verify the error counter
        assertEquals(1, ErrorHandler.getErrorCounter());
        System.out.println("errorCounterTest passed, errorCounter is 1");
    }
}

package org.example.ica1;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.TestFx;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest extends TestBase {


    @Test
    void errorCounterTest() throws Exception {
        Platform.runLater(() -> {
            ErrorHandler errorHandler = new ErrorHandler();
            errorHandler.showErrorMessageAndWarning("Test error message", 1);
            errorHandler.showErrorMessageAndWarning("Test error message", 1);
            errorHandler.showErrorMessageAndWarning("Test error message", 1);
        });

        // Wait for JavaFX application thread to finish showing the alerts
        FxToolkit.waitForFxEvents();

        assertEquals(3, ErrorHandler.getErrorCounter());
    }
}
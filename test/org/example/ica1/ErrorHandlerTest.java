package org.example.ica1;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.TestFx;

import java.awt.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest extends TestBase {


    @Test
    void errorCounterTest() throws TimeoutException {
//        ErrorHandler errorHandler = new ErrorHandler();
        Platform.runLater(() -> {
            ErrorHandler errorHandler = new ErrorHandler();
            errorHandler.showErrorMessageAndWarning("Test error message", 1);
        });

//        Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
//            assertEquals(3, ErrorHandler.getErrorCounter());
//        });

        assertEquals(3, ErrorHandler.getErrorCounter());
    }
}
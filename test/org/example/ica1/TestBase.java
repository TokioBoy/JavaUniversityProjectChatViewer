package org.example.ica1;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeoutException;

/**
 * TestBase class provides a base setup for JavaFX tests using TestFX.
 * It includes methods for initializing and cleaning up the JavaFX environment before and after each test.
 */
public class TestBase extends ApplicationTest {

    /**
     * Sets up the headless mode for JavaFX tests before any test methods are executed.
     * This method hides the primary stage if it was previously shown.
     *
     * @throws TimeoutException if a timeout occurs during the setup.
     */
    @BeforeClass
    public static void setUpHeadLessMode() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage(); // hide primary Stage, if was previously shown.
    }

    /**
     * Sets up the JavaFX environment before each test method.
     * It registers a new stage and sets up a custom scene with a label.
     *
     * @throws Exception if an error occurs during the setup.
     */
    @BeforeEach
    public void beforeEachTest() throws Exception {
        FxToolkit.registerStage(Stage::new);
        FxToolkit.setupStage(stage -> stage.setScene(new Scene(new Label("within custom stage"))));
        FxToolkit.showStage();
    }

    /**
     * Cleans up the JavaFX environment after each test method.
     * This method hides the stage to ensure it does not affect subsequent tests.
     *
     * @throws TimeoutException if a timeout occurs during the cleanup.
     */
    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
    }
}

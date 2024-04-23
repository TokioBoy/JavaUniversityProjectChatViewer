package org.example.ica1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeoutException;

public class TestBase extends ApplicationTest {

    @BeforeClass
    public static void setUpHeadLessMode() throws TimeoutException {
//        if (Boolean.getBoolean("headless")) {
//            System.setProperty("testfx.robot", "glass");
//            System.setProperty("testfx.headless", "true");
//            System.setProperty("prism.order", "sw");
//            System.setProperty("prism.text", "t2k");
//            System.setProperty("java.awt.headless", "true");
//        }

        FxToolkit.registerPrimaryStage();
        FxToolkit.hideStage(); // hide primary Stage, if was previously shown.
    }

    @BeforeEach
    public void beforeEachTest() throws Exception{
        FxToolkit.registerStage(Stage::new);
        FxToolkit.setupStage(stage -> stage.setScene(new Scene(new Label("within custom stage"))));
        FxToolkit.showStage();
    }

    @AfterEach
    public void afterEachTest() throws TimeoutException {
        FxToolkit.hideStage();
    }

}

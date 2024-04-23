module org.example.ica1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.prefs;

    opens org.example.ica1 to javafx.fxml;
    exports org.example.ica1;
        }
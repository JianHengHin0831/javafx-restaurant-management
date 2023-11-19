module com.example.coursework1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires org.testng;
    requires junit.platform.console.standalone;

    requires java.sql;

    opens com.example.coursework1 to javafx.fxml;
    exports com.example.coursework1;
}
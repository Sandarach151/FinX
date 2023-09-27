module com.example.finx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.finx to javafx.fxml;
    exports com.example.finx;
    exports com.example.finx.Model;
    exports com.example.finx.Controller;
    opens com.example.finx.Controller to javafx.fxml;
    exports com.example.finx.Others;
    opens com.example.finx.Others to javafx.fxml;
}
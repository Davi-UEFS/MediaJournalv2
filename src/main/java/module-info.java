module journal {
    requires com.google.gson;
    requires javafx.fxml;
    requires javafx.controls;

    exports com.davigui.mediajournal;
    opens com.davigui.mediajournal to javafx.fxml;
}
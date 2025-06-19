module journal {
    requires com.google.gson;
    requires javafx.fxml;
    requires javafx.controls;

    exports com.davigui.mediajournal to javafx.graphics;
    opens com.davigui.mediajournal to javafx.fxml, com.google.gson;
    opens com.davigui.mediajournal.Model.Medias to com.google.gson;
    opens com.davigui.mediajournal.Model.Enums to com.google.gson;

    exports com.davigui.mediajournal.Model.Medias;
    exports com.davigui.mediajournal.Model.Enums;
    exports com.davigui.mediajournal.Model.Repository;
    exports com.davigui.mediajournal.Controller;
    exports com.davigui.mediajournal.ViewFXControllers;
    opens com.davigui.mediajournal.ViewFXControllers to javafx.fxml;
}
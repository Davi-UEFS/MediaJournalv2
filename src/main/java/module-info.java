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
    exports com.davigui.mediajournal.ViewFXControllers.MainScreen;
    exports com.davigui.mediajournal.ViewFXControllers.RateScreens to javafx.fxml;
    opens com.davigui.mediajournal.ViewFXControllers.MainScreen to javafx.fxml;
    exports com.davigui.mediajournal.ViewFXControllers.RegisterScreens;
    opens com.davigui.mediajournal.ViewFXControllers.RegisterScreens to javafx.fxml;
    opens com.davigui.mediajournal.ViewFXControllers.RateScreens to javafx.fxml;

}
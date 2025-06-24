package com.davigui.mediajournal;

import com.davigui.mediajournal.ViewFXControllers.MainScreen.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(MainFX.class.getResource("fxml/MainScreen.fxml"));
        Parent root = mainLoader.load();
        MainScreenController mainScreenController = mainLoader.getController();

        Scene scene = new Scene(root);
        Image icon = new Image(MainFX.class.getResourceAsStream("images/library_icon_G.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Diario Cultural");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            mainScreenController.saveLibrary();
        });
        stage.show();

    }

    public static void main(String[] args){
        launch();
    }

}

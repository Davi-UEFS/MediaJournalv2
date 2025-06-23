package com.davigui.mediajournal.ViewFXControllers.RegisterScreens;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class RegisterScreenController implements Initializable {

    //*********Atributos FXML******************
    @FXML protected TextField field_title;
    @FXML protected TextField field_author;
    @FXML protected TextField field_year;
    @FXML protected ComboBox<String> genreBox;
    @FXML protected Button registerButton;

    //***********Metodos Comuns e Assinaturas*********************
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        field_title.setPromptText("Título");
        field_author.setPromptText("Autor");
        field_year.setPromptText("Ano");

        genreBox.getItems().addAll("- Gênero -", "Terror", "Ação", "Aventura",
                "Suspense", "Romance", "Ficção", "Esportes", "Comédia",
                "Mistério", "Criminal", "Infantil", "Outros"
        );
        genreBox.getSelectionModel().selectFirst();
    }

    @FXML abstract public void onRegisterButtonClicked();

    //**********Metodos de Validaçãp*************
    @FXML
    public int validateInt(String string) {
        int num;
        try {
            num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (num > 2025) return -1;
        return num;
    }

    @FXML
    public static void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }

}

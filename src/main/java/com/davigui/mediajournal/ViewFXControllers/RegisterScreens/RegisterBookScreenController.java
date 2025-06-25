package com.davigui.mediajournal.ViewFXControllers.RegisterScreens;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.Model.Result.Success;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterBookScreenController extends RegisterScreenController{

    //*********Atributos FXML******************
    @FXML private TextField field_publisher;
    @FXML private TextField field_isbn;
    @FXML private CheckBox possuiLivroCheckBox;

    //*********Atributos NAO FXML***********
    private BookService service;

    //***********Metodos*********************

    public void setService(BookService bookService) {
        this.service = bookService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        field_publisher.setPromptText("Editora");
        field_isbn.setPromptText("ISBN");

    }

    @Override
    public void onRegisterButtonClicked() {
        String title = field_title.getText();
        String author = field_author.getText();
        String publisher = field_publisher.getText();
        String year = field_year.getText();
        String isbn = field_isbn.getText();
        String genre = genreBox.getValue();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() ||
                year.isEmpty() || isbn.isEmpty() || genre.equals("- Gênero -")) {
            // Exibir mensagem de erro ou alerta
            showAlert(Alert.AlertType.ERROR, "Por favor, preencha todos os campos.");
            return;
        }
        int yearInt = validateInt(year);
        if (yearInt == -1) {
            field_year.clear();
            showAlert(Alert.AlertType.ERROR, "Ano inválido. Por favor, insira um ano válido.");
            return;
        }
        Genres genreE = Genres.valueOf(genre.toUpperCase());


        IResult result = service.register(title, yearInt, genreE,
                isbn, author, publisher, possuiLivroCheckBox.isSelected());
        if (result.getClass().equals(Success.class)) {
            showAlert(Alert.AlertType.INFORMATION, "Registrado com sucesso!");
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, result.getMessage());
        }


    }


}

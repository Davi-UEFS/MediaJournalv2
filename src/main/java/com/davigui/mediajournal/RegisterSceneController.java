package com.davigui.mediajournal;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterSceneController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label year;

    @FXML
    private Label author;

    @FXML
    private Label publisher;

    @FXML
    private Label isbn;

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker yearField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField isbnField;

    @FXML
    private CheckBox ownedCheckBox;

    @FXML
    private ComboBox<String> mediaComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaComboBox.getItems().addAll("Livro", "Filme", "Serie", "Temporada");
    }
}

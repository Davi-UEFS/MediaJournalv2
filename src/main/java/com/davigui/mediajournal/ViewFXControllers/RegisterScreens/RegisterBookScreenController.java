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

/**
 * Controlador para a tela de registro de livros.
 * <p>
 * Estende {@code RegisterScreenController} e implementa a lógica específica para registro de livros.
 */
public class RegisterBookScreenController extends RegisterScreenController {

    //*********Atributos FXML******************

    /**
     * Campo de texto para a editora do livro.
     */
    @FXML private TextField fieldPublisher;

    /**
     * Campo de texto para o ISBN do livro.
     */
    @FXML private TextField fieldIsbn;

    /**
     * CheckBox para marcar se possui ou não o livro.
     */
    @FXML private CheckBox ownBookCheckBox;

    //*********Atributos NAO FXML***********

    /**
     * Serviço de livros utilizado para registrar o livro.
     */
    private BookService service;

    //***********Métodos*********************

    /**
     * Define o serviço de livros a ser utilizado pelo controlador.
     *
     * @param bookService O serviço de livros a ser utilizado.
     */
    public void setService(BookService bookService) {
        this.service = bookService;
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * <p>
     * Chama a inicialização da classe pai e configura os textos temporários adicionais
     * dos campos de texto específicos para o registro de livros.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        fieldPublisher.setPromptText("Editora");
        fieldIsbn.setPromptText("ISBN");
    }

    /**
     * Método chamado quando o botão de registro é clicado.
     * <p>
     * Valida os campos de entrada e tenta registrar o livro através da classe serviço.
     * Exibe mensagens de sucesso ou erro conforme o resultado da operação.
     */
    @Override
    public void onRegisterButtonClicked() {
        String title = fieldTitle.getText();
        String author = fieldAuthor.getText();
        String publisher = fieldPublisher.getText();
        String year = fieldYear.getText();
        String isbn = fieldIsbn.getText();
        String genre = genreBox.getValue();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() ||
                year.isEmpty() || isbn.isEmpty() || genre.equals("- Gênero -")) {
            // Exibir mensagem de erro ou alerta
            showAlert(Alert.AlertType.ERROR, "Por favor, preencha todos os campos.");
            return;
        }
        int yearInt = validateInt(year);
        if (yearInt == -1) {
            fieldYear.clear();
            showAlert(Alert.AlertType.ERROR, "Ano inválido. Por favor, insira um ano válido.");
            return;
        }
        Genres genreE = Genres.valueOf(genre.toUpperCase());

        IResult result = service.register(title, yearInt, genreE,
                isbn, author, publisher, ownBookCheckBox.isSelected());
        if (result.getClass().equals(Success.class)) {
            showAlert(Alert.AlertType.INFORMATION, "Registrado com sucesso!");
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, result.getMessage());
        }
    }
}
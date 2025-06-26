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
 * </p>
 * Estende {@code RegisterScreenController} e implementa a lógica específica para registro de livros.
 */
public class RegisterBookScreenController extends RegisterScreenController {

    //*********Atributos FXML******************

    /**
     * Campo de texto para a editora do livro.
     */
    @FXML private TextField field_publisher;

    /**
     * Campo de texto para o ISBN do livro.
     */
    @FXML private TextField field_isbn;

    /**
     * ChechkBox para marcar se possui ou não o livro.
     */
    @FXML private CheckBox possuiLivroCheckBox;

    //*********Atributos NAO FXML***********

    /**
     * Serviço de livros utilizado para registrar o livro.
     */
    private BookService service;

    //***********Metodos*********************

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
     * </p>
     * Chama a inicialização da classe pai e configura os textos temporários adicionais
     * dos campos de texto específicos para o registro de livros.
     *
     * @param url O local usado para resolver caminhos relativos para o objeto raiz.
     * @param rb O recurso usado para localizar o objeto raiz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        field_publisher.setPromptText("Editora");
        field_isbn.setPromptText("ISBN");
    }

    /**
     * Metodo chamado quando o botão de registro é clicado.
     * </p>
     * Valida os campos de entrada e tenta registrar o livro através da classe serviço.
     * Exibe mensagens de sucesso ou erro conforme o resultado da operação.
     */
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
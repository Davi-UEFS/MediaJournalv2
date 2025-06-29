package com.davigui.mediajournal.ViewFXControllers.RegisterScreens;

import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.Model.Result.Success;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para a tela de registro de filmes.
 * <p>
 * Estende RegisterScreenController e implementa a lógica específica para registro de filme.
 */
public class RegisterMovieScreenController extends RegisterScreenController{

    // *********Atributos FXML******************

    /**
     * Campo de texto para a direção do filme.
     */
    @FXML private TextField fieldDirection;

    /**
     * Campo de texto para a duração do filme.
     */
    @FXML private TextField fieldDuration;

    /**
     * Campo de texto para o título original do filme.
     */
    @FXML private TextField fieldOGTitle;

    /**
     * Campo de texto para os locais onde o filme pode ser assistido.
     */
    @FXML private TextArea fieldWhere;

    /**
     * Campo de texto para o elenco do filme.
     */
    @FXML private TextArea fieldCast;

    /**
     * Campo de texto para a sinopse do filme.
     */
    @FXML private TextArea fieldScript;

    // *********Atributos NAO FXML***********

    /**
     * Serviço de filmes utilizado para registrar o filme.
     */
    private MovieService service;

    // ***********Métodos*********************

    /**
     * Define o serviço de filmes a ser utilizado pelo controlador.
     *
     * @param movieService O serviço de filmes a ser utilizado.
     */
    public void setService(MovieService movieService) {
        this.service = movieService;
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * <p>
     * Chama a inicialização da classe pai e configura os textos temporários adicionais
     * dos campos de texto específicos para o registro de filmes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        fieldDirection.setPromptText("Direção");
        fieldDuration.setPromptText("Duração (minutos)");
        fieldWhere.setPromptText("Streaming1, Streaming2, ...");
        fieldCast.setPromptText("Ator1, Ator2, ...");
        fieldScript.setPromptText("Sinopse");
    }

    /**
     * Método chamado quando o botão de registro é clicado.
     * <p>
     * Valida os campos de entrada e registra o filme usando o serviço de filmes.
     * Exibe mensagens de erro ou sucesso conforme o resultado da operação.
     */
    @Override
    public void onRegisterButtonClicked() {
        String title = fieldTitle.getText();
        String year = fieldYear.getText();
        String direction = fieldDirection.getText();
        String duration = fieldDuration.getText();
        String whereToWatch = fieldWhere.getText();
        String cast = fieldCast.getText();
        String script = fieldScript.getText();
        Genres genre = genreBox.getValue();
        String originalTitle = fieldOGTitle.getText();

        if (title.isEmpty() || year.isEmpty() || direction.isEmpty() || duration.isEmpty() || whereToWatch.isEmpty() ||
                cast.isEmpty() || script.isEmpty() || genre == null || originalTitle.isEmpty()) {
            // Exibir mensagem de erro ou alerta
            showAlert(Alert.AlertType.ERROR, "Por favor, preencha todos os campos.");
            return;
        }

        int yearInt = parseAndValidateInt(year);
        if (yearInt == -1) {
            fieldYear.clear();
            showAlert(Alert.AlertType.ERROR, "Ano inválido. Por favor, insira um ano válido.");
            return;
        }
        int durationInt = parseAndValidateInt(duration);
        if (durationInt == -1) {
            fieldDuration.clear();
            showAlert(Alert.AlertType.ERROR, "Duração inválida. Por favor, insira uma duração válida.");
            return;
        }

        String[] castBuffer = cast.split(",");
        String[] whereToWatchBuffer = whereToWatch.split(",");

        IResult result = service.register(title, yearInt, genre, castBuffer,
                durationInt, direction, script, originalTitle, whereToWatchBuffer);
        if (result.getClass().equals(Success.class)) {
            showAlert(Alert.AlertType.INFORMATION, "Filme registrado com sucesso!");
            // Fechar a janela de registro
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
        } else {
            showAlert(Alert.AlertType.ERROR, result.getMessage());
        }
    }
}

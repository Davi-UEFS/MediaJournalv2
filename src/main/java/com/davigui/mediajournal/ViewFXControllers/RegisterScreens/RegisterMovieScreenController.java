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
    @FXML private TextField field_direction;

    /**
     * Campo de texto para a duração do filme.
     */
    @FXML private TextField field_duration;

    /**
     * Campo de texto para o título original do filme.
     */
    @FXML private TextField field_ogTitle;

    /**
     * Campo de texto para os locais onde o filme pode ser assistido.
     */
    @FXML private TextArea field_where;

    /**
     * Campo de texto para o elenco do filme.
     */
    @FXML private TextArea field_cast;

    /**
     * Campo de texto para a sinopse do filme.
     */
    @FXML private TextArea field_script;

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
        field_direction.setPromptText("Direção");
        field_duration.setPromptText("Duração (minutos)");
        field_where.setPromptText("Streaming1, Streaming2, ...");
        field_cast.setPromptText("Ator1, Ator2, ...");
        field_script.setPromptText("Sinopse");
    }

    /**
     * Método chamado quando o botão de registro é clicado.
     * <p>
     * Valida os campos de entrada e registra o filme usando o serviço de filmes.
     * Exibe mensagens de erro ou sucesso conforme o resultado da operação.
     */
    @Override
    public void onRegisterButtonClicked() {
        String title = field_title.getText();
        String year = field_year.getText();
        String direction = field_direction.getText();
        String duration = field_duration.getText();
        String whereToWatch = field_where.getText();
        String cast = field_cast.getText();
        String script = field_script.getText();
        String genre = genreBox.getValue();
        String originalTitle = field_ogTitle.getText();

        if (title.isEmpty() || year.isEmpty() || direction.isEmpty() || duration.isEmpty() || whereToWatch.isEmpty() ||
                cast.isEmpty() || script.isEmpty() || genre.equals("- Gênero -") || originalTitle.isEmpty()) {
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
        int durationInt = validateInt(duration);
        if (durationInt == -1) {
            field_duration.clear();
            showAlert(Alert.AlertType.ERROR, "Duração inválida. Por favor, insira uma duração válida.");
            return;
        }
        Genres genreE = Genres.valueOf(genre.toUpperCase());
        String[] castBuffer = cast.split(",");
        String[] whereToWatchBuffer = whereToWatch.split(",");

        IResult result = service.register(title, yearInt, genreE, castBuffer,
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

package com.davigui.mediajournal.ViewFXControllers.RegisterScreens;

import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.Model.Result.Success;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para a tela de registro de séries.
 * <p>
 * Estende {@code RegisterScreenController} e implementa a lógica específica para registro de séries.
 */
public class RegisterSeriesScreenController extends RegisterScreenController{

    //*********Atributos FXML******************

    /**
     * Campo de texto para o título original da série.
     */
    @FXML private TextField fieldOGTitle;

    /**
     * Campo de texto para o elenco da série.
     */
    @FXML private TextArea fieldCast;

    /**
     * Campo de texto para os locais onde a série pode ser assistida.
     */
    @FXML private TextArea fieldWhere;

    /**
     * Campo de texto para o ano de encerramento da série.
     */
    @FXML private TextField fieldEndingYear;

    /**
     * Campo de texto para o número de episódios da primeira temporada.
     */
    @FXML private TextField fieldEpisodeCount;

    /**
     * CheckBox para marcar se a série está em andamento.
     */
    @FXML CheckBox andamentoCheckBox;

    //*********Atributos NAO FXML***********

    /**
     * Serviço de séries utilizado para registrar a série.
     */
    private SeriesService seriesService;

    //***********Métodos*********************

    /**
     * Define o serviço de séries a ser utilizado pelo controlador.
     *
     * @param seriesService O serviço de séries a ser utilizado.
     */
    public void setService(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * <p>
     * Chama a inicialização da classe pai e configura os textos temporários adicionais
     * dos campos de texto específicos para o registro de séries.
     * Quando a série está em andamento, o campo de ano de encerramento é desabilitado.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        fieldOGTitle.setPromptText("Título Original");
        fieldCast.setPromptText("Ator1, Ator2, ...");
        fieldWhere.setPromptText("Streaming1, Streaming2, ...");
        fieldEndingYear.setPromptText("Ano");
        fieldEpisodeCount.setPromptText("N° de Ep.");
        fieldEndingYear.disableProperty().bind(andamentoCheckBox.selectedProperty());
    }

    /**
     * Método chamado quando o botão de registro é clicado.
     * <p>
     * Valida os campos de entrada e registra a série usando o serviço de séries.
     * Exibe mensagens de erro ou sucesso conforme necessário.
     * Sempre registra a primeira temporada com o ano de lançamento da série.
     */
    @Override
    public void onRegisterButtonClicked() {
        String title = fieldTitle.getText();
        String year = fieldYear.getText();
        String ogTitle = fieldOGTitle.getText();
        String cast = fieldCast.getText();
        String whereToWatch = fieldWhere.getText();
        String endingYear = fieldEndingYear.getText();
        String episodeCount = fieldEpisodeCount.getText();
        String genre = genreBox.getValue();

        if (title.isEmpty() || year.isEmpty() || ogTitle.isEmpty() || cast.isEmpty() ||
                whereToWatch.isEmpty() || genre.equals("- Gênero -") || episodeCount.isEmpty() ||
                (!andamentoCheckBox.isSelected() && endingYear.isEmpty())) {
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
        int endingYearInt;
        if (andamentoCheckBox.isSelected()) {
            endingYearInt = 9999; // 9999 representa "Em andamento"
        } else {
            endingYearInt = validateInt(endingYear);
            if (endingYearInt == -1 || endingYearInt < yearInt) {
                fieldEndingYear.clear();
                showAlert(Alert.AlertType.ERROR, "Ano de encerramento inválido. Por favor, insira um ano válido.");
                return;
            }
        }
        int episodeCountInt = validateInt(episodeCount);
        if (episodeCountInt == -1) {
            fieldEpisodeCount.clear();
            showAlert(Alert.AlertType.ERROR, "Número de episódios inválido. Por favor, insira um número válido.");
            return;
        }
        Genres genreE = Genres.valueOf(genre.toUpperCase());
        String[] castBuffer = cast.split(",");
        String[] whereBuffer = whereToWatch.split(",");

        IResult result = seriesService.register(title, yearInt, genreE, endingYearInt, castBuffer, ogTitle,
                whereBuffer, 1, episodeCountInt, yearInt);
        if (result.getClass().equals(Success.class)) {
            showAlert(Alert.AlertType.INFORMATION, "Registrado com sucesso!");
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();;
        } else {
            showAlert(Alert.AlertType.ERROR, result.getMessage());
        }

    }

    /**
     * Método chamado quando o CheckBox de andamento é marcado.
     * <p>
     * Limpa o campo de ano de encerramento quando a série está em andamento.
     */
    @FXML
    public void onCheckBoxMarked() {
        fieldEndingYear.clear();
    }
}

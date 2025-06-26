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
    @FXML private TextField field_ogTitle;

    /**
     * Campo de texto para o elenco da série.
     */
    @FXML private TextArea field_cast;

    /**
     * Campo de texto para os locais onde a série pode ser assistida.
     */
    @FXML private TextArea field_where;

    /**
     * Campo de texto para o ano de encerramento da série.
     */
    @FXML private TextField field_endingYear;

    /**
     * Campo de texto para o número de episódios da primeira temporada.
     */
    @FXML private TextField field_episodeCount;

    /**
     * CheckBox para marcar se a série está em andamento.
     */
    @FXML CheckBox andamentoCheckBox;

    //*********Atributos NAO FXML***********

    /**
     * Serviço de séries utilizado para registrar a série.
     */
    private SeriesService seriesService;

    //***********Metodos*********************

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
     *
     * @param url O local usado para resolver caminhos relativos para o objeto raiz.
     * @param rb O recurso usado para localizar o objeto raiz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        field_ogTitle.setPromptText("Título Original");
        field_cast.setPromptText("Ator1, Ator2, ...");
        field_where.setPromptText("Streaming1, Streaming2, ...");
        field_endingYear.setPromptText("Ano");
        field_episodeCount.setPromptText("N° de Ep.");
        field_endingYear.disableProperty().bind(andamentoCheckBox.selectedProperty());
    }

    /**
     * Metodo chamado quando o botão de registro é clicado.
     * <p>
     * Valida os campos de entrada e registra a série usando o serviço de séries.
     * Exibe mensagens de erro ou sucesso conforme necessário.
     * Sempre registra a primeira temporada com o ano de lançamento da série.
     */
    @Override
    public void onRegisterButtonClicked() {
        String title = field_title.getText();
        String year = field_year.getText();
        String ogTitle = field_ogTitle.getText();
        String cast = field_cast.getText();
        String whereToWatch = field_where.getText();
        String endingYear = field_endingYear.getText();
        String episodeCount = field_episodeCount.getText();
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
            field_year.clear();
            showAlert(Alert.AlertType.ERROR, "Ano inválido. Por favor, insira um ano válido.");
            return;
        }
        int endingYearInt;
        if (andamentoCheckBox.isSelected()) {
            endingYearInt = 9999; // 9999 representa "Em andamento"
        } else {
            endingYearInt = validateInt(endingYear);
            if (endingYearInt == -1 || endingYearInt < yearInt) {
                field_endingYear.clear();
                showAlert(Alert.AlertType.ERROR, "Ano de encerramento inválido. Por favor, insira um ano válido.");
                return;
            }
        }
        int episodeCountInt = validateInt(episodeCount);
        if (episodeCountInt == -1) {
            field_episodeCount.clear();
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
     * Metodo chamado quando o CheckBox de andamento é marcado.
     * <p>
     * Limpa o campo de ano de encerramento quando a série está em andamento.
     */
    @FXML
    public void onCheckBoxMarked() {
        field_endingYear.clear();
    }
}

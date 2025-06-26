package com.davigui.mediajournal.ViewFXControllers.RegisterScreens;

import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.Model.Result.Success;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para a tela de registro de temporadas de séries.
 * <p>
 * Este controlador é responsável por gerenciar a interface de registro de uma nova temporada
 * de uma série, incluindo a validação dos campos e o registro da temporada através do serviço de séries.
 */
public class RegisterSeasonScreenController implements Initializable {

    // *********Atributos FXML******************

    /**
     * Botão para registro de temporada.
     */
    @FXML private Button registerButton;

    /**
     * Campo de texto para o número de episódios da temporada.
     */
    @FXML private TextField field_episodeCount;

    /**
     * Campo de texto para o ano da temporada.
     */
    @FXML private TextField field_year;

    /**
     * Texto que exibe a temporada e o título da série.
     */
    @FXML private Text labelUpper;

    // *********Atributos NAO FXML***********

    /**
     * Serviço de séries utilizado para registrar a temporada.
     */
    private SeriesService service;

    /**
     * Série à qual a temporada pertence.
     */
    private Series series;

    /**
     * Número da temporada a ser registrada.
     */
    private int seasonNumber;

    // *********Métodos *************

    /**
     * Define o serviço de séries a ser utilizado pelo controlador.
     *
     * @param service O serviço de séries a ser utilizado.
     */
    public void setService(SeriesService service) {
        this.service = service;
    }

    /**
     * Define a série à qual a temporada pertence.
     *
     * @param series A série para a qual a temporada será registrada.
     */
    public void setSeries(Series series) {
        this.series = series;
    }

    /**
     * Inicializa os campos da interface gráfica com o número da nova temporada e o título da série.
     * <p>
     * Este método é chamado para configurar a interface antes do usuário registrar uma nova temporada,
     * e deve ser chamado após a inicialização do controlador e antes de exibir a tela de registro.
     */
    public void initFields() {
        this.seasonNumber = series.getNumberOfSeasons() + 1;
        labelUpper.setText("Temporada " + seasonNumber + " de " + series.getTitle());
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * <p>
     * Configura os textos temporários dos campos de texto para o número de episódios e ano.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        field_episodeCount.setPromptText("Número do Episódios");
        field_year.setPromptText("Ano");
    }

    /**
     * Método chamado quando o botão de registro é clicado.
     * <p>
     * Valida os campos de entrada e registra a nova temporada através do serviço de séries.
     * Se a validação falhar, exibe um alerta de erro. Se o registro for bem-sucedido,
     * exibe um alerta de sucesso e fecha a janela de registro.
     */
    @FXML
    public void onRegisterButtonClicked() {
        int episodeCount;
        int year;

        try {
            episodeCount = Integer.parseInt(field_episodeCount.getText());
            year = Integer.parseInt(field_year.getText());
        } catch (NumberFormatException e) {
            field_episodeCount.clear();
            field_year.clear();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, insira números válidos para o número de episódios e ano.");
            alert.showAndWait();
            return;
        }

        IResult result = service.registerSeason(series, seasonNumber, episodeCount, year);

        if (result.getClass().equals(Success.class)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Temporada registrada com sucesso!");
            alert.showAndWait();
            // Fechar a janela de registro
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(result.getMessage());
            alert.showAndWait();
        }

    }

}

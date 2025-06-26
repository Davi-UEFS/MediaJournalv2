package com.davigui.mediajournal.ViewFXControllers.RateScreens;

import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Medias.Season;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.Model.Result.IResult;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para a tela de avaliação de temporadas de séries.
 * <p>
 * Este controlador é responsável por gerenciar a interface de avaliação da temporada
 * de uma série, permitindo ao usuário atribuir uma nota e escrever uma resenha.
 */
public class RateSeasonScreenController implements Initializable {

    // *********Atributos FXML******************

    /**
     * Slider para selecionar a nota da temporada.
     */
    @FXML private Slider rateSlider;

    /**
     * Campo de texto para escrever uma resenha sobre a temporada.
     */
    @FXML private TextArea reviewField;

    /**
     * Botão para salvar a avaliação e a resenha.
     */
    @FXML private Button saveButton;

    //*********Atributos NAO FXML***********

    /**
     * Serviço de séries utilizado para registrar a avaliação e a resenha da temporada.
     */
    private SeriesService service;

    /**
     * Temporada a ser avaliada.
     */
    private Season season;

    /**
     * Série à qual a temporada pertence.
     */
    private Series series;

    //*********Métodos *************

    /**
     * Define o serviço de séries a ser utilizado.
     *
     * @param service Serviço de séries.
     */
    public void setService(SeriesService service) {
        this.service = service;
    }

    /**
     * Define a temporada a ser avaliada.
     *
     * @param season Temporada a ser avaliada.
     */
    public void setSeason(Season season) {
        this.season = season;
    }

    /**
     * Define a série à qual a temporada pertence.
     *
     * @param series Série para a qual a temporada será avaliada.
     */
    public void setSeries(Series series) {
        this.series = series;
    }

    /**
     * Inicializa os componentes da interface gráfica.
     * <p>
     * Configura o slider de avaliação e o campo de texto para a resenha.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurações iniciais do slider e textarea
        rateSlider.setMin(0);
        rateSlider.setMax(5);
        rateSlider.setValue(0);
        reviewField.setText("");
    }

    /**
     * Método chamado quando o botão de salvar é clicado.
     * <p>
     * Registra a avaliação e a resenha da temporada, exibe uma mensagem de confirmação
     * e fecha a janela atual.
     */
    @FXML
    public void onSaveButtonClicked() {
        IResult result1 = service.rateSeason(series, season.getSeasonNumber(), (int) rateSlider.getValue());
        IResult result2 = service.writeReviewSeason(series, season.getSeasonNumber(), reviewField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(result1.getMessage() + "\n" + result2.getMessage());
        alert.showAndWait();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Inicializa os campos de avaliação com os valores atuais da temporada.
     * <p>
     * Este método deve ser chamado após a definição da temporada a ser avaliada,
     * para que os campos sejam preenchidos com as informações corretas.
     */
    @FXML
    public void initFields() {
        rateSlider.setValue(season.getRating());
        reviewField.setText(season.getReview());
    }
}

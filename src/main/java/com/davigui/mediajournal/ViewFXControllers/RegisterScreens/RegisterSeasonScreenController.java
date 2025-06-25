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

public class RegisterSeasonScreenController implements Initializable {

    // *********Atributos FXML******************
    @FXML private Button registerButton;
    @FXML private TextField field_episodeCount;
    @FXML private TextField field_year;
    @FXML private Text labelUpper;

    // *********Atributos NAO FXML***********
    private SeriesService service;
    private Series series;
    private int seasonNumber;

    // *********Metodos *************

    public void setService(SeriesService service) {
        this.service = service;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public void initFields() {
        this.seasonNumber = series.getNumberOfSeasons() + 1;
        labelUpper.setText("Temporada " + seasonNumber + " de " + series.getTitle());
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        field_episodeCount.setPromptText("Número do Episódios");
        field_year.setPromptText("Ano");
    }

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

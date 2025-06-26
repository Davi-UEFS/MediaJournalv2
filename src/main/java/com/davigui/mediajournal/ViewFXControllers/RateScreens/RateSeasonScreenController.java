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

public class RateSeasonScreenController implements Initializable {

    // *********Atributos FXML******************
    @FXML private Slider rateSlider;
    @FXML private TextArea reviewField;
    @FXML private Button saveButton;

    //*********Atributos NAO FXML***********
    private SeriesService service;
    private Season season;
    private Series series;

    //*********Metodos *************

    public void setService(SeriesService service) {
        this.service = service;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurações iniciais do slider e textarea
        rateSlider.setMin(0);
        rateSlider.setMax(5);
        rateSlider.setValue(0);
        reviewField.setText("");
    }

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

    @FXML
    public void initFields() {
        rateSlider.setValue(season.getRating());
        reviewField.setText(season.getReview());
    }
}

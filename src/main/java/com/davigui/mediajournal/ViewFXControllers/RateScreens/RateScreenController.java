package com.davigui.mediajournal.ViewFXControllers.RateScreens;

import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Model.Medias.Media;
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

public class RateScreenController<T extends Media> implements Initializable {

    // *********Atributos FXML******************
    @FXML private Slider rateSlider;
    @FXML private TextArea reviewField;
    @FXML private Button saveButton;

    //*********Atributos NAO FXML***********
    private CommonService<T> service;
    private T media;

    //*********Metodos *************

    public void setService(CommonService<T> service) {
        this.service = service;
    }

    public void setMedia(T media) {
        this.media = media;
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
        IResult result1 = service.rate(media, (int) rateSlider.getValue());
        System.out.println(result1.getMessage());
        IResult result2 = service.writeReview(media, reviewField.getText());
        System.out.println(result2.getMessage());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(result1.getMessage() + "\n" + result2.getMessage());
        alert.showAndWait();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initFields() {
        rateSlider.setValue(media.getRating());
        reviewField.setText(media.getReview());
    }
}

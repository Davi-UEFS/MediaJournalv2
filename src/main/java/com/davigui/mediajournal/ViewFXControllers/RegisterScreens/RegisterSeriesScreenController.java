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

public class RegisterSeriesScreenController extends RegisterScreenController{

    //*********Atributos FXML******************
    @FXML private TextField field_ogTitle;
    @FXML private TextArea field_cast;
    @FXML private TextArea field_where;
    @FXML private TextField field_endingYear;
    @FXML private TextField field_episodeCount;
    @FXML CheckBox andamentoCheckBox;

    //*********Atributos NAO FXML***********
    private SeriesService seriesService;

    //***********Metodos*********************
    public void setService(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

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

    @FXML
    public void onCheckBoxMarked() {
        field_endingYear.clear();
    }
}

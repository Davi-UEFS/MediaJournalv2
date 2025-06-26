package com.davigui.mediajournal.ViewFXControllers.RateScreens;

import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Model.Enums.Months;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.Model.Result.Success;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SeenMovieScreenController implements Initializable {

    // *********Atributos FXML******************
    @FXML Button saveButton;
    @FXML DatePicker datePicker;

    //*********Atributos NAO FXML***********
    MovieService service;
    Movie movie;

    //*********Metodos *************
    public void setService(MovieService service) {
        this.service = service;
    }

    public void setMedia(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurações iniciais do DatePicker
        datePicker.setValue(java.time.LocalDate.now());
    }

    public void onSaveButtonClicked() {
        LocalDate pickedDate = datePicker.getValue();
        int day = pickedDate.getDayOfMonth();
        int month = pickedDate.getMonthValue();
        int year = pickedDate.getYear();

        LocalDate now = LocalDate.now();
        int currentDay = now.getDayOfMonth();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        if (year > currentYear || (year == currentYear && month > currentMonth)
                || (year == currentYear && month == currentMonth && day > currentDay)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText("A data não pode ser futura.");
            alert.showAndWait();
            return;
        }

        Months monthE = Months.values()[month - 1];

        IResult result = service.markAsSeen(movie, year, monthE);

        if (result.getClass().equals(Success.class)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Filme marcado como visto");
            alert.setContentText("O filme foi marcado como visto com sucesso!");
            alert.showAndWait();

            // Fecha a janela atual
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao marcar filme como visto");
            alert.setContentText(result.getMessage());
            alert.showAndWait();
        }
    }
}

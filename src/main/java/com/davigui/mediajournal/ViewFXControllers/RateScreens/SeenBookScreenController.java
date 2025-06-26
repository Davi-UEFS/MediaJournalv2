package com.davigui.mediajournal.ViewFXControllers.RateScreens;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Model.Enums.Months;
import com.davigui.mediajournal.Model.Medias.Book;
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

public class SeenBookScreenController implements Initializable {

    // *********Atributos FXML******************
    @FXML Button saveButton;
    @FXML DatePicker datePicker;

    //*********Atributos NAO FXML***********
    BookService service;
    Book book;

    //*********Metodos *************
    public void setService(BookService service) {
        this.service = service;
    }

    public void setMedia(Book book) {
        this.book = book;
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

        IResult result = service.markAsSeen(book, year, monthE);

        if (result.getClass().equals(Success.class)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Livro marcado como lido");
            alert.setContentText("O livro foi marcado como lido com sucesso!");
            alert.showAndWait();

            // Fecha a janela atual
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao marcar livro como lido");
            alert.setContentText(result.getMessage());
            alert.showAndWait();
        }
    }
}

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

/**
 * Controlador para a tela de marcação de filmes como vistos.
 * <p>
 * Este controlador é responsável por gerenciar a interface de marcação de um filme como visto,
 * permitindo ao usuário selecionar uma data e salvar essa informação.
 */
public class SeenMovieScreenController implements Initializable {

    // *********Atributos FXML******************

    /**
     * Botão para salvar as alterações.
     */
    @FXML Button saveButton;

    /**
     * Seletor de data para escolher a data em que o filme foi visto.
     */
    @FXML DatePicker datePicker;

    //*********Atributos NAO FXML***********

    /**
     * Serviço de filmes utilizado para marcar o filme como visto.
     */
    MovieService service;

    /**
     * Filme que será marcado como visto.
     */
    Movie movie;

    //*********Métodos *************

    /**
     * Define o serviço de filmes a ser utilizado.
     *
     * @param service O serviço de filmes.
     */
    public void setService(MovieService service) {
        this.service = service;
    }

    /**
     * Define o filme que será marcado como visto.
     *
     * @param movie O filme a ser marcado como visto.
     */
    public void setMedia(Movie movie) {
        this.movie = movie;
    }

    /**
     * Método chamado ao inicializar o controlador.
     * <p>
     * Configura o DatePicker para a data atual.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurações iniciais do DatePicker
        datePicker.setValue(java.time.LocalDate.now());
    }

    /**
     * Método chamado quando o botão de salvar é clicado.
     * <p>
     * Verifica se a data selecionada é válida (não pode ser futura) e, se for,
     * marca o filme como lido no serviço de filmes.
     * Se a operação for bem-sucedida, exibe uma mensagem de sucesso e fecha a janela.
     * Se ocorrer um erro, exibe uma mensagem de erro.
     */
    public void onSaveButtonClicked() {
        LocalDate pickedDate = datePicker.getValue();

        if (pickedDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText("Selecione uma data no formato DD/MM/AAAA.");
            alert.showAndWait();
            return;
        }

        LocalDate now = LocalDate.now();

        if (pickedDate.isAfter(now)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Data inválida");
            alert.setContentText("A data não pode ser futura.");
            alert.showAndWait();
            return;
        }

        int month = pickedDate.getMonthValue();
        int year = pickedDate.getYear();

        Months monthE = Months.values()[month - 1];

        IResult result = service.markAsSeen(movie, year, monthE);

        if (result.getClass().equals(Success.class)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Filme marcado como lido");
            alert.setContentText("O filme foi marcado como lido com sucesso!");
            alert.showAndWait();

            // Fecha a janela atual
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao marcar filme como lido");
            alert.setContentText(result.getMessage());
            alert.showAndWait();
        }
    }
}

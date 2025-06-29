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

/**
 * Controlador para a tela de marcação de livros como lidos.
 * <p>
 * Este controlador é responsável por gerenciar a interface de marcação de um livro como lido,
 * permitindo ao usuário selecionar uma data e salvar essa informação.
 */
public class SeenBookScreenController implements Initializable {

    // *********Atributos FXML******************

    /**
     * Botão para salvar as alterações.
     */
    @FXML Button saveButton;

    /**
     * Seletor de data para escolher a data em que o livro foi lido.
     */
    @FXML DatePicker datePicker;

    //*********Atributos NAO FXML***********

    /**
     * Serviço de livros utilizado para marcar o livro como lido.
     */
    BookService service;

    /**
     * Livro que será marcado como lido.
     */
    Book book;

    //*********Métodos *************

    /**
     * Define o serviço de livros a ser utilizado.
     *
     * @param service O serviço de livros.
     */
    public void setService(BookService service) {
        this.service = service;
    }

    /**
     * Define o livro que será marcado como lido.
     *
     * @param book O livro a ser marcado como lido.
     */
    public void setMedia(Book book) {
        this.book = book;
    }

    /**
     * Inicializa os componentes da interface gráfica.
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
     * marca o livro como lido no serviço de livros.
     * Se a operação for bem-sucedida, exibe uma mensagem de sucesso e fecha a janela.
     * Se ocorrer um erro, exibe uma mensagem de erro.
     */
    public void onSaveButtonClicked() {
        LocalDate pickedDate = datePicker.getValue();
        int day = pickedDate.getDayOfMonth();
        int month = pickedDate.getMonthValue();
        int year = pickedDate.getYear();

        LocalDate now = LocalDate.now();
        int currentDay = now.getDayOfMonth();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        //TODO: USAR ALGUM METODO DE LOCALDATE?

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

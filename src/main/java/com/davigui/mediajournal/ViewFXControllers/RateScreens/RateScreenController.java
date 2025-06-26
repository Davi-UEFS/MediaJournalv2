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

/**
 * Controlador para a tela de avaliação de mídias.
 * <p>
 * Gerencia a interface de avaliação, permitindo ao usuário atribuir uma nota e escrever uma resenha
 * para uma mídia específica (livro ou filme). Permite a implementação tanto com o serviço de livros
 * quanto com o de filmes, cuja lógica de avaliação é similar.
 * @param <T> Tipo de mídia a ser avaliada, que deve ser {@code Book} ou {@code Movie}.
 */
public class RateScreenController<T extends Media> implements Initializable {

    // *********Atributos FXML******************

    /**
     * Slider para selecionar a nota da obra.
     */
    @FXML private Slider rateSlider;

    /**
     * Campo de texto para escrever uma resenha sobre a obra.
     */
    @FXML private TextArea reviewField;

    /**
     * Botão para salvar a avaliação e a resenha.
     */
    @FXML private Button saveButton;

    //*********Atributos NAO FXML***********

    /**
     * Serviço comum utilizado para registrar a avaliação e a resenha da obra.
     * <p>
     * Pode ser um serviço de livros {@code BookService} ou de filmes {@code MovieService}.
     */
    private CommonService<T> service;

    /**
     * Mídia a ser avaliada, que pode ser um livro ou um filme.
     * <p>
     * Este atributo é do tipo genérico {@code T}, que deve estender {@code Media},
     * mais especificamente, deve ser {@code Book} ou {@code Movie}.
     */
    private T media;

    //*********Métodos *************

    /**
     * Define o serviço a ser utilizado pelo controlador.
     *
     * @param service O serviço a ser utilizado.
     */
    public void setService(CommonService<T> service) {
        this.service = service;
    }

    /**
     * Define a obra a ser avaliada.
     *
     * @param media A obra a ser avaliada, que deve ser do tipo {@code Book} ou {@code Movie}.
     */
    public void setMedia(T media) {
        this.media = media;
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
     * Registra a avaliação e a resenha da obra, exibe uma mensagem de confirmação
     * e fecha a janela atual.
     */
    @FXML
    public void onSaveButtonClicked() {
        IResult result1 = service.rate(media, (int) rateSlider.getValue());
        IResult result2 = service.writeReview(media, reviewField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(result1.getMessage() + "\n" + result2.getMessage());
        alert.showAndWait();
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Inicializa os campos de avaliação com os valores atuais da obra.
     * <p>
     * Define o valor do slider de avaliação e o texto do campo de resenha
     * com base nos dados da obra, permitindo ao usuário revisar o que já escreveu.
     */
    @FXML
    public void initFields() {
        rateSlider.setValue(media.getRating());
        reviewField.setText(media.getReview());
    }
}

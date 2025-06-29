package com.davigui.mediajournal.ViewFXControllers.RegisterScreens;

import com.davigui.mediajournal.Model.Enums.Genres;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe abstrata que serve como controlador base para telas de registro.
 * <p>
 * Implementa a interface Initializable do JavaFX para inicialização dos componentes.
 * Define campos comuns e métodos básicos para telas de registro de mídias.
 */
public abstract class RegisterScreenController implements Initializable {

    //*********Atributos FXML******************

    /**
     * Campo de texto para o título da obra.
     */
    @FXML protected TextField fieldTitle;

    /**
     * Campo de texto para o ano de lançamento da obra.
     */
    @FXML protected TextField fieldYear;

    /**
     * ComboBox para seleção do gênero da obra.
     */
    @FXML protected ComboBox<Genres> genreBox;

    /**
     * Botão para registrar a obra.
     */
    @FXML protected Button registerButton;

    //***********Métodos Comuns e Assinaturas*********************

    /**
     * Inicializa os componentes da interface gráfica.
     * <p>
     * Configura os textos temporários dos campos de texto e preenche o
     * ComboBox de gêneros com os gêneros disponíveis.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        fieldTitle.setPromptText("Título");
        fieldYear.setPromptText("Ano");

        genreBox.setPromptText("- Gênero -");
        List<Genres> genreChoices = Arrays.asList(Genres.values());
        genreBox.setItems(FXCollections.observableList(genreChoices));

    }

    /**
     * Assinatura do método para lidar com o clique no botão de registro.
     * Deve ser implementado pelas classes concretas que herdam desta.
     */
    @FXML abstract public void onRegisterButtonClicked();

    //**********Métodos de Validaçãp*************

    /**
     * Valida se uma string pode ser convertida em um inteiro válido, e
     * retorna esse inteiro ou um valor negativo.
     *
     * @param string A string a ser validada como número.
     * @return O número convertido ou -1 se for inválido.
     */
    @FXML
    public int parseAndValidateInt(String string) {
        int num;
        try {
            num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (num > LocalDate.now().getYear()) return -1;
        return num;
    }

    /**
     * Método estático para exibir um alerta com o tipo e a mensagem fornecidos.
     *
     * @param alertType O tipo de alerta a ser exibido (INFORMATION, ERROR, etc.).
     * @param message A mensagem a ser exibida no alerta.
     */
    @FXML
    public static void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }

}

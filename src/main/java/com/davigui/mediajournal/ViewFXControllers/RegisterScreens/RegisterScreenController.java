package com.davigui.mediajournal.ViewFXControllers.RegisterScreens;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
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
    @FXML protected TextField field_title;

    /**
     * Campo de texto para o autor da obra.
     */
    @FXML protected TextField field_author;

    /**
     * Campo de texto para o ano de lançamento da obra.
     */
    @FXML protected TextField field_year;

    /**
     * ComboBox para seleção do gênero da obra.
     */
    @FXML protected ComboBox<String> genreBox;

    /**
     * Botão para registrar a obra.
     */
    @FXML protected Button registerButton;

    //***********Metodos Comuns e Assinaturas*********************

    /**
     * Inicializa os componentes da interface gráfica.
     * <p>
     * Configura os textos temporários dos campos de texto e preenche o
     * ComboBox de gêneros com os gêneros disponíveis.
     *
     * @param url O local usado para resolver caminhos relativos para o objeto raiz.
     * @param rb O recurso usado para localizar o objeto raiz.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        field_title.setPromptText("Título");
        field_author.setPromptText("Autor");
        field_year.setPromptText("Ano");

        genreBox.getItems().addAll("- Gênero -", "Terror", "Ação", "Aventura",
                "Suspense", "Romance", "Ficção", "Esportes", "Comédia",
                "Mistério", "Criminal", "Infantil", "Outros"
        );
        genreBox.getSelectionModel().selectFirst();
    }

    /**
     * Assinatura do metodo para lidar com o clique no botão de registro.
     * Deve ser implementado pelas classes concretas que herdam desta.
     */
    @FXML abstract public void onRegisterButtonClicked();

    //**********Metodos de Validaçãp*************

    /**
     * Valida se uma string pode ser convertida em um inteiro válido, e
     * retorna esse inteiro ou um valor negativo.
     *
     * @param string A string a ser validada como número.
     * @return O número convertido ou -1 se for inválido.
     */
    @FXML
    public int validateInt(String string) {
        int num;
        try {
            num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (num > 2025) return -1;
        return num;
    }

    /**
     * Metodo estático para exibir um alerta com o tipo e a mensagem fornecidos.
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

package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.MainFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controlador da tela de opções da aplicação.
 * <p>
 * Gerencia as configurações de áudio, como tocar/pausar música, ajustar volume e
 * selecionar a música a ser reproduzida.
 */
public class OptionsTabController implements Initializable {

    //*********Atributos FXML ************

    /**
     * Rótulos para exibir o estado do áudio.
     */
    @FXML
    private Label labelOnOff;

    /**
     * Rótulo para exibir o volume atual.
     */
    @FXML
    private Label labelVolume;

    /**
     * Botão para tocar ou pausar a música.
     */
    @FXML
    private ToggleButton toggleMusic;

    /**
     * Slider para ajustar o volume da música.
     */
    @FXML
    private Slider sliderVolume;

    /**
     * Caixa de seleção para escolher a música a ser reproduzida.
     */
    @FXML
    private ChoiceBox<String> choiceSong;

    //*********Atributos NAO FXML ************

    /**
     * Player de mídia utilizado para reproduzir a música selecionada.
     */
    private MediaPlayer mediaPlayer;

    //*********Métodos *************

    /**
     * Método chamado ao clicar no botão de tocar/pausar música.
     * <p>
     * Inicia ou pausa a reprodução da música dependendo do estado do botão.
     */
    @FXML
    public void onToggleMusic() {
        if (mediaPlayer != null) {
            if (toggleMusic.isSelected()) {
                mediaPlayer.play();
                labelOnOff.setText("(Ligada)");
            } else {
                mediaPlayer.pause();
                labelOnOff.setText("(Desligada)");
            }
        }
    }

    /**
     * Método chamado ao ajustar o volume do slider.
     * <p>
     * Atualiza o volume do player de mídia com base no valor do slider.
     * Atualiza o texto do rótulo de volume com o valor atual do slider.
     */
    @FXML
    public void onUpdateVolumeValue() {
        if (mediaPlayer != null) {
            double volume = sliderVolume.getValue();
            labelVolume.setText(String.format("%.0f%%", volume));
            mediaPlayer.setVolume(volume/100.0);
        }
    }

    /**
     * Método chamado ao atualizar a seleção da música.
     * <p>
     * Encerra a reprodução atual, carrega a nova música selecionada e inicia a reprodução
     * se o botão de tocar estiver ativo.
     */
    @FXML
    public void onUpdateCurrentSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String fileName = switch (choiceSong.getValue()) {
            case "Kevin MacLeod - Local Forecast" -> "audio/song1.mp3";
            case "BuGuMi - Today's Diary" -> "audio/song2.mp3";
            default -> "audio/song1.mp3";
        };


        Media media = new Media(Objects.requireNonNull(MainFX.class.getResource(fileName)).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(sliderVolume.getValue() / 100.0);

        if (toggleMusic.isSelected()) {
            mediaPlayer.play();
        }
    }

    /**
     * Método chamado ao inicializar o controlador.
     * <p>
     * Configura os itens do ChoiceBox, define o volume inicial e carrega a música padrão.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceSong.getItems().addAll("Kevin MacLeod - Local Forecast", "BuGuMi - Today's diary");
        choiceSong.getSelectionModel().selectFirst();
        sliderVolume.setValue(100);
        labelVolume.setText("100%");
        labelOnOff.setText("(Desligada)");

        // Carrega a música inicial
        String fileName = "audio/song1.mp3";
        Media media = new Media(Objects.requireNonNull(MainFX.class.getResource(fileName)).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(sliderVolume.getValue() / 100.0);
    }
}

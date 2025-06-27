package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.MainFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
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
public class OptionsController implements Initializable {

    //*********Atributos FXML ************

    /**
     * Botão para tocar ou pausar a música.
     */
    @FXML
    private ToggleButton toggle_music;

    /**
     * Slider para ajustar o volume da música.
     */
    @FXML
    private Slider slider_volume;

    /**
     * Caixa de seleção para escolher a música a ser reproduzida.
     */
    @FXML
    private ChoiceBox<String> choice_song;

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
    public void onPlayAudio() {
        if (mediaPlayer != null) {
            if (toggle_music.isSelected()) {
                mediaPlayer.play();
            } else {
                mediaPlayer.pause();
            }
        }
    }

    /**
     * Método chamado ao ajustar o volume do slider.
     * <p>
     * Atualiza o volume do player de mídia com base no valor do slider.
     */
    @FXML
    public void onUpdateVolume() {
        if (mediaPlayer != null) {
            double volume = slider_volume.getValue() / 100.0;
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Método chamado ao atualizar a seleção da música.
     * <p>
     * Encerra a reprodução atual, carrega a nova música selecionada e inicia a reprodução
     * se o botão de tocar estiver ativo.
     */
    @FXML
    public void onSongUpdated() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String fileName = switch (choice_song.getValue()) {
            case "Música 1" -> "audio/song1.mp3";
            case "Música 2" -> "audio/song2.mp3";
            default -> "audio/song1.mp3";
        };


        Media media = new Media(Objects.requireNonNull(MainFX.class.getResource(fileName)).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(slider_volume.getValue() / 100.0);

        if (toggle_music.isSelected()) {
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
        choice_song.getItems().addAll("Música 1", "Música 2");
        choice_song.getSelectionModel().selectFirst();
        slider_volume.setValue(100);

        // Carrega a música inicial
        String fileName = "audio/song1.mp3";
        Media media = new Media(Objects.requireNonNull(MainFX.class.getResource(fileName)).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(slider_volume.getValue() / 100.0);
    }
}

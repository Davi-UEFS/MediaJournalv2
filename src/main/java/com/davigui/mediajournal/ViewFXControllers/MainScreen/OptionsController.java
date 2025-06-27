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

public class OptionsController implements Initializable {

    //*********Atributos FXML ************
    @FXML
    private ToggleButton toggle_music;
    @FXML
    private Slider slider_volume;
    @FXML
    private ChoiceBox<String> choice_song;

    //*********Atributos NAO FXML ************
    private MediaPlayer mediaPlayer;

    //*********Métodos *************
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

    @FXML
    public void onUpdateVolume() {
        if (mediaPlayer != null) {
            double volume = slider_volume.getValue() / 100.0;
            mediaPlayer.setVolume(volume);
        }
    }

    @FXML
    public void onSongUpdated() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        String fileName = switch (choice_song.getValue()) {

            case "Música 1" -> "audio/song1.mp3";

            case "Música 2" -> "audio/song2.mp3";

            default -> throw new IllegalArgumentException("Música inválida.");
        };

        Media media = new Media(Objects.requireNonNull(MainFX.class.getResource(fileName)).toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(slider_volume.getValue() / 100.0);

        if (toggle_music.isSelected()) {
            mediaPlayer.play();
        }
    }

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

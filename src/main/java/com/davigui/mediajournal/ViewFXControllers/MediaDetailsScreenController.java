package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Model.Medias.Book;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MediaDetailsScreenController implements Initializable {

    @FXML
    private VBox mediaDetailVbox;

    @FXML
    private ImageView mediaImageView;

    @FXML
    private Label mediaTitleYear;

    @FXML
    private Label mediaGenre;

    @FXML
    private Label mediaCast;

    @FXML
    private Label mediaSeasons;

    @FXML
    private Label mediaRating;

    @FXML
    private Label mediaReview;

    @FXML
    private Label mediaWhereToWatch;

    public void initialize(URL url, ResourceBundle rb){
        mediaCast.setVisible(false);
        mediaSeasons.setVisible(false);
        mediaWhereToWatch.setVisible(false);
    }

    private void disableSpecificAInfo(){
        mediaCast.setVisible(false);
        mediaCast.setManaged(false);
        mediaSeasons.setVisible(false);
        mediaSeasons.setManaged(false);
        mediaWhereToWatch.setVisible(false);
        mediaWhereToWatch.setManaged(false);    }

    public void handleBookInfo(Book book){
        mediaTitleYear.setText(book.getTitle() + " (" + book.getYear() + ")");
        mediaGenre.setText(book.getGenre().toString());

        if(book.getReview().isEmpty())
            mediaReview.setText("Sem resenha atribuita");

        else
            mediaReview.setText(book.getReview());

    }

}

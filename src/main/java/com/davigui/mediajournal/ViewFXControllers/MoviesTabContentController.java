package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.MovieService; // Assumindo que você tem um MovieService
import com.davigui.mediajournal.Model.Enums.Genres; // Reutilizando Genres se aplicável a filmes
import com.davigui.mediajournal.Model.Medias.Movie; // Assumindo sua classe Movie
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MoviesTabContentController implements Initializable {

    @FXML
    private TableColumn<Movie, String> movieDirectorColumn;

    @FXML
    private TableColumn<Movie, Integer> movieDurationColumn;

    @FXML
    private ImageView movieEditButton;

    @FXML
    private ImageView movieFilterButton;

    @FXML
    private TextField movieFilterTextField;

    @FXML
    private ChoiceBox<String> movieFilterTypeChoiceBox;

    @FXML
    private ChoiceBox<Genres> movieGenreChoiceBox;

    @FXML
    private TableColumn<Movie, String> movieOriginalTitleColumn;

    @FXML
    private ImageView movieRateButton;

    @FXML
    private TableColumn<Movie, Integer> movieRatingColumn;

    @FXML
    private ImageView movieRemoveButton;

    @FXML
    private TableColumn<Movie, String> movieSeenDateColumn;

    @FXML
    private Label movieTableLabel;

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TableColumn<Movie, String> movieTitleColumn;

    @FXML
    private TableColumn<Movie, Integer> movieYearColumn;

    @FXML
    private VBox moviesTabVbox;

    private MovieService movieService;

    private ObservableList<Movie> movieObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb){

        movieTitleColumn.setCellValueFactory(cellData->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        movieYearColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getYear())).asObject());

        movieRatingColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getRating())).asObject());

        movieDirectorColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getDirection()));

        movieDurationColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getDuration()).asObject())); // Assumindo getDuration() retorna String

        movieOriginalTitleColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getOriginalTitle()));

        movieSeenDateColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getSeenDate()));
    }

    public void loadMovieList(){
        List<Movie> movies = movieService.getAllMovies();
        this.movieObservableList = FXCollections.observableArrayList(movies);
        movieTableView.setItems(movieObservableList);
    }

    public void setMovieService(MovieService movieService){
        this.movieService = movieService;

    }
}
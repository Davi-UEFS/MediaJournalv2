package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.MainFX;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterMovieScreenController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoviesTabContentController extends MediaContentController<Movie> implements Initializable {

    // *********Atributos FXML******************
    @FXML
    private TableColumn<Movie, String> originalTitleColumn;

    @FXML
    private TableColumn<Movie, String> directionColumn;

    @FXML
    private TableColumn<Movie, Integer> durationColumn;

    @FXML
    private TableColumn<Movie, String> seenDateColumn;

    @FXML
    private Label whereToWatchInfo;

    @FXML
    private Label castInfo;

    @FXML
    private Label scriptInfo;

    @FXML
    private Label durationInfo;

    //*********Atributos NAO FXML***********
    //TODO: EXPLICITAR POSSIVEL ERRO DE CAST NO JAVADOC
    private MovieService movieService = (MovieService) service;

    //***********Metodos*********************


    @Override
    protected void setService(CommonService<Movie> service) {
        this.service = service;
        this.movieService = (MovieService) service;
    }

    @Override
    protected void configureTable() {
        titleColumn.setCellValueFactory(cellData->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        yearColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getYear())).asObject());

        ratingColumn.setCellValueFactory(celldata->
                new SimpleStringProperty("★".repeat(celldata.getValue().getRating())));

        directionColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getDirection()));

        durationColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getDuration()).asObject())); // Assumindo getDuration() retorna String

        originalTitleColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getOriginalTitle()));

        seenDateColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getSeenDate()));
    }

    private void actorSearch(String filter){
        mediaObservableList.setAll(movieService.searchByActor(filter));
    }

    private void directorSearch(String filter){
        mediaObservableList.setAll(movieService.searchByDirector(filter));
    }

    @Override
    protected void configureFilterChoices() {
        List<String> filterChoices = new ArrayList<>();
        filterChoices.add("Título");
        filterChoices.add("Ano");
        filterChoices.add("Gênero");
        filterChoices.add("Diretor");
        filterChoices.add("Ator");

        filterTypeChoiceBox.setItems(FXCollections.observableArrayList(filterChoices));
    }

    @Override
    protected void handleSpecificSearch(String filter) {
        switch (selectedFilter.getValue()){
            case "Ator":
                actorSearch(filter);
                break;

            case "Diretor":
                directorSearch(filter);
                break;
        }
    }

    @Override
    protected void handleMediaInfo(Movie movie) {
        titleYearInfo.setText(movie.getTitle() + " (" + movie.getYear() + ")");
        genreInfo.setText(movie.getGenre().toString());
        ratingInfo.setText("★".repeat(movie.getRating()));
        if (movie.getReview() == null)
            reviewInfo.setText("RESENHA: Sem resenha atribuida");
        else
            reviewInfo.setText("RESENHA: " + movie.getReview());

        durationInfo.setText("Duração: " + movie.getDuration() + "min");
        castInfo.setText("Elenco: " + movie.getCast().toString());
        scriptInfo.setText("Roteiro: " + movie.getScript());
        whereToWatchInfo.setText("Plataformas: " + movie.getWhereToWatch().toString());
    }

    @FXML
    public void onAddButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RegisterMovieScreen.fxml"));
        Parent root = loader.load();

        RegisterMovieScreenController controller = loader.getController();
        controller.setService(movieService);

        Stage stage = new Stage();
        Scene scene = new Scene(root, 660, 430);
        stage.setTitle("Registro de Filme");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/movie_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> loadMediaList());
        stage.show();
    }

    @Override
    public void onRemoveButtonClicked() {
        // Implementação do método para remover um livro
        Movie selectedMovie = selectedItem.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Remoção");
        alert.setHeaderText("Deseja realmente remover o filme " + selectedMovie.getTitle() + " ?");
        alert.setContentText("O filme será permanentemente removido.");
        ButtonType buttonContinuar = new ButtonType("Continuar");
        ButtonType buttonCancelar = ButtonType.CANCEL;

        alert.getButtonTypes().setAll(buttonContinuar, buttonCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonContinuar) {
            movieService.deleteMovie(selectedMovie);
            loadMediaList(); // Recarrega a lista de mídias após a remoção
            System.out.println("Filme removido: " + selectedMovie.getTitle());
        }
        // Se o usuário cancelar, não faz nada
    }
}
package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Series;
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

public class SeriesTabContentController implements Initializable {

    @FXML
    private ImageView seriesEditButton;

    @FXML
    private TableColumn<Series, Integer> seriesEndingYearColumn;

    @FXML
    private Button seriesFilterButton;

    @FXML
    private TextField seriesFilterTextField;

    @FXML
    private ChoiceBox<String> seriesFilterTypeChoiceBox;

    @FXML
    private ChoiceBox<Genres> seriesGenreChoiceBox;

    @FXML
    private TableColumn<Series, String> seriesOriginalTitleColumn;

    @FXML
    private Button seriesRateButton;

    @FXML
    private TableColumn<Series, Integer> seriesRatingColumn;

    @FXML
    private Button seriesRemoveButton;

    @FXML
    private TableColumn<Series, Integer> seriesSeasonNumberColumn;

    //TODO: DECIDIR SE VOU IMPLEMENTAR ISSO
    @FXML
    private TableColumn<Series, String> seriesSeenDateColumn;

    @FXML
    private VBox seriesTabVbox;

    @FXML
    private Label seriesTableLabel;

    @FXML
    private TableView<Series> seriesTableView;

    @FXML
    private TableColumn<Series, String> seriesTitleColumn;

    @FXML
    private TableColumn<Series, Integer> seriesYearColumn;

    private SeriesService seriesService;

    private ObservableList<Series> seriesObservableList;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        seriesTitleColumn.setCellValueFactory(cellData->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        seriesYearColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getYear())).asObject());

        seriesEndingYearColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getYearOfEnding())).asObject());

        seriesRatingColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getRating())).asObject());

        seriesOriginalTitleColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getOriginalTitle()));

        seriesSeasonNumberColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getNumberOfSeasons())).asObject());

    }

    public void loadSeriesList(){
        List<Series> series = seriesService.getAllSeries();
        this.seriesObservableList = FXCollections.observableArrayList(series);
        seriesTableView.setItems(seriesObservableList);
    }

    public void setSeriesService(SeriesService seriesService){
        this.seriesService = seriesService;

    }
}
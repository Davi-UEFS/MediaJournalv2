package com.davigui.mediajournal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MainScreenController {

    @FXML
    private TableColumn<?, ?> bookAuthorColumn;

    @FXML
    private TableColumn<?, ?> bookIsbnColumn;

    @FXML
    private Label bookLabel;

    @FXML
    private TableColumn<?, ?> bookPublisherColumn;

    @FXML
    private TableColumn<?, ?> bookRatingColumn;

    @FXML
    private TableColumn<?, ?> bookSeenDateColumn;

    @FXML
    private TableView<?> bookTableView;

    @FXML
    private TableColumn<?, ?> bookTitleColumn;

    @FXML
    private TableColumn<?, ?> bookYearColumn;

    @FXML
    private Tab booksTab;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private Label mediaCast;

    @FXML
    private Label mediaGenre;

    @FXML
    private ImageView mediaImageView;

    @FXML
    private VBox mediaInfoVbox;

    @FXML
    private Label mediaRating;

    @FXML
    private Label mediaReview;

    @FXML
    private Label mediaSeasons;

    @FXML
    private Label mediaTitleYear;

    @FXML
    private Label mediaWhereToWatch;

    @FXML
    private TabPane mediasTabPane;

    @FXML
    private TableColumn<?, ?> movieDirectorColumn;

    @FXML
    private TableColumn<?, ?> movieDurationColumn;

    @FXML
    private TableColumn<?, ?> movieOriginalTitleColumn;

    @FXML
    private TableColumn<?, ?> movieRatingColumn;

    @FXML
    private TableColumn<?, ?> movieSeenDateColumn;

    @FXML
    private Label movieTableLabel;

    @FXML
    private TableView<?> movieTableView;

    @FXML
    private TableColumn<?, ?> movieTitleColumn;

    @FXML
    private TableColumn<?, ?> movieYearColumn;

    @FXML
    private Tab moviesTab;

    @FXML
    private VBox moviesTabVbox;

    @FXML
    private TableColumn<?, ?> seriesEndingYearColumn;

    @FXML
    private TableColumn<?, ?> seriesOriginalTitleColumn;

    @FXML
    private TableColumn<?, ?> seriesRatingColumn;

    @FXML
    private TableColumn<?, ?> seriesSeasonNumberColumn;

    @FXML
    private TableColumn<?, ?> seriesSeenDateColumn;

    @FXML
    private VBox seriesTabVbox;

    @FXML
    private Label seriesTableLabel;

    @FXML
    private TableView<?> seriesTableView;

    @FXML
    private TableColumn<?, ?> seriesTitleColumn;

    @FXML
    private TableColumn<?, ?> seriesYearColumn;

    @FXML
    private VBox tabBooxVbox;

}

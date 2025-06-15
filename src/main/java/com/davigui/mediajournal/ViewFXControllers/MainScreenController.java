package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Repository.DataOperations;
import com.davigui.mediajournal.Model.Repository.Library;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    //*********Atributos FXML ************
    @FXML
    private Tab seriesTab;

    @FXML
    private Tab booksTab;

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private TabPane mediasTabPane;

    @FXML
    private Tab moviesTab;

    @FXML
    private BooksTabContentController booksTabContentController;

    @FXML
    private MoviesTabContentController moviesTabContentController;

    @FXML
    private SeriesTabContentController seriesTabContentController;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Library journal = new Library();
        DataOperations.load(journal);

        BookService bookService = new BookService(journal);
        MovieService movieService = new MovieService(journal);
        SeriesService seriesService = new SeriesService(journal);

        booksTabContentController.setBookService(bookService);
        booksTabContentController.loadBookList();

        moviesTabContentController.setMovieService(movieService);
        moviesTabContentController.loadMovieList();

        seriesTabContentController.setSeriesService(seriesService);
        seriesTabContentController.loadSeriesList();

    }
}

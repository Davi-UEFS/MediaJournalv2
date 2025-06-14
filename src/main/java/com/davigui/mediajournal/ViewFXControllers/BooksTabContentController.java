package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BooksTabContentController implements Initializable {

    @FXML
    private TableColumn<Book, String> bookAuthorColumn;

    @FXML
    private ImageView bookEditButton;

    @FXML
    private ImageView bookFilterButton;

    @FXML
    private TextField bookFilterTetField;

    @FXML
    private ChoiceBox<String> bookFilterTypeChoiceBox;

    @FXML
    private ChoiceBox<Genres> bookGenreChoiceBox;

    @FXML
    private TableColumn<Book, String> bookIsbnColumn;

    @FXML
    private TableColumn<Book, String> bookPublisherColumn;

    @FXML
    private ImageView bookRateButton;

    @FXML
    private TableColumn<Book, Integer> bookRatingColumn;

    @FXML
    private ImageView bookRemoveButton;

    @FXML
    private TableColumn<Book, String> bookSeenDateColumn;

    @FXML
    private Label bookTableLabel;

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, String> bookTitleColumn;

    @FXML
    private TableColumn<Book, Integer> bookYearColumn;

    // *********Atributos nao FXMLs******************

    private BookService bookService;

    private ObservableList<Book> bookObservableList;


    // ***********Metodos*******************
    @Override
    public void initialize(URL url, ResourceBundle rb){

        //Precisa criar o Property pois meu nao irei mudar o Model
        bookTitleColumn.setCellValueFactory(cellData->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        /*Alem do Property, tem que usar asObject porque getYear e getRating retornam
        int e nao Integer (objeto)*/
        bookYearColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getYear())).asObject());

        bookRatingColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getRating())).asObject());

        //Daqui pra frente e a mesma ideia do titulo, entao nao irei explicar
        bookAuthorColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getAuthor()));

        bookIsbnColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getIsbn()));

        bookPublisherColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getPublisher()));

        bookSeenDateColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getSeenDate()));

    }

    public void loadBookList(){
        List<Book> books = bookService.getAllBooks();
        this.bookObservableList = FXCollections.observableArrayList(books);
        bookTableView.setItems(bookObservableList);
    }

    public void setBookService(BookService bookService){
        this.bookService = bookService;

    }

}

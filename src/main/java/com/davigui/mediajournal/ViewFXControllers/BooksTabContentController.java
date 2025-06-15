package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BooksTabContentController implements Initializable {

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private Label bookTableLabel;

    @FXML
    private TableColumn<Book, String> bookTitleColumn;

    @FXML
    private TableColumn<Book, String> bookAuthorColumn;

    @FXML
    private TableColumn<Book, String> bookPublisherColumn;

    @FXML
    private TableColumn<Book, Integer> bookYearColumn;

    @FXML
    private TableColumn<Book, String> bookIsbnColumn;

    @FXML
    private TableColumn<Book, Integer> bookRatingColumn;

    @FXML
    private TableColumn<Book, String> bookSeenDateColumn;

    @FXML
    private Button bookEditButton;

    @FXML
    private Button bookRemoveButton;

    @FXML
    private Button bookRateButton;

    @FXML
    private Button bookFilterButton;

    @FXML
    private TextField bookFilterTextField;

    @FXML
    private ChoiceBox<String> bookFilterTypeChoiceBox;

    @FXML
    private ChoiceBox<Genres> bookGenreChoiceBox;

    @FXML
    private ImageView bookImageView;

    @FXML
    private Label bookTitleYear;

    @FXML
    private Label bookGenre;

    @FXML
    private Label bookRating;

    @FXML
    private Label bookReview;

    // *********Atributos nao FXMLs******************

    private BookService bookService;

    private ObservableList<Book> bookObservableList;

    private ObservableValue<Book> selectedBook;


    // ***********Metodos*******************
    @Override
    public void initialize(URL url, ResourceBundle rb){

        //**************TABELA************************************88

        initTableSelectionListener();

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


        //***********BOTOES*************************

        deactivateFilterFields();
        bookEditButton.setDisable(true);

    }

    public void loadBookList(){
        List<Book> books = bookService.getAllBooks();
        this.bookObservableList = FXCollections.observableArrayList(books);
        bookTableView.setItems(bookObservableList);
    }

    public void setBookService(BookService bookService){
        this.bookService = bookService;

    }

    void initTableSelectionListener (){

        selectedBook = bookTableView.getSelectionModel().selectedItemProperty();

        selectedBook.addListener(new ChangeListener<Book>() {

            @Override
            public void changed(ObservableValue<? extends Book> obs, Book oldBook, Book newBook) {
                if(newBook == null)
                    disableEditButton();

                else {
                    enableEditButton();
                    handleBookInfo(selectedBook.getValue());
                }
            }
        });
    }

    @FXML
    public void onFilterButtonClicked(ActionEvent event){

        if(bookFilterTextField.isVisible())
            deactivateFilterFields();

        else
            activateFilterFields();

    }

    private void activateFilterFields(){
        bookFilterTypeChoiceBox.setVisible(true);
        bookFilterTypeChoiceBox.setManaged(true);
        bookGenreChoiceBox.setVisible(true);
        bookGenreChoiceBox.setManaged(true);
        bookFilterTextField.setVisible(true);
        bookFilterTextField.setManaged(true);
    }

    private void deactivateFilterFields(){
        bookFilterTypeChoiceBox.setVisible(false);
        bookFilterTypeChoiceBox.setManaged(false);
        bookGenreChoiceBox.setVisible(false);
        bookGenreChoiceBox.setManaged(false);
        bookFilterTextField.setVisible(false);
        bookFilterTextField.setManaged(false);
    }

    private void disableEditButton(){
        bookEditButton.setDisable(true);
    }

    /// ///TODO PAREI AQUI AQUI

    private void enableEditButton(){
        bookEditButton.setDisable(false);
    }

    public void handleBookInfo(Book book){
        bookTitleYear.setText(book.getTitle() + " (" + book.getYear() + ")");
        bookGenre.setText(book.getGenre().toString());
        bookRating.setText("★".repeat(book.getRating()));
        if(book.getReview() == null)
            bookReview.setText("RESENHA: Sem resenha atribuida");

        else
            bookReview.setText("RESENHA: " + book.getReview());

    }

}

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class BooksTabContentController implements Initializable {
    // *********Atributos FXML******************

    @FXML
    private TableView<Book> tableView;

    @FXML
    private Label tableLabel;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private TableColumn<Book, Integer> yearColumn;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, Integer> ratingColumn;

    @FXML
    private TableColumn<Book, String> seenDateColumn;

    @FXML
    private Button editButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button rateButton;

    @FXML
    private Button filterButton;

    @FXML
    private TextField filterTextField;

    @FXML
    private ChoiceBox<String> filterTypeChoiceBox;

    @FXML
    private Label filterTypeLabel;

    @FXML
    private ChoiceBox<Genres> genreChoiceBox;

    @FXML
    private Button clearSearchButton;

    @FXML
    private ImageView coverViewInfo;

    @FXML
    private Label titleYearInfo;

    @FXML
    private Label genreInfo;

    @FXML
    private Label ratingInfo;

    @FXML
    private Label reviewInfo;

    // *********Atributos nao FXMLs******************

    private BookService bookService;

    private ObservableList<Book> bookObservableList;

    private ObservableValue<Book> selectedBook;

    private ObservableValue<Genres> selectedGenre;

    private ObservableValue<String> selectedFilter;

    private ObservableValue<String> selectedTitle;

    // ***********Metodos*******************
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //**************TABELA************************************

        initTableListener();

        //Precisa criar o Property pois nao irei mudar o Model
        titleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        /*Alem do Property, tem que usar asObject porque getYear e getRating retornam
        int e nao Integer (objeto)*/
        yearColumn.setCellValueFactory(cellData ->
                (new SimpleIntegerProperty(cellData.getValue().getYear())).asObject());

        ratingColumn.setCellValueFactory(cellData ->
                (new SimpleIntegerProperty(cellData.getValue().getRating())).asObject());

        //Daqui pra frente e a mesma ideia do titulo, entao nao irei explicar
        authorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAuthor()));

        isbnColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getIsbn()));

        publisherColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPublisher()));

        seenDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSeenDate()));


        //***********TEXTFIELDS*********************
        filterTextField.setPromptText("Buscar");
        initTextFieldListener();

        //***********CHOICEBOXS*********************

        List<String> filterChoices = new ArrayList<>();
        filterChoices.add("Título");
        filterChoices.add("Ano");
        filterChoices.add("ISBN");
        filterChoices.add("Gênero");
        filterChoices.add("Autor");

        filterTypeChoiceBox.setItems(FXCollections.observableArrayList(filterChoices));
        initFilterChoiceBoxListener();

        List<Genres> genreChoices = Arrays.asList(Genres.values());
        genreChoiceBox.setItems(FXCollections.observableList(genreChoices));
        initGenreChoiceBoxListener();

        //***********BOTOES*************************

        deactivateFilterTypeChoiceBox();
        deactivateFilterTextField();
        deactivateGenreChoiceBox();
        deactivateClearSearchButton();
        editButton.setDisable(true);
    }

    public void loadBookList() {
        List<Book> books = bookService.getAllBooks();
        bookObservableList = FXCollections.observableArrayList(books);
        tableView.setItems(bookObservableList);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    private void initTableListener() {

        selectedBook = tableView.getSelectionModel().selectedItemProperty();

        selectedBook.addListener(new ChangeListener<Book>() {
            @Override
            public void changed(ObservableValue<? extends Book> obs, Book oldValue, Book newValue) {
                if (newValue == null)
                    disableEditButton();
                else {
                    enableEditButton();
                    handleBookInfo(selectedBook.getValue());
                }
            }
        });
    }

    private void initFilterChoiceBoxListener() {

        selectedFilter = filterTypeChoiceBox.getSelectionModel().selectedItemProperty();

        selectedFilter.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

                if (newValue != null) {
                    if (newValue.equals("Gênero")) {
                        activateGenreChoiceBox();
                        deactivateFilterTextField();
                    } else {
                        deactivateGenreChoiceBox();
                        activateFilterTextField();
                        filterTextField.clear();
                    }
                }
            }
        });

    }

    private void initGenreChoiceBoxListener() {

        selectedGenre = genreChoiceBox.getSelectionModel().selectedItemProperty();

        selectedGenre.addListener(new ChangeListener<Genres>() {
            @Override
            public void changed(ObservableValue<? extends Genres> obsValue, Genres oldGenre, Genres newGenre) {

                if (newGenre != null)
                    genreSearch(newGenre);

            }
        });
    }

    private void initTextFieldListener(){

        filterTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obsValue, String oldValue, String newValue) {

                if(newValue.isEmpty()) return;

                String onlyIntValue;

                if(filterTypeChoiceBox.getValue().equals("Ano")){

                    if(!newValue.matches("^\\d*")){
                        onlyIntValue = newValue.replaceAll("[^\\d]", "");
                        filterTextField.setText(onlyIntValue);

                    } else onlyIntValue = newValue;

                    if(!onlyIntValue.isEmpty())
                        yearSearch(Integer.parseInt(onlyIntValue));

                } else{
                    handleSearch(newValue);
                }
            }
        });
    }

    private void handleSearch(String filter){

        switch (filterTypeChoiceBox.getValue()){
            case "Título":
                titleSearch(filter);
                break;
            case "ISBN":
                isbnSearch(filter);
                break;
            case "Autor":
                authorSearch(filter);
        }

    }

    private void authorSearch(String author){
        List<Book> filteredBooks = bookService.searchBookByAuthor(author);
        bookObservableList.setAll(filteredBooks);
    }

    private void genreSearch(Genres genre) {
        List<Book> filteredBooks = bookService.searchByGenre(genre, bookService.getAllBooks());
        bookObservableList.setAll(filteredBooks);
    }

    private void titleSearch(String title) {
        List<Book> filteredBooks = bookService.searchByTitle(title, bookService.getAllBooks());
        bookObservableList.setAll(filteredBooks);
    }

    private void isbnSearch(String isbn) {
        List<Book> filteredBooks = bookService.searchBookByIsbn(isbn);
        bookObservableList.setAll(filteredBooks);
    }

    private void yearSearch(int year){
        List<Book> filteredBooks = bookService.searchByYear(year, bookService.getAllBooks());
        bookObservableList.setAll(filteredBooks);
    }

    @FXML
    public void onFilterButtonClicked() {

        if (filterTypeChoiceBox.isVisible()) {
            deactivateFilterTypeChoiceBox();
            deactivateClearSearchButton();
            deactivateFilterTextField();
            deactivateGenreChoiceBox();
        } else {
            activateFilterTypeChoiceBox();
            activateClearSearchButton();
        }

    }

    @FXML
    private void clearSearch() {
        bookObservableList.setAll(bookService.getAllBooks());
        filterTypeChoiceBox.getSelectionModel().clearSelection();
        deactivateFilterTextField();
        deactivateGenreChoiceBox();
    }

    private void activateGenreChoiceBox() {
        genreChoiceBox.setVisible(true);
        genreChoiceBox.setManaged(true);
    }

    private void activateClearSearchButton() {
        clearSearchButton.setVisible(true);
        clearSearchButton.setManaged(true);
    }

    private void deactivateClearSearchButton() {
        clearSearchButton.setVisible(false);
        clearSearchButton.setManaged(false);
    }

    private void deactivateGenreChoiceBox() {
        genreChoiceBox.setVisible(false);
        genreChoiceBox.setManaged(false);
        genreChoiceBox.getSelectionModel().clearSelection();
    }

    private void activateFilterTypeChoiceBox() {
        filterTypeChoiceBox.setVisible(true);
        filterTypeChoiceBox.setManaged(true);
        filterTypeLabel.setVisible(true);
        filterTypeLabel.setManaged(true);
    }

    private void deactivateFilterTypeChoiceBox() {
        filterTypeChoiceBox.setVisible(false);
        filterTypeChoiceBox.setManaged(false);
        filterTypeChoiceBox.getSelectionModel().clearSelection();
        filterTypeLabel.setVisible(false);
        filterTypeLabel.setManaged(false);
    }

    private void activateFilterTextField() {
        filterTextField.setVisible(true);
        filterTextField.setManaged(true);
    }

    private void deactivateFilterTextField() {
        filterTextField.setVisible(false);
        filterTextField.setManaged(false);
        filterTextField.clear();
    }

    private void disableEditButton() {
        editButton.setDisable(true);
    }

    private void enableEditButton() {
        editButton.setDisable(false);
    }

    public void handleBookInfo(Book book) {
        titleYearInfo.setText(book.getTitle() + " (" + book.getYear() + ")");
        genreInfo.setText(book.getGenre().toString());
        ratingInfo.setText("★".repeat(book.getRating()));
        if (book.getReview() == null)
            reviewInfo.setText("RESENHA: Sem resenha atribuida");
        else
            reviewInfo.setText("RESENHA: " + book.getReview());
    }

}

package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

    // ***********Metodos*******************
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //**************TABELA************************************

        initTableListener();
        configureTable();
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

        toggleFilterTypeComponents(false);
        toggleFilterTextField(false);
        toggleGenreChoiceBox(false);
        updateActionButtons();
    }

    public void loadBookList() {
        List<Book> books = bookService.getAll();
        bookObservableList = FXCollections.observableArrayList(books);
        tableView.setItems(bookObservableList);
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    private void initTableListener() {

        selectedBook = tableView.getSelectionModel().selectedItemProperty();

        selectedBook.addListener((obs, oldValue, newValue) -> {
            if (newValue != null)
                handleBookInfo(selectedBook.getValue());
            updateActionButtons();
        });
    }

    private void configureTable(){
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
    }

    private void updateActionButtons(){
        boolean isSelected = (selectedBook.getValue()!=null);
        editButton.setDisable(!isSelected);
        rateButton.setDisable(!isSelected);
        removeButton.setDisable(!isSelected);
    }

    private void initFilterChoiceBoxListener() {

        selectedFilter = filterTypeChoiceBox.getSelectionModel().selectedItemProperty();

        selectedFilter.addListener((observableValue, oldValue, newValue) -> {

            if (newValue != null) {
                if (newValue.equals("Gênero")) {
                    toggleGenreChoiceBox(true);
                    toggleFilterTextField(false);
                } else {
                    toggleGenreChoiceBox(false);
                    toggleFilterTextField(true);
                    filterTextField.clear();
                }
            }
        });
    }

    private void initGenreChoiceBoxListener() {

        selectedGenre = genreChoiceBox.getSelectionModel().selectedItemProperty();

        selectedGenre.addListener((obsValue, oldGenre, newGenre) -> {

            if (newGenre != null)
                genreSearch(newGenre);

        });
    }

    private void initTextFieldListener(){

        filterTextField.textProperty().addListener((obsValue, oldValue, newValue) -> {

            if(newValue.isEmpty() && !("Gênero").equals(filterTypeChoiceBox.getValue())){
                loadBookList(); //Recarregar todos os livros se o filtro ficar vazio
                return;         // e o filtro nao for genero (boa pratica)
            }

            if(filterTypeChoiceBox.getValue().equals("Ano")){
                String onlyIntValue = newValue.replaceAll("[^\\d]", "");

                if(!newValue.equals(onlyIntValue)){
                    filterTextField.setText(onlyIntValue);
                }

                if(!onlyIntValue.isEmpty())
                    yearSearch(Integer.parseInt(onlyIntValue));

            } else{
                handleSearch(newValue);
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
        bookObservableList.setAll(bookService.searchBookByAuthor(author));
    }

    private void genreSearch(Genres genre) {
        bookObservableList.setAll(bookService.searchByGenre(genre));
    }

    private void titleSearch(String title) {
        bookObservableList.setAll(bookService.searchByTitle(title));
    }

    private void isbnSearch(String isbn) {
        bookObservableList.setAll(bookService.searchBookByIsbn(isbn));
    }

    private void yearSearch(int year){
        bookObservableList.setAll(bookService.searchByYear(year));
    }

    private void setVisibleAndManaged(Control control, boolean active){
        control.setVisible(active);
        control.setManaged(active);
    }

    @FXML
    public void onFilterButtonClicked() {

        if (filterTypeChoiceBox.isVisible()) {
            toggleFilterTypeComponents(false);
            toggleFilterTextField(false);
            toggleGenreChoiceBox(false);
        } else {
            toggleFilterTypeComponents(true);
        }
    }

    @FXML
    private void clearSearch() {
        bookObservableList.setAll(bookService.getAll());
        filterTypeChoiceBox.getSelectionModel().clearSelection();
        toggleFilterTextField(false);
        toggleGenreChoiceBox(false);
    }

    private void toggleGenreChoiceBox(boolean active){
        setVisibleAndManaged(genreChoiceBox, active);
        if(!active) genreChoiceBox.getSelectionModel().clearSelection();
    }

    private void toggleFilterTypeComponents(boolean active){
        setVisibleAndManaged(filterTypeChoiceBox, active);
        setVisibleAndManaged(filterTypeLabel, active);
        setVisibleAndManaged(clearSearchButton, active);
        if(!active) filterTypeChoiceBox.getSelectionModel().clearSelection();
    }

    private void toggleFilterTextField(boolean active){
        setVisibleAndManaged(filterTextField, active);
        if(!active) filterTextField.clear();
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

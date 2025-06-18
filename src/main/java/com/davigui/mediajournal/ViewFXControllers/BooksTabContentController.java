package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Model.Medias.Book;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

public class BooksTabContentController extends MediaContentController<Book> implements Initializable {

    // *********Atributos FXML*****************
    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> publisherColumn;

    @FXML
    private TableColumn<Book, String> isbnColumn;

    @FXML
    private TableColumn<Book, String> seenDateColumn;

    //*********Atributos NAO FXML***********
    //TODO: EXPLICITAR POSSIVEL ERRO DE CAST NO JAVADOC
    private BookService bookService;

    // ***********Metodos*******************

    @Override
    protected void setService(CommonService<Book> bookService){
        this.service = bookService;
        this.bookService = (BookService) bookService;
    }

    @Override
    protected void configureTable(){
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

    @Override
    protected void configureFilterChoices() {
        List<String> filterChoices = new ArrayList<>();
        filterChoices.add("Título");
        filterChoices.add("Ano");
        filterChoices.add("ISBN");
        filterChoices.add("Gênero");
        filterChoices.add("Autor");

        filterTypeChoiceBox.setItems(FXCollections.observableArrayList(filterChoices));
    }

    private void authorSearch(String author){
        mediaObservableList.setAll(bookService.searchBookByAuthor(author));
    }

    private void isbnSearch(String isbn) {
        mediaObservableList.setAll(bookService.searchBookByIsbn(isbn));
    }

    @Override
    protected void handleSpecificSearch(String filter) {
        switch (selectedFilter.getValue()){
            case "Autor":
                authorSearch(filter);
                break;

            case "ISBN":
                isbnSearch(filter);
        }
    }

    @Override
    public void handleMediaInfo(Book book) {
        titleYearInfo.setText(book.getTitle() + " (" + book.getYear() + ")");
        genreInfo.setText(book.getGenre().toString());
        ratingInfo.setText("★".repeat(book.getRating()));
        if (book.getReview() == null)
            reviewInfo.setText("RESENHA: Sem resenha atribuida");
        else
            reviewInfo.setText("RESENHA: " + book.getReview());
    }

}

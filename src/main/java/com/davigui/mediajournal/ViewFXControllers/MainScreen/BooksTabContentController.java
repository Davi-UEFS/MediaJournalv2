package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.MainFX;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.ViewFXControllers.RateScreens.RateScreenController;
import com.davigui.mediajournal.ViewFXControllers.RateScreens.SeenBookScreenController;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterBookScreenController;
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
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                new SimpleStringProperty("★".repeat(cellData.getValue().getRating())));

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

    @Override
    public void onAddButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RegisterBookScreen.fxml"));
        Parent root = loader.load();

        RegisterBookScreenController controllerAdd = loader.getController();
        controllerAdd.setService(bookService);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registro de Livro");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/book_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> loadMediaList());
        stage.show();
    }

    @Override
    public void onRemoveButtonClicked() {
        // Implementação do método para remover um livro
        Book selectedBook = selectedItem.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Remoção");
        alert.setHeaderText("Deseja realmente remover o livro " + selectedBook.getTitle() + " ?");
        alert.setContentText("O livro será permanentemente removido.");
        ButtonType buttonContinuar = new ButtonType("Continuar");
        ButtonType buttonCancelar = ButtonType.CANCEL;

        alert.getButtonTypes().setAll(buttonContinuar, buttonCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonContinuar) {
            bookService.deleteBook(selectedBook);
            loadMediaList(); // Recarrega a lista de mídias após a remoção
            System.out.println("Livro removido: " + selectedBook.getTitle());
        }
        // Se o usuário cancelar, não faz nada
    }

    @Override
    public void onSeenButtonClicked() throws IOException {
        Book selectedBook = selectedItem.getValue();

        if (selectedBook.getSeenDate() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText("Livro já visto");
            alert.setContentText("Você já marcou este livro como visto.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/SeenScreen.fxml"));
        loader.setController(new SeenBookScreenController());
        Parent root = loader.load();

        SeenBookScreenController controllerSeen = loader.getController();
        controllerSeen.setService(bookService);
        controllerSeen.setMedia(selectedBook);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Marcar Livro como Visto");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/seen_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> loadMediaList());
        stage.show();
    }

}

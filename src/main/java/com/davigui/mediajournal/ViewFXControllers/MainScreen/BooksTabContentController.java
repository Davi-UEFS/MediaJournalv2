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

/**
 * Controlador da aba de livros na tela principal.
 * </p>
 * Estende {@code MediaContentController<Book>}. Esta classe é responsável por
 * configurar a tabela, os filtros e as buscas específicos para livros.
 */
public class BooksTabContentController extends MediaContentController<Book> {

    // *********Atributos FXML*****************
    /**
     * Coluna de autor do livro.
     */
    @FXML
    private TableColumn<Book, String> authorColumn;

    /**
     * Coluna de editora do livro.
     */
    @FXML
    private TableColumn<Book, String> publisherColumn;

    /**
     * Coluna de ISBN do livro.
     */
    @FXML
    private TableColumn<Book, String> isbnColumn;

    /**
     * Coluna da data em que o livro foi lido.
     */
    @FXML
    private TableColumn<Book, String> seenDateColumn;

    //*********Atributos NAO FXML***********
    /**
     * Serviço específico para operações com livros.
     * </p>
     * Este atributo é obtido via downcast de {@code CommonService<Book>} e,
     * portanto, pode gerar {@code ClassCastException} se um serviço incorreto
     * for fornecido.
     */
    private BookService bookService;

    // ***********Metodos*******************

    /**
     * Define o serviço específico de livros a ser utilizado pelo controlador.
     * O service herdado da classe abstrata utiliza upcasting com o controlador
     * de modelo de livros.
     * <p>
     * Este método realiza um downcast de {@code CommonService<Book>} para {@code BookService}
     * para permitir acesso a métodos específicos do serviço de livros e, portanto,
     * pode lançar {@code ClassCastException}.
     *
     * @param bookService O serviço a ser atribuído
     * @throws ClassCastException Se o serviço fornecido não for uma instância de {@code BookService}
     */
    @Override
    protected void setService(CommonService<Book> bookService){
        this.service = bookService;
        this.bookService = (BookService) bookService;
    }

    /**
     * Configura as colunas da tabela de livros.
     * <p>
     * Define como cada coluna da tabela extrai os dados dos objetos {@code Book},
     * incluindo propriedades como título, ano, avaliação, autor, ISBN, editora
     * e data de leitura.
     * Como os métodos do modelo retornam tipos comuns (e não propriedades observáveis),
     * é necessário encapsular os valores em {@code Property} ao configurar as colunas,
     */
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

    /**
     * Configura os critérios disponíveis para filtragem de livros.
     * </p>
     * As opções devem ser inicialmente inseridas em uma {@code ArrayList} que
     * será convertida para uma lista observável com {@code FXCollections.observableArrayList()},
     * permitindo sua atribuição ao componente gráfico.
     */
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

    /**
     * Realiza a busca por autor.
     * </p>
     * A busca é executada pelo controlador de modelo de livros com base no autor
     * e a lista retornada é atribuida à lista observável de mídias.
     * @param author O autor do livro
     */
    private void authorSearch(String author){
        mediaObservableList.setAll(bookService.searchBookByAuthor(author));
    }

    /**
     * Realiza a busca por ISBN.
     * </p>
     * A busca é executada pelo controlador de modelo de livros com base no ISBN
     * e a lista retornada é atribuida à lista observável de mídias.
     * @param isbn O código ISBN do livro
     */
    private void isbnSearch(String isbn) {
        mediaObservableList.setAll(bookService.searchBookByIsbn(isbn));
    }

    /**
     * Executa buscas específicas para os critérios "Autor" e "ISBN".
     * </p>
     * Este método é chamado quando o critério de filtro selecionado não
     * pertence aos tipos genéricos na superclasse (título, ano e gênero).
     *
     * @param filter O filtro inserido no campo de texto
     */
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

    /**
     * Exibe as informações detalhadas de um livro selecionado na interface.
     * </p>
     * Preenche os campos visuais com os dados do livro, como título, ano,
     * gênero, avaliação e resenha. Caso não haja resenha cadastrada,
     * exibe uma mensagem padrão.
     *
     * @param book o livro atualmente selecionado na tabela
     */
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
        }
        // Se o usuário cancelar, não faz nada
    }

    @Override public void onRateButtonClicked() throws IOException{
        Book selectedBook = selectedItem.getValue();

        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RateScreen.fxml"));
        loader.setController(new RateScreenController<Book>());
        Parent root = loader.load();

        RateScreenController<Book> controllerRate = loader.getController();
        controllerRate.setService(bookService);
        controllerRate.setMedia(selectedBook);
        controllerRate.initFields();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Avaliação de Livro");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/book_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> loadMediaList());
        stage.show();
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

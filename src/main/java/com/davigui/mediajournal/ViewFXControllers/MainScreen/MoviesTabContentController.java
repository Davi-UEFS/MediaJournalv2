package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.MainFX;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.ViewFXControllers.RateScreens.RateScreenController;
import com.davigui.mediajournal.ViewFXControllers.RateScreens.SeenMovieScreenController;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterMovieScreenController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador da aba de filmes na tela principal.
 * <p>
 * Estende {@code MediaContentController<Movie>}. Esta classe é responsável por
 * configurar a tabela, os filtros e as buscas específicos para filmes.
 */
public class MoviesTabContentController extends MediaContentController<Movie> {

    // *********Atributos FXML******************

    /**
     * Coluna do título original do filme.
     */
    @FXML
    private TableColumn<Movie, String> originalTitleColumn;

    /**
     * Coluna do diretor do filme.
     */
    @FXML
    private TableColumn<Movie, String> directionColumn;

    /**
     * Coluna da duração do filme.
     */
    @FXML
    private TableColumn<Movie, Integer> durationColumn;

    /**
     * Coluna da data em que o filme foi assistido.
     */
    @FXML
    private TableColumn<Movie, String> seenDateColumn;

    /**
     * Rótulo que exibe as plataformas onde o filme pode ser assistido no painel lateral.
     */
    @FXML
    private Label whereToWatchInfo;

    /**
     * Rótulo que exibe o elenco do filme no painel lateral.
     */
    @FXML
    private Label castInfo;

    /**
     * Rótulo que exibe o roteirista do filme no painel lateral.
     */
    @FXML
    private Label scriptInfo;

    /**
     * Rótulo que exibe a duração do filme no painel lateral.
     */
    @FXML
    private Label durationInfo;

    //*********Atributos NAO FXML***********

    /**
     * Serviço específico para operações com filmes.
     * <p>
     * Este atributo é obtido via downcast de {@code CommonService<Movie>} e
     * pode gerar {@code ClassCastException} se um serviço incorreto
     * for fornecido.
     */
    private MovieService movieService = (MovieService) service;

    //***********Metodos*********************

    /**
     * Define o serviço específico de filmes a ser utilizado pelo controlador.
     * O serviço herdado da classe abstrata utiliza upcasting com o controlador
     * de modelo de filmes.
     * <p>
     * Este metodo realiza um downcast de {@code CommonService<Movie>} para {@code MovieService}
     * para permitir acesso a metodos específicos do serviço de filmes e, portanto,
     * pode lançar {@code ClassCastException}.
     *
     * @param service O serviço a ser atribuído
     * @throws ClassCastException Se o serviço fornecido não for uma instância de {@code MovieService}
     */
    @Override
    protected void setService(CommonService<Movie> service) {
        this.service = service;
        this.movieService = (MovieService) service;
    }

    /**
     * Configura as colunas da tabela de filmes.
     * <p>
     * Define como cada coluna extrai os dados dos objetos {@code Movie},
     * incluindo propriedades como título, ano, avaliação, direção, duração,
     * título original e data de visualização.
     * Como os metodos do modelo retornam tipos comuns (e não propriedades observáveis),
     * é necessário encapsular os valores em {@code Property}.
     */
    @Override
    protected void configureTable() {
        titleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        yearColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());

        ratingColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty("★".repeat(cellData.getValue().getRating())));

        directionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDirection()));

        durationColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getDuration()).asObject());

        originalTitleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getOriginalTitle()));

        seenDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSeenDate()));
    }

    /**
     * Realiza a busca por ator.
     * <p>
     * A busca é realizada pelo serviço de filmes com base no nome do ator,
     * e a lista retornada é atribuída à lista observável de mídias.
     *
     * @param filter O nome do ator
     */
    private void actorSearch(String filter) {
        mediaObservableList.setAll(movieService.searchByActor(filter));
    }

    /**
     * Realiza a busca por diretor.
     * <p>
     * A busca é realizada pelo serviço de filmes com base no nome do diretor,
     * e a lista retornada é atribuída à lista observável de mídias.
     *
     * @param filter O nome do diretor
     */
    private void directorSearch(String filter) {
        mediaObservableList.setAll(movieService.searchByDirector(filter));
    }

    /**
     * Configura os critérios disponíveis para filtragem de filmes.
     * <p>
     * As opções devem ser adicionadas em uma {@code ArrayList} que é convertidas
     * em uma lista observável e atribuídas ao {@code ChoiceBox} de filtros.
     */
    @Override
    protected void configureFilterChoices() {
        List<String> filterChoices = new ArrayList<>();
        filterChoices.add("Título");
        filterChoices.add("Ano");
        filterChoices.add("Gênero");
        filterChoices.add("Diretor");
        filterChoices.add("Ator");

        filterTypeChoiceBox.setItems(FXCollections.observableArrayList(filterChoices));
    }

    /**
     * Executa buscas específicas para os critérios "Diretor" e "Ator".
     * <p>
     * Este metodo é chamado quando o critério de filtro selecionado não
     * pertence aos tipos genéricos tratados na superclasse (título, ano, gênero).
     *
     * @param filter O filtro digitado no campo de busca
     */
    @Override
    protected void handleSpecificSearch(String filter) {
        switch (selectedFilter.getValue()) {
            case "Ator":
                actorSearch(filter);
                break;
            case "Diretor":
                directorSearch(filter);
                break;
        }
    }

    /**
     * Exibe as informações detalhadas de um filme selecionado na interface.
     * <p>
     * Preenche os campos visuais com dados do filme, como título, ano, gênero,
     * avaliação, roteiro, elenco, plataformas de exibição e duração. Caso não
     * haja resenha cadastrada, uma mensagem padrão é exibida.
     *
     * @param movie O filme atualmente selecionado na tabela
     */
    @Override
    protected void handleMediaInfo(Movie movie) {
        titleYearInfo.setText(movie.getTitle() + " (" + movie.getYear() + ")");
        genreInfo.setText(movie.getGenre().toString());
        ratingInfo.setText("★".repeat(movie.getRating()));
        if (movie.getReview() == null)
            reviewInfo.setText("RESENHA: Sem resenha atribuida");
        else
            reviewInfo.setText("RESENHA: " + movie.getReview());

        durationInfo.setText("Duração: " + movie.getDuration() + "min");
        castInfo.setText("Elenco: " + movie.getCast().toString());
        scriptInfo.setText("Roteiro: " + movie.getScript());
        whereToWatchInfo.setText("Plataformas: " + movie.getWhereToWatch().toString());
    }

    /**
     * Abre tela de registro de filme ao clicar no botão "Add. Filme".
     * <p>
     * Cria uma nova janela para registrar um filme, desativando a
     * janela principal até que a janela de registro seja fechada.
     * Ao fechar a janela de registro, a tabela de mídias é recarregada.
     * @throws IOException Se ocorrer um erro ao carregar o arquivo FXML.
     */
    @FXML
    protected void onAddButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RegisterMovieScreen.fxml"));
        Parent root = loader.load();

        RegisterMovieScreenController controller = loader.getController();
        controller.setService(movieService);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registro de Filme");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/movie_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> resetMediaList());
        stage.show();
    }

    /**
     * Cuida da remoção de um filme selecionado ao clicar no botão "Excluir".
     * <p>
     * Exibe um diálogo de confirmação antes de remover o filme, pegando o
     * filme selecionado da tabela.
     * Se o usuário confirmar, o filme é removido e uma mensagem de sucesso
     * é exibida. A tabela de mídias é recarregada após a remoção.
     */
    @Override
    protected void onRemoveButtonClicked() {
        // Implementação do metodo para remover um livro
        Movie selectedMovie = selectedItem.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Remoção");
        alert.setHeaderText("Deseja realmente remover o filme " + selectedMovie.getTitle() + " ?");
        alert.setContentText("O filme será permanentemente removido.");
        ButtonType buttonContinuar = new ButtonType("Continuar");
        ButtonType buttonCancelar = ButtonType.CANCEL;

        alert.getButtonTypes().setAll(buttonContinuar, buttonCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonContinuar) {
            movieService.deleteMovie(selectedMovie);
            resetMediaList(); // Recarrega a lista de mídias após a remoção
        }
        // Se o usuário cancelar, não faz nada
    }

    /**
     * Abre a tela de avaliação de filme ao clicar no botão "Avaliar".
     * <p>
     * Cria uma nova janela para avaliar o filme selecionado, desativando
     * a janela principal até que a janela de avaliação seja fechada.
     * Ao fechar a janela de avaliação, a tabela de mídias é recarregada.
     *
     * @throws IOException Se ocorrer um erro ao carregar o arquivo FXML.
     */
    @Override protected void onRateButtonClicked() throws IOException {
        Movie selectedMovie = selectedItem.getValue();

        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RateScreen.fxml"));
        loader.setController(new RateScreenController<Movie>());
        Parent root = loader.load();

        RateScreenController<Movie> controllerRate = loader.getController();
        controllerRate.setService(movieService);
        controllerRate.setMedia(selectedMovie);
        controllerRate.initFields();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Avaliação de Filme");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/movie_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> resetMediaList());
        stage.show();
    }

    /**
     * Abre a tela de marcar um filme como visto ao clicar no botão "Marcar Visto".
     * <p>
     * Cria uma nova janela para marcar o filme selecionado como visto,
     * desativando a janela principal até que a janela de marcação seja fechada.
     * Ao fechar a janela de marcação, a tabela de mídias é recarregada.
     * Se o filme já tiver marcado como visto, exibe um alerta.
     *
     * @throws IOException Se ocorrer um erro ao carregar o arquivo FXML.
     */
    @Override
    protected void onSeenButtonClicked() throws IOException {
        Movie selectedMovie = selectedItem.getValue();

        if (selectedMovie.isSeen()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText("Filme já visto");
            alert.setContentText("Você já marcou este filme como visto.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/SeenScreen.fxml"));
        loader.setController(new SeenMovieScreenController());
        Parent root = loader.load();

        SeenMovieScreenController controllerSeen = loader.getController();
        controllerSeen.setService(movieService);
        controllerSeen.setMedia(selectedMovie);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Marcar Filme como Visto");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/seen_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> resetMediaList());
        stage.show();
    }
}
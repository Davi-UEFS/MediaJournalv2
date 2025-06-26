package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.MainFX;
import com.davigui.mediajournal.Model.Exceptions.SeasonNotFoundException;
import com.davigui.mediajournal.Model.Medias.Season;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.ViewFXControllers.RateScreens.RateSeasonScreenController;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterSeasonScreenController;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterSeriesScreenController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador da aba de séries na tela principal.
 * </p>
 * Estende {@code MediaContentController<Series>}. Esta classe é responsável por
 * configurar a tabela, os filtros e as buscas específicos para séries.
 */
public class SeriesTabContentController extends MediaContentController<Series> {

    //**********Atributos FXML***************

    /**
     * Rótulo do título da tabela.
     */
    @FXML
    private Label tableLabel;

    /**
     * Coluna do título original da série.
     */
    @FXML
    private TableColumn<Series, String> originalTitleColumn;

    /**
     * Coluna do ano de encerramento da série.
     */
    @FXML
    private TableColumn<Series, String> endingYearColumn;

    /**
     * Coluna com a quantidade de temporadas da série.
     */
    @FXML
    private TableColumn<Series, Integer> seasonNumberColumn;

    /**
     * Rótulo que exibe o elenco da série no painel lateral.
     */
    @FXML
    private Label castInfo;

    /**
     * Rótulo que exibe as plataformas onde a série pode ser assistida no painel lateral.
     */
    @FXML
    private Label whereToWatchInfo;

    /**
     * Rótulo que exibe as informações das temporadas da série no painel lateral.
     */
    @FXML
    private Label seasonsInfo;

    /**
     * Botão para adicionar uma nova temporada.
     */
    @FXML
    private Button addSeasonButton;

    /**
     * Botão para visualizar temporadas.
     */
    @FXML
    private Button seeSeasonButton;

    //************ Atributos NÃO FXML **************

    /**
     * Serviço específico para operações com séries.
     * </p>
     * Este atributo é obtido via downcast de {@code CommonService<Series>} e
     * pode gerar {@code ClassCastException} caso o serviço fornecido
     * não seja do tipo esperado.
     */
    private SeriesService seriesService;

    //************ Métodos *****************

    /**
     * Define o serviço específico de séries a ser utilizado pelo controlador.
     * O serviço herdado da classe abstrata utiliza upcasting com o controlador
     * de modelo de séries.
     * <p>
     * Este método realiza um downcast de {@code CommonService<Series>} para {@code SeriesService}
     * para permitir acesso a métodos específicos do serviço de séries e, portanto,
     * pode lançar {@code ClassCastException}.
     *
     * @param service O serviço a ser atribuído
     * @throws ClassCastException Se o serviço fornecido não for uma instância de {@code SeriesService}
     */
    @Override
    protected void setService(CommonService<Series> service) {
        this.service = service;
        this.seriesService = (SeriesService) service;
    }

    /**
     * Configura as colunas da tabela de séries.
     * <p>
     * Define como cada coluna extrai os dados dos objetos {@code Series},
     * incluindo propriedades como título, ano de início, ano de encerramento,
     * avaliação, título original e quantidade de temporadas.
     * </p>
     * Caso a série ainda esteja em andamento (valor 9999), o ano de encerramento
     * é exibido como "Em andamento".
     */
    @Override
    protected void configureTable() {
        titleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        yearColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getYear()).asObject());

        endingYearColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getYearOfEnding() == 9999 ?
                                "Em andamento" :
                                Integer.toString(cellData.getValue().getYearOfEnding())
                ));

        ratingColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty("★".repeat(cellData.getValue().getRating())));

        originalTitleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getOriginalTitle()));

        seasonNumberColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getNumberOfSeasons()).asObject());
    }

    /**
     * Configura os critérios disponíveis para filtragem de séries.
     * <p>
     * As opções devem ser inseridas em uma {@code ArrayList}, convertidas em uma
     * lista observável e atribuídas ao {@code ChoiceBox} de filtros.
     */
    @Override
    protected void configureFilterChoices() {
        List<String> filterChoices = new ArrayList<>();
        filterChoices.add("Título");
        filterChoices.add("Ano");
        filterChoices.add("Gênero");
        filterChoices.add("Ator");

        filterTypeChoiceBox.setItems(FXCollections.observableArrayList(filterChoices));
    }

    /**
     * Realiza a busca por ator.
     * </p>
     * A busca é realizada pelo serviço de séries com base no nome do ator,
     * e a lista retornada é atribuída à lista observável de mídias.
     *
     * @param filter O nome do ator
     */
    private void actorSearch(String filter) {
        mediaObservableList.setAll(seriesService.searchByActor(filter));
    }

    /**
     * Executa buscas específicas para o critério "Ator".
     * <p>
     * Este método é chamado quando o filtro selecionado não pertence aos tipos
     * genéricos tratados na superclasse (título, ano, gênero). O bloco switch,
     * embora possua apenas um case, foi mantido para possíveis adições.
     *
     * @param filter O filtro digitado no campo de busca
     */
    @Override
    protected void handleSpecificSearch(String filter) {
        switch (selectedFilter.getValue()) {
            case "Ator":
                actorSearch(filter);
                break;
            // Mantido como switch para facilitar extensões futuras
        }
    }

    /**
     * Exibe as informações detalhadas de uma série selecionada na interface.
     * <p>
     * Preenche os campos visuais com dados da série, como título, anos de início e fim,
     * gênero, avaliação, elenco, plataformas de exibição e temporadas.
     * Caso não haja resenha cadastrada, uma mensagem padrão é exibida.
     *
     * @param series A série atualmente selecionada na tabela
     */
    @Override
    protected void handleMediaInfo(Series series) {
        String endingYear = (series.getYearOfEnding() == 9999)
                ? "Em andamento"
                : Integer.toString(series.getYearOfEnding());

        titleYearInfo.setText(series.getTitle() + " (" + series.getYear() + " - " + endingYear + ")");
        genreInfo.setText(series.getGenre().toString());
        ratingInfo.setText("★".repeat(series.getRating()));

        if (series.getReview() == null)
            reviewInfo.setText("RESENHA: Sem resenha atribuida");
        else
            reviewInfo.setText("RESENHA: " + series.getReview());

        castInfo.setText("Elenco: " + series.getCast().toString());
        whereToWatchInfo.setText("Plataformas: " + series.getWhereToWatch().toString());

        StringBuffer seasonsString = new StringBuffer();
        series.getSeasons().forEach(season ->
                seasonsString.append("\n\t").append(season.toString()));
        seasonsInfo.setText(seasonsString.toString());
    }

    @Override
    public void onAddButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RegisterSeriesScreen.fxml"));
        Parent root = loader.load();

        RegisterSeriesScreenController controller = loader.getController();
        controller.setService(seriesService);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registro de Série");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/series_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> loadMediaList());
        stage.show();
    }

    @Override
    public void updateActionButtons() {
        super.updateActionButtons();
        boolean isSelected = (selectedItem.getValue()!=null);
        addSeasonButton.setDisable(!isSelected);
        seeSeasonButton.setDisable(!isSelected);
    }

    @Override
    public void onRemoveButtonClicked() {
        // Implementação do método para remover um livro
        Series selectedSeries = selectedItem.getValue();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Remoção");
        alert.setHeaderText("Deseja realmente remover a série " + selectedSeries.getTitle() + " ?");
        alert.setContentText("A série será permanentemente removida.");
        ButtonType buttonContinuar = new ButtonType("Continuar");
        ButtonType buttonCancelar = ButtonType.CANCEL;

        alert.getButtonTypes().setAll(buttonContinuar, buttonCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonContinuar) {
            seriesService.deleteSeries(selectedSeries);
            loadMediaList(); // Recarrega a lista de mídias após a remoção
        }
        // Se o usuário cancelar, não faz nada
    }

    @Override public void onRateButtonClicked() throws IOException {
        Series selectedSeries = selectedItem.getValue();
        Season selectedSeason = askForSeason(selectedSeries, "avaliar");

        if (selectedSeason == null) {
            // Usuário cancelou a seleção da temporada
            return;
        }

        if (!selectedSeason.isSeen()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText("Temporada não vista");
            alert.setContentText("Você ainda não marcou esta temporada como vista.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RateScreen.fxml"));
        loader.setController(new RateSeasonScreenController());
        Parent root = loader.load();

        RateSeasonScreenController controllerRate = loader.getController();
        controllerRate.setService(seriesService);
        controllerRate.setSeries(selectedSeries);
        controllerRate.setSeason(selectedSeason);
        controllerRate.initFields();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Avaliação de Temporada");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/series_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> loadMediaList());
        stage.show();

    }

    @Override
    public void onSeenButtonClicked() {
        Series selectedSeries = selectedItem.getValue();
        Season season = askForSeason(selectedSeries, "marcar como vista");

        if (season != null) {
            if (season.isSeen()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informação");
                alert.setHeaderText("Temporada já vista");
                alert.setContentText("Você já marcou esta temporada como vista.");
                alert.showAndWait();
            } else {
                askForSeen(selectedSeries, season);
            }
        }

    }

    @FXML
    public void onSeeSeasonButtonClicked() {
        Series selectedSeries = selectedItem.getValue();
        Season season = askForSeason(selectedSeries, "ver");

        if (season != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação da Temporada");
            alert.setHeaderText("Informações da Temporada " + season.getSeasonNumber() + " de " + selectedSeries.getTitle());
            alert.setContentText(season +
                    "\nAvaliação: " + (season.getRating() > 0 ? "★".repeat(season.getRating()) : "Nenhuma avaliação") +
                    "\nResenha: " + (season.getReview() != null ? season.getReview() : "Nenhuma resenha"));
            alert.showAndWait();
        }
    }

    @FXML
    public void onAddSeasonButtonClicked() throws IOException {
        Series selectedSeries = selectedItem.getValue();

        FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("fxml/RegisterSeasonScreen.fxml"));
        Parent root = loader.load();

        RegisterSeasonScreenController controllerAddSeason = loader.getController();
        controllerAddSeason.setService(seriesService);
        controllerAddSeason.setSeries(selectedSeries);
        controllerAddSeason.initFields();

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registro de Temporada");
        stage.getIcons().add(new Image(MainFX.class.getResourceAsStream("images/series_icon_G.png")));
        stage.setScene(scene);
        stage.setOnHidden(e -> loadMediaList());
        stage.show();
    }

    private static Season askForSeason(Series series, String s) {
        // Se a série tiver apenas uma temporada, retorna essa temporada
        if (series.getNumberOfSeasons() == 1) {
           return series.findSeason(1);
        }
        // Se a série tiver mais de uma temporada, pede ao usuário para selecionar uma
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Selecionar Temporada");
        dialog.setHeaderText("Selecione a temporada para " + series.getTitle() + " que deseja " + s);
        dialog.setContentText("Digite o número da temporada (1 a " + series.getNumberOfSeasons() + "):");

        Optional<String> resultado = dialog.showAndWait();

        if (resultado.isEmpty()) {
            // Usuário clicou em cancelar ou fechou a janela
            return null;
        }

        String input = resultado.get().trim();

        if (input.isEmpty()) {
            // Usuário clicou OK sem digitar nada
            return askForSeason(series, s);
        }

        try {
            int num = Integer.parseInt(resultado.get());
            return series.findSeason(num);
        } catch (NumberFormatException | SeasonNotFoundException e) {
            // Se a temporada não for encontrada, continua para exibir o alerta
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Número de temporada inválido");
            alert.setContentText("Por favor, insira um número válido entre 1 e " + series.getNumberOfSeasons() + ".");
            alert.showAndWait();
            return askForSeason(series, s); // Chama recursivamente até obter um número válido
        }

    }

    private void askForSeen(Series series, Season season) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Marcar como Visto");
        alert.setHeaderText("Deseja realmente marcar a temporada " + season.getSeasonNumber() +
                " de " + series.getTitle() + " como vista?");
        alert.setContentText("Essa mudança é irreversível.");
        ButtonType buttonContinuar = new ButtonType("Continuar");
        ButtonType buttonCancelar = ButtonType.CANCEL;

        alert.getButtonTypes().setAll(buttonContinuar, buttonCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonContinuar) {
            seriesService.markAsSeenSeason(series, season.getSeasonNumber());
            loadMediaList(); // Recarrega a lista de mídias após a remoção
        }
        // Se o usuário cancelar, não faz nada
    }
}
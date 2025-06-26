package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.MainFX;
import com.davigui.mediajournal.Model.Exceptions.SeasonNotFoundException;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Season;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.ViewFXControllers.RateScreens.RateScreenController;
import com.davigui.mediajournal.ViewFXControllers.RateScreens.RateSeasonScreenController;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterMovieScreenController;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterSeasonScreenController;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterSeriesScreenController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeriesTabContentController extends MediaContentController<Series> implements Initializable {

    //**********Atributos FXML***************
    @FXML
    private Label tableLabel;

    @FXML
    private TableColumn<Series, String> originalTitleColumn;

    @FXML
    private TableColumn<Series, String> endingYearColumn;

    @FXML
    private TableColumn<Series, Integer> seasonNumberColumn;

    @FXML
    private Label castInfo;

    @FXML
    private Label whereToWatchInfo;

    @FXML
    private Label seasonsInfo;

    @FXML
    private Button addSeasonButton;

    @FXML
    private Button seeSeasonButton;

    /*TODO: DECIDIR SE VOU IMPLEMENTAR ISSO
    @FXML
    private TableColumn<Series, String> seenDateColumn;
    */

    //************ Atributos NAO FXML *****************8

    private SeriesService seriesService;

    //************ Metodos *****************
    @Override
    protected void configureTable() {
        titleColumn.setCellValueFactory(cellData->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        yearColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getYear())).asObject());

        /*Aqui o ano de encerramento é tratado como um String. Dessa forma, podemos
        utilizar um ternário dentro da função lambda para retornar "Em andamento"
        se a série ainda não foi concluída*/
        endingYearColumn.setCellValueFactory(celldata->
                (new SimpleStringProperty(
                        celldata.getValue().getYearOfEnding() == 9999? "Em andamento" : Integer.toString(celldata.getValue().getYearOfEnding()))));

        ratingColumn.setCellValueFactory(celldata->
                new SimpleStringProperty("★".repeat(celldata.getValue().getRating())));

        originalTitleColumn.setCellValueFactory(celldata->
                new SimpleStringProperty(celldata.getValue().getOriginalTitle()));

        seasonNumberColumn.setCellValueFactory(celldata->
                (new SimpleIntegerProperty(celldata.getValue().getNumberOfSeasons())).asObject());
    }

    @Override
    protected void configureFilterChoices() {
        List<String> filterChoices = new ArrayList<>();
        filterChoices.add("Título");
        filterChoices.add("Ano");
        filterChoices.add("Gênero");
        filterChoices.add("Ator");

        filterTypeChoiceBox.setItems(FXCollections.observableArrayList(filterChoices));
    }

    private void actorSearch(String filter){
        mediaObservableList.setAll(seriesService.searchByActor(filter));
    }

    @Override
    protected void setService(CommonService<Series> service) {
        this.service = service;
        this.seriesService = (SeriesService) service;
    }

    @Override
    protected void handleSpecificSearch(String filter) {
        switch (selectedFilter.getValue()){
            case "Ator":
                actorSearch(filter);

            /*So tem um case no switch, mas vou deixar assim para
            caso for adicionar mais tipos de buscas*/
        }
    }

    @Override
    protected void handleMediaInfo(Series series) {
        String endingYear = (series.getYearOfEnding() == 9999) ? "Em andamento" : Integer.toString(series.getYearOfEnding());
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
package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.MainFX;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.ViewFXControllers.RegisterScreens.RegisterMovieScreenController;
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
    }

    @FXML
    public void onAddSeasonButtonClicked() throws IOException {
        //TODO: Implementar a tela de adicionar temporada
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
            System.out.println("Série removida: " + selectedSeries.getTitle());
        }
        // Se o usuário cancelar, não faz nada
    }
}
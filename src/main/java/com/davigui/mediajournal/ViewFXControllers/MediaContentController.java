package com.davigui.mediajournal.ViewFXControllers;

import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Media;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public abstract class MediaContentController<T extends Media> implements Initializable {
    // *********Atributos FXML******************

    @FXML
    private TableView<T> tableView;

    @FXML
    private Label tableLabel;

    @FXML
    private TableColumn<T, String> titleColumn;

    @FXML
    private TableColumn<T, Integer> yearColumn;

    @FXML
    private TableColumn<T, Integer> ratingColumn;

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

    private CommonService<T> service;

    private ObservableList<T> mediaObservableList;

    private ObservableValue<T> selectedItem;

    private ObservableValue<Genres> selectedGenre;

    private ObservableValue<String> selectedFilter;

    // ***********Metodos*******************
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //**************TABELA************************************

        configureTable();
        initTableListener();
        //***********TEXTFIELDS*********************
        filterTextField.setPromptText("Buscar");
        initTextFieldListener();

        //***********CHOICEBOXES*********************
        configureFilterChoices();
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

    public void setMediaService(CommonService<T> service) {
        this.service = service;
    }

    protected void loadMediaList(){
        mediaObservableList = FXCollections.observableArrayList(service.getAll());
        tableView.setItems(mediaObservableList);
        //TODO: PASSAR O  SET PARA O CONFIGURETABLE
    }

    protected void initTableListener() {

        selectedItem = tableView.getSelectionModel().selectedItemProperty();

        selectedItem.addListener((obs, oldValue, newValue) -> {
            if (newValue != null)
                handleMediaInfo(newValue);
            updateActionButtons();
        });
    }

    protected void updateActionButtons(){
        boolean isSelected = (selectedItem.getValue()!=null);
        editButton.setDisable(!isSelected);
        rateButton.setDisable(!isSelected);
        removeButton.setDisable(!isSelected);
    }

    protected void initFilterChoiceBoxListener() {

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

    protected void initGenreChoiceBoxListener() {

        selectedGenre = genreChoiceBox.getSelectionModel().selectedItemProperty();

        selectedGenre.addListener((obsValue, oldGenre, newGenre) -> {

            if (newGenre != null)
                handleSearch(null);

        });
    }

    protected void initTextFieldListener(){

        filterTextField.textProperty().addListener((obsValue, oldValue, newValue) -> {

            if(filterTypeChoiceBox.getValue().equals("Ano")){
                String onlyIntValue = newValue.replaceAll("[^\\d]", "");

                if(!newValue.equals(onlyIntValue)){
                    filterTextField.setText(onlyIntValue);
                }
            }
            //Valor atualizado do textField (depois do replace)
            handleSearch(filterTextField.getText());
        });
    }

    protected void handleSearch(String filter){

        //Se o filtro não for por gênero e for vazio, apenas recarrega a tabela
        if((!selectedFilter.getValue().equals("Gênero")) && (filter == null || filter.isEmpty())){
            loadMediaList();
            return;
        }

        switch (selectedFilter.getValue()){
            case "Título":
                titleSearch(filter);
                break;

            case "Ano":
                try {
                    yearSearch(Integer.parseInt(filter));
                }catch (NumberFormatException numberFormatException){
                    mediaObservableList.clear();
                    //TODO: LANCAR UM AVISO AQUI CASO DE MERDA?
                }
                break;

            case "Gênero":
                genreSearch(selectedGenre.getValue());
                break;

            default:
                handleSpecificSearch(filter);
                break;
        }
    }

    protected void titleSearch(String title){
        mediaObservableList.setAll(service.searchByTitle(title));
    }

    protected void genreSearch(Genres genre) {
        mediaObservableList.setAll(service.searchByGenre(genre));
    }

    protected void yearSearch(int year){
        mediaObservableList.setAll(service.searchByYear(year));
    }

    protected void setVisibleAndManaged(Control control, boolean active){
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
    protected void clearSearch() {
        mediaObservableList.setAll(service.getAll());
        filterTypeChoiceBox.getSelectionModel().clearSelection();
        toggleFilterTextField(false);
        toggleGenreChoiceBox(false);
    }

    protected void toggleGenreChoiceBox(boolean active){
        setVisibleAndManaged(genreChoiceBox, active);
        if(!active) genreChoiceBox.getSelectionModel().clearSelection();
    }

    protected void toggleFilterTypeComponents(boolean active){
        setVisibleAndManaged(filterTypeChoiceBox, active);
        setVisibleAndManaged(filterTypeLabel, active);
        setVisibleAndManaged(clearSearchButton, active);
        if(!active) filterTypeChoiceBox.getSelectionModel().clearSelection();
    }

    protected void toggleFilterTextField(boolean active){
        setVisibleAndManaged(filterTextField, active);
        if(!active) filterTextField.clear();
    }

    protected abstract void configureFilterChoices();
    protected abstract void configureTable();
    protected abstract void handleSpecificSearch(String filter);
    protected abstract void handleMediaInfo(T media);

}

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
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public abstract class MediaContentController<T extends Media> implements Initializable {
    // *********Atributos FXML******************

    @FXML
    protected SplitPane splitPane;

    @FXML
    protected TableView<T> tableView;

    @FXML
    protected Label tableLabel;

    @FXML
    protected TableColumn<T, String> titleColumn;

    @FXML
    protected TableColumn<T, Integer> yearColumn;

    @FXML
    protected TableColumn<T, Integer> ratingColumn;

    @FXML
    protected Button editButton;

    @FXML
    protected Button removeButton;

    @FXML
    protected Button rateButton;

    @FXML
    protected Button filterButton;

    @FXML
    protected TextField filterTextField;

    @FXML
    protected ChoiceBox<String> filterTypeChoiceBox;

    @FXML
    protected Label filterTypeLabel;

    @FXML
    protected ChoiceBox<Genres> genreChoiceBox;

    @FXML
    protected Button clearSearchButton;

    @FXML
    protected VBox mediaInfoVbox;

    @FXML
    protected ImageView coverViewInfo;

    @FXML
    protected Label titleYearInfo;

    @FXML
    protected Label genreInfo;

    @FXML
    protected Label ratingInfo;

    @FXML
    protected Label reviewInfo;

    // *********Atributos nao FXMLs******************

    protected CommonService<T> service;

    protected ObservableList<T> mediaObservableList;

    protected ObservableValue<T> selectedItem;

    protected ObservableValue<Genres> selectedGenre;

    protected ObservableValue<String> selectedFilter;

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

        //**********VBOXES************
        hideMediaInfo();
    }

    protected void loadMediaList(){
        mediaObservableList = FXCollections.observableArrayList(service.getAll());
        tableView.setItems(mediaObservableList);
        //TODO: PASSAR O  SET PARA O CONFIGURETABLE
    }

    protected void initTableListener() {

        selectedItem = tableView.getSelectionModel().selectedItemProperty();

        selectedItem.addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                showMediaInfo();
                handleMediaInfo(newValue);
            }else
                hideMediaInfo();
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
    protected void onFilterButtonClicked() {

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
        tableView.getSelectionModel().clearSelection();
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

    protected void hideMediaInfo(){
        mediaInfoVbox.setVisible(false);
        mediaInfoVbox.setManaged(false);
        splitPane.setDividerPosition(0, 1);

    }

    protected void showMediaInfo(){
        mediaInfoVbox.setVisible(true);
        mediaInfoVbox.setManaged(true);
        splitPane.setDividerPosition(0, 0.7);
    }

    protected abstract void setService(CommonService<T> service);
    protected abstract void configureFilterChoices();
    protected abstract void configureTable();
    protected abstract void handleSpecificSearch(String filter);
    protected abstract void handleMediaInfo(T media);

}

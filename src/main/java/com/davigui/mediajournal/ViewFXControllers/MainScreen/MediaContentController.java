package com.davigui.mediajournal.ViewFXControllers.MainScreen;

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

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe abstrata que contém os métodos compartilhados entre as cenas de mídias.
 * Deve ser parametrizada com o tipo de mídia a ser trabalhada. Implementa a interface
 * Initializable e o método initialize() passa a ser um Template Method e sua
 * implementação se dá pela sobrecarga dos Hook Methods.
 * Os atributos comuns referentes à tela são injetados por FXML.
 *
 * @param <T> O tipo de mídia utilizada pelo controlador.
 */
public abstract class MediaContentController<T extends Media> implements Initializable {
    // *********Atributos FXML******************

    /**
     * Painel principal. À esquerda têm-se a tabela e as funcionalidades e à
     * direita as informações da mídia.
     */
    @FXML
    protected SplitPane splitPane;

    /**
     * A tabela da mídia parametrizada.
     */
    @FXML
    protected TableView<T> tableView;

    /**
     * O título da tabela.
     */
    @FXML
    protected Label tableLabel;

    /**
     * Coluna de título da mídia parametrizada.
     */
    @FXML
    protected TableColumn<T, String> titleColumn;

    /**
     * Coluna de ano da mídia parametrizada.
     */
    @FXML
    protected TableColumn<T, Integer> yearColumn;

    /**
     * Coluna de avaliação/nota da mídia parametrizada.
     */
    @FXML
    protected TableColumn<T, String> ratingColumn;

    /**
     * Botão de adicionar.
     */
    @FXML
    protected Button addButton;

    /**
     * Botão de remover.
     */
    @FXML
    protected Button removeButton;

    /**
     * Botão de avaliar/escrever review.
     */
    @FXML
    protected Button rateButton;

    /**
     * Botão de filtro/busca.
     */
    @FXML
    protected Button filterButton;

    /**
     * Botão de marcar como visto.
     */
    @FXML
    protected Button seenButton;

    /**
     * Campo de texto para filtrar mídias.
     */
    @FXML
    protected TextField filterTextField;

    /**
     * Caixa de escolha para escolher o critério de busca (título, ano, etc.).
     */
    @FXML
    protected ChoiceBox<String> filterTypeChoiceBox;

    /**
     * Título da caixa de escolha de busca.
     */
    @FXML
    protected Label filterTypeLabel;

    /**
     * Caixa de escolha do gênero desejado para busca.
     */
    @FXML
    protected ChoiceBox<Genres> genreChoiceBox;

    /**
     * Botão de limpar filtros.
     */
    @FXML
    protected Button clearSearchButton;

    /**
     * Vbox contendo os nodes com as informações de mídia.
     */
    @FXML
    protected VBox mediaInfoVbox;

    /**
     * Imagem/capa da mídia.
     */
    @FXML
    protected ImageView coverViewInfo;

    /**
     * As informações de título e ano da obra selecionada na tabela.
     */
    @FXML
    protected Label titleYearInfo;

    /**
     * O gênero da obra selecionada na tabela.
     */
    @FXML
    protected Label genreInfo;

    /**
     * A nota da obra selecionada na tabela.
     */
    @FXML
    protected Label ratingInfo;

    /**
     * A resenha da obra selecionada na tabela.
     */
    @FXML
    protected Label reviewInfo;

    // *********Atributos nao FXMLs******************

    /**
     * O controlador de modelo do tipo de mídia parametrizado. Utiliza os métodos
     * da classe abstrata CommonService por composição.
     */
    protected CommonService<T> service;

    /**
     * Lista observável da mídia parametrizada usada na tabela. A atualização
     * dos items se dá pelo método setAll().
     */
    protected ObservableList<T> mediaObservableList;

    /**
     * A mídia selecionada na tabela. A seleção não é apagada ao clicar em um
     * elemento fora da tabela ou ao alternar entre abas.
     */
    protected ObservableValue<T> selectedItem;

    /**
     * O gênero selecionado na caixa de escolha de busca por gênero.
     */
    protected ObservableValue<Genres> selectedGenre;

    /**
     * O filtro selecionado na caixa de escolha de critério de busca.
     */
    protected ObservableValue<String> selectedFilter;

    // ***********Metodos*******************

    /**
     * O método de inicialização dos elementos do GUI. É um Template Method que
     * utiliza os Hook Methods: {@code configureFilterChoices()} e {@code configureTable()}.
     * As subclasses devem implementar esses métodos de suas maneiras. Dessa forma,
     * o método initialize não precisa ser implementado pelas subclasses, sendo usado
     * o desta superclasse ao carregar o FXML.
     * <p>
     * O método começa configurando a tabela e seu listener. Em seguida, define
     * o texto padrão do campo de busca e inicializa seu listener.
     * Depois, configura as opções das caixas de escolha de filtro e de gênero,
     * também iniciando seus respectivos listeners. Por fim, desativa os
     * componentes relacionados à busca, oculta a Vbox de informações de mídia
     * e desabilita os botões de ação (avaliar, remover, etc.).
     */
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

    /**
     * Carrega a lista observável de mídias e a atribui à tabela.
     * A lista original de mídias é obtida a partir do controlador
     * e convertida em uma lista observável utilizando o método
     * estático {@code FXCollections.observableArrayList()}.
     */
    protected void loadMediaList(){
        mediaObservableList = FXCollections.observableArrayList(service.getAll());
        tableView.setItems(mediaObservableList);
        //TODO: PASSAR O  SET PARA O CONFIGURETABLE
    }

    /**
     * Inicializa o listener de seleção da tabela.
     * Sempre que uma nova mídia for selecionada, os dados serão exibidos
     * no painel lateral direito (VBox) por meio do método {@code handleMediaInfo()}.
     * Se a nova seleção for nula, a exibição lateral é escondida através do método
     * {@code hideMediaInfo()}.
     * Por fim, os botões de ação são habilitados ou desativados com base na
     * seleção no método {@code updateActionButtons()}.
     */
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

    /**
     * Atualiza o estado dos botões de ação (avaliar, remover, marcar como visto).
     * Os botões só são habilitados se uma mídia estiver selecionada.
     */
    protected void updateActionButtons(){
        boolean isSelected = (selectedItem.getValue()!=null);
        rateButton.setDisable(!isSelected);
        removeButton.setDisable(!isSelected);
        seenButton.setDisable(!isSelected);
    }

    /**
     * Inicializa o listener da caixa de escolha de filtro.
     * Alterna entre os campos de busca por texto e por gênero de acordo com
     * o novo valor observado.
     */
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

    /**
     * Inicializa o listener da caixa de escolha de gênero.
     * A busca é automaticamente realizada após observar um novo valor não nulo.
     */
    protected void initGenreChoiceBoxListener() {

        selectedGenre = genreChoiceBox.getSelectionModel().selectedItemProperty();

        selectedGenre.addListener((obsValue, oldGenre, newGenre) -> {

            if (newGenre != null)
                handleSearch(null);

        });
    }

    /**
     * Inicializa o listener do campo de texto de busca.
     * <p>
     * O listener é acionado a cada alteração no conteúdo do campo de texto,
     * ou seja, a cada novo caractere digitado.
     * </p>
     * Caso o critério de busca atual seja "Ano", todos os caracteres não numéricos
     * são removidos do valor digitado. Se houver alteração após essa limpeza
     * (o valor original continha letras), o campo de texto é atualizado para
     * refletir apenas os dígitos válidos.
     * </p>
     * Após esse processamento, o texto resultante é repassado ao método {@code handleSearch()},
     * que executa a lógica de filtragem apropriada.
     */

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

    /**
     * Executa a lógica de busca com base no filtro selecionado.
     * </p>
     * Se o filtro passado for uma String nula ou vazia e o critério de busca
     * não for Gênero, recarrega toda a lista de mídias.
     * </p>
     * O fluxo é delegado de acordo com a seleção do critério de busca. Se a busca
     * for por Ano, o filtro é convertido em inteiro e, caso isto não seja possível,
     * captura a exceção e limpa a lista de mídias.
     * </p>
     * Os casos específicos das subclasses são feitos através do método {@code
     * handleSpecificSearch()}.
     * @param filter o valor inserido no campo de busca
     */
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

    /**
     * Realiza a busca por título.
     * </p>
     * A busca é executada pelo controlador de modelo da mídia com base no título
     * e a lista retornada é atribuida à lista observável de mídias.
     * @param title O título da obra
     */
    protected void titleSearch(String title){
        mediaObservableList.setAll(service.searchByTitle(title));
    }

    /**
     * Realiza a busca por gênero.
     * </p>
     * A busca é executada pelo controlador de modelo da mídia, com base no gênero
     * fornecido, e a lista retornada é atribuída à lista observável de mídias.
     *
     * @param genre O gênero da obra
     */
    protected void genreSearch(Genres genre) {
        mediaObservableList.setAll(service.searchByGenre(genre));
    }

    /**
     * Realiza a busca por ano.
     * </p>
     * A busca é executada pelo controlador de modelo da mídia com base no ano informado e
     * a lista retornada é atribuída à lista observável de mídias.
     *
     * @param year O ano de lançamento da obra
     */
    protected void yearSearch(int year){
        mediaObservableList.setAll(service.searchByYear(year));
    }

    /**
     * Ativa ou desativa a visibilidade e o gerenciamento de um componente.
     *
     * @param control O componente
     * @param active  O estado
     */
    protected void setVisibleAndManaged(Control control, boolean active){
        control.setVisible(active);
        control.setManaged(active);
    }

    /**
     * Evento chamado ao clicar no botão de filtro.
     * Alterna a visibilidade dos componentes relacionados ao filtro.
     */
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

    /**
     * Limpa todos as seleções e filtros aplicados, desativa o campo de texto e
     * caixa de escolha de gênero e recarrega a tabela com todas as mídias.
     */
    @FXML
    protected void clearSearch() {
        mediaObservableList.setAll(service.getAll());
        filterTypeChoiceBox.getSelectionModel().clearSelection();
        tableView.getSelectionModel().clearSelection();
        toggleFilterTextField(false);
        toggleGenreChoiceBox(false);
    }

    /**
     * Alterna a visibilidade e o estado da caixa de escolha de gênero. Se o
     * novo estado for desativado, também limpa a seleção.
     *
     * @param active O novo estado
     */
    protected void toggleGenreChoiceBox(boolean active){
        setVisibleAndManaged(genreChoiceBox, active);
        if(!active) genreChoiceBox.getSelectionModel().clearSelection();
    }

    /**
     * Alterna a visibilidade dos componentes de tipo de filtro (label,
     * choiceBox e botão de limpar). Se o novo estado for desativado, também
     * limpa a seleção.
     *
     * @param active O novo estado
     */
    protected void toggleFilterTypeComponents(boolean active){
        setVisibleAndManaged(filterTypeChoiceBox, active);
        setVisibleAndManaged(filterTypeLabel, active);
        setVisibleAndManaged(clearSearchButton, active);
        if(!active) filterTypeChoiceBox.getSelectionModel().clearSelection();
    }

    /**
     * Alterna a visibilidade do campo de busca textual. Se o novo estado for
     * desativado, também limpa o campo de texto.
     *
     * @param active O novo estado
     */
    protected void toggleFilterTextField(boolean active){
        setVisibleAndManaged(filterTextField, active);
        if(!active) filterTextField.clear();
    }

    /**
     * Oculta a seção de informações detalhadas da mídia.
     * </p>
     * Define a VBox de informações como invisível e não gerenciada no layout,
     * impedindo que ocupe espaço na interface. Além disso, atualiza a posição do divisor
     * do  SplitPane, expandindo totalmente a área da tabela e ocultando a área
     * lateral.
     */
    protected void hideMediaInfo(){
        mediaInfoVbox.setVisible(false);
        mediaInfoVbox.setManaged(false);
        splitPane.setDividerPosition(0, 1);

    }

    /**
     * Mostra a seção de informações detalhadas da mídia.
     * </p>
     * Define a VBox de informações como visível e gerenciável no layout e
     * atualiza a posição do divisor do SplitPane para mostrar as informações
     * na área lateral.
     */
    protected void showMediaInfo(){
        mediaInfoVbox.setVisible(true);
        mediaInfoVbox.setManaged(true);
        splitPane.setDividerPosition(0, 0.7);
    }

    /**
     * Define o controlador de modelo da mídia.
     *
     * @param service a instância do serviço de mídia
     */
    protected abstract void setService(CommonService<T> service);

    /**
     * Configura a caixa de escolha de tipos de filtros. As opções de
     * critérios de busca devem ser implementadas pelas subclasses.
     */
    protected abstract void configureFilterChoices();

    /**
     * Configura as colunas da tabela principal. As subclasses devem implementar
     * quais colunas serão utilizadas.
     */
    protected abstract void configureTable();

    /**
     * Lida com buscas por critérios que não sejam título, ano ou gênero. As subclasses
     * devem adicionar os critérios específicos num bloco switch-case e criar
     * o método de busca correspondente.
     *
     * @param filter O filtro original que será repassado
     */
    protected abstract void handleSpecificSearch(String filter);

    /**
     * Carrega e exibe as informações detalhadas da mídia selecionada na VBox lateral.
     * </p>
     * Este método deve ser implementado pelas subclasses para definir quais
     * atributos específicos da mídia devem ser exibidos e como serão apresentados
     * nos componentes visuais da tela.
     *
     * @param media A mídia atualmente selecionada na tabela
     */
    protected abstract void handleMediaInfo(T media);

    // ******TODO: GUILHERME ESSA DOC E COM TU *********************************

    /**
     * On add button clicked.
     *
     * @throws IOException the io exception
     */
    @FXML abstract void onAddButtonClicked() throws IOException;

    /**
     * On remove button clicked.
     */
    @FXML abstract void onRemoveButtonClicked();

    /**
     * On rate button clicked.
     *
     * @throws IOException the io exception
     */
    @FXML abstract void onRateButtonClicked() throws IOException;

    /**
     * On seen button clicked.
     *
     * @throws IOException the io exception
     */
    @FXML abstract void onSeenButtonClicked() throws IOException;

}

package com.davigui.mediajournal.ViewFXControllers.MainScreen;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Repository.DataOperations;
import com.davigui.mediajournal.Model.Repository.Library;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador da tela principal da aplicação.
 * <p>
 * Gerencia as abas de mídias (livros, filmes e séries) e seus respectivos
 * controladores de conteúdo.
 * <p>
 * Inicializa os serviços específicos de cada tipo de mídia e carrega
 * as listas correspondentes ao iniciar a aplicação.
 */
public class MainScreenController implements Initializable {

    //*********Atributos FXML ************

    /**
     * Painel principal com as abas de mídias (livros, filmes, séries).
     */
    @FXML
    private TabPane mediasTabPane;

    /**
     * Aba que contém o conteúdo das séries.
     */
    @FXML
    private Tab seriesTab;

    /**
     * Aba que contém o conteúdo dos livros.
     */
    @FXML
    private Tab booksTab;

    /**
     * Aba que contém o conteúdo dos filmes.
     */
    @FXML
    private Tab moviesTab;

    /**
     * Controlador da aba de livros.
     */
    @FXML
    private BooksTabContentController booksTabContentController;

    /**
     * Controlador da aba de filmes.
     */
    @FXML
    private MoviesTabContentController moviesTabContentController;

    /**
     * Controlador da aba de séries.
     */
    @FXML
    private SeriesTabContentController seriesTabContentController;

    //*********Atributos NÃO FXML ************

    /**
     * Instância da biblioteca principal que armazena todas as mídias.
     */
    private Library journal;

    /**
     * Inicializa o controlador principal da aplicação.
     * <p>
     * Cria uma nova biblioteca, carrega os dados salvos e inicializa os serviços
     * específicos para livros, filmes e séries.
     * Os controladores das abas são injetados por FXML e então definem os
     * serviços criados e carregam suas respectivas listas de mídias.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.journal = new Library();
        DataOperations.load(journal);

        BookService bookService = new BookService(journal);
        MovieService movieService = new MovieService(journal);
        SeriesService seriesService = new SeriesService(journal);

        booksTabContentController.setService(bookService);
        booksTabContentController.configureMediaList();

        moviesTabContentController.setService(movieService);
        moviesTabContentController.configureMediaList();

        seriesTabContentController.setService(seriesService);
        seriesTabContentController.configureMediaList();
    }

    /**
     * Salva os dados da biblioteca.
     * <p>
     * Este método delega a operação para a classe {@code DataOperations}.
     */
    public void saveLibrary() {
        DataOperations.save(journal);
    }
}


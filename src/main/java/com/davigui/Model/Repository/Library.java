package com.davigui.Model.Repository;

import com.davigui.Model.Exceptions.MediaAlreadyExistsException;
import com.davigui.Model.Medias.Book;
import com.davigui.Model.Medias.Movie;
import com.davigui.Model.Medias.Series;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * A classe Library representa uma biblioteca que gerencia listas de livros, filmes e séries,
 * além de registrar os anos associados às mídias cadastradas.
 */
public class Library {
    // Lista de livros cadastrados na biblioteca
    private ArrayList<Book> bookList;
    // Lista de filmes cadastrados na biblioteca
    private ArrayList<Movie> movieList;
    // Lista de séries cadastradas na biblioteca
    private ArrayList<Series> seriesList;
    // Conjunto de anos registrados na biblioteca
    private TreeSet<Integer> yearsRegistered;

    /**
     * Construtor da classe Library.
     * Inicializa as listas de livros, filmes, séries e o conjunto de anos registrados.
     */
    public Library() {
        this.bookList = new ArrayList<>();
        this.movieList = new ArrayList<>();
        this.seriesList = new ArrayList<>();
        this.yearsRegistered = new TreeSet<>();
    }

    /**
     * Verifica se um livro já está cadastrado na biblioteca.
     *
     * @param book O livro a ser verificado.
     * @throws MediaAlreadyExistsException Se o livro já estiver cadastrado.
     */
    public void exists(Book book) throws MediaAlreadyExistsException {
        for (Book bookE : bookList) {
            if (book.getId() == bookE.getId())
                throw new MediaAlreadyExistsException("Esse livro já foi cadastrado!");
        }
    }

    /**
     * Verifica se um filme já está cadastrado na biblioteca.
     *
     * @param movie O filme a ser verificado.
     * @throws MediaAlreadyExistsException Se o filme já estiver cadastrado.
     */
    public void exists(Movie movie) throws MediaAlreadyExistsException {
        for (Movie movieE : movieList) {
            if (movie.getId() == movieE.getId())
                throw new MediaAlreadyExistsException("Esse filme já foi cadastrado!");
        }
    }

    /**
     * Verifica se uma série já está cadastrada na biblioteca.
     *
     * @param series A série a ser verificada.
     * @throws MediaAlreadyExistsException Se a série já estiver cadastrada.
     */
    public void exists(Series series) throws MediaAlreadyExistsException {
        for (Series seriesE : seriesList) {
            if (series.getId() == seriesE.getId())
                throw new MediaAlreadyExistsException("Essa série já foi cadastrada!");
        }
    }

    /**
     * Adiciona um livro à lista de livros da biblioteca.
     *
     * @param book O livro a ser adicionado.
     */
    public void addBook(Book book) {
        bookList.add(book);
    }

    /**
     * Adiciona um filme à lista de filmes da biblioteca.
     *
     * @param movie O filme a ser adicionado.
     */
    public void addMovie(Movie movie) {
        movieList.add(movie);
    }

    /**
     * Adiciona uma série à lista de séries da biblioteca.
     *
     * @param series A série a ser adicionada.
     */
    public void addSeries(Series series) {
        seriesList.add(series);
    }

    /**
     * Adiciona um ano ao conjunto de anos registrados na biblioteca.
     *
     * @param year O ano a ser adicionado.
     */
    public void addYear(int year) {
        yearsRegistered.add(year);
    }

    /**
     * Obtém a lista de livros cadastrados na biblioteca.
     *
     * @return Uma lista de livros.
     */
    public ArrayList<Book> getBookList() {
        return bookList;
    }

    /**
     * Obtém a lista de séries cadastradas na biblioteca.
     *
     * @return Uma lista de séries.
     */
    public ArrayList<Series> getSeriesList() {
        return seriesList;
    }

    /**
     * Obtém a lista de filmes cadastrados na biblioteca.
     *
     * @return Uma lista de filmes.
     */
    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    /**
     * Obtém o conjunto de anos registrados na biblioteca.
     *
     * @return Um conjunto de anos.
     */
    public TreeSet<Integer> getYearsRegistered() {
        return yearsRegistered;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public void setSeriesList(ArrayList<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public void setYearsRegistered(TreeSet<Integer> yearsRegistered) {
        this.yearsRegistered = yearsRegistered;
    }
}
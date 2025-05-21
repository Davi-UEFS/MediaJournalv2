package com.davigui.mediajournal.Model.Repository;

import com.davigui.mediajournal.Model.Exceptions.MediaAlreadyExistsException;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.Model.Medias.Series;


import java.util.ArrayList;
import java.util.TreeMap;

/**
 * A classe Library representa uma biblioteca que gerencia listas de livros,
 * filmes e séries, além de registrar os anos associados às mídias cadastradas.
 */
public class Library {
    // Lista de livros cadastrados na biblioteca
    private ArrayList<Book> bookList;
    // Lista de filmes cadastrados na biblioteca
    private ArrayList<Movie> movieList;
    // Lista de séries cadastradas na biblioteca
    private ArrayList<Series> seriesList;
    // Mapa de anos registrados na biblioteca (ordenado pelo número do ano)
    private TreeMap<Integer, Integer> yearsRegistered;

    /**
     * Construtor da classe Library.
     * Inicializa as listas de livros, filmes, séries e o
     * mapa de anos.
     */
    public Library() {
        this.bookList = new ArrayList<>();
        this.movieList = new ArrayList<>();
        this.seriesList = new ArrayList<>();
        this.yearsRegistered = new TreeMap<>();
    }

    /**
     * Verifica se um livro já está cadastrado na biblioteca.
     * Não utiliza o equals() de Object. Em vez disso, compara os IDs.
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
     * Não utiliza o equals() de Object. Em vez disso, compara os IDs.
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
     * Não utiliza o equals() de Object. Em vez disso, compara os IDs.
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
    public void add(Book book) {
        bookList.add(book);
    }

    /**
     * Adiciona um filme à lista de filmes da biblioteca.
     *
     * @param movie O filme a ser adicionado.
     */
    public void add(Movie movie) {
        movieList.add(movie);
    }

    /**
     * Adiciona uma série à lista de séries da biblioteca.
     *
     * @param series A série a ser adicionada.
     */
    public void add(Series series) {
        seriesList.add(series);
    }

    /**
     * Adiciona um ano ao mapa de anos registrados na biblioteca com ocorrência 1.
     * Se o ano já existir, sua ocorrência é incrementada em 1.
     *
     * @param year O ano a ser adicionado.
     */
    public void addYear(int year) {
        if (yearsRegistered.containsKey(year)) {
            yearsRegistered.put(year, yearsRegistered.get(year) + 1);
        } else{
            yearsRegistered.put(year, 1);
        }
    }

    /**
     * Remove um livro da lista de livros da biblioteca.
     *
     * @param book O livro a ser removido.
     */
    public void remove(Book book) {
        bookList.remove(book);
    }

    /**
     * Remove um filme da lista de filmes da biblioteca.
     *
     * @param movie O filme a ser removido.
     */
    public void remove(Movie movie) {
        movieList.remove(movie);
    }

    /**
     * Remove uma série da lista de séries da biblioteca.
     *
     * @param series A série a ser removida.
     */
    public void remove(Series series) {
        seriesList.remove(series);
    }

    /**
     * Remove uma ocorrência do ano no mapa de anos registrados na biblioteca.
     * Se após isso ano não tiver mais associações, ele será completamente removido.
     *
     * @param year O ano a ser removido.
     */
    public void removeYear(int year) {
        if (yearsRegistered.containsKey(year)) {
            yearsRegistered.put(year, yearsRegistered.get(year) - 1);
            if (yearsRegistered.get(year) == 0)
                yearsRegistered.remove(year);
        }
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
     * Obtém o mapa de anos registrados na biblioteca, ordenado de forma crescente.
     *
     * @return Um mapa ordenado contendo os anos registrados e suas respectivas contagens.
     */
    public TreeMap<Integer, Integer> getYearsRegistered() {
        return yearsRegistered;
    }

    /**
     * Define a lista de livros cadastrados na biblioteca.
     *
     * @param bookList A nova lista de livros a ser definida.
     */
    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    /**
     * Define a lista de filmes cadastrados na biblioteca.
     *
     * @param movieList A nova lista de filmes a ser definida.
     */
    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     * Define a lista de séries cadastradas na biblioteca.
     *
     * @param seriesList A nova lista de séries a ser definida.
     */
    public void setSeriesList(ArrayList<Series> seriesList) {
        this.seriesList = seriesList;
    }

    /**
     * Define o mapa de anos registrados na biblioteca.
     *
     * @param yearsRegistered O novo mapa de anos registrados a ser definido.
     */
    public void setYearsRegistered(TreeMap<Integer, Integer> yearsRegistered) {
        this.yearsRegistered = yearsRegistered;
    }

}

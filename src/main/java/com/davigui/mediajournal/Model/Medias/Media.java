package com.davigui.mediajournal.Model.Medias;

import com.davigui.mediajournal.Model.Enums.Genres;

/**
 * A classe abstrata Media representa uma mídia genérica com propriedades comuns,
 * como título, ano, gênero, avaliação, status de visualização e review.
 * Esta classe serve como base para tipos específicos de mídia.
 */
public abstract class Media {
    // O título da mídia
    protected String title;
    // O ano de lançamento da mídia
    protected int year;
    // O gênero da mídia
    protected  Genres genre;
    // A avaliação da mídia (nota de 0 a 5)
    protected int rating;
    // Indica se a mídia foi vista
    protected boolean seen;
    // A review da mídia
    protected String review;

    /**
     * Construtor da classe Media.
     * Os parâmetros são os atributos comuns e imutáveis de uma mídia.
     * Os atributos mutáveis são inicializados como 0/null/false.
     *
     * @param title O título da mídia.
     * @param year  O ano de lançamento da mídia.
     * @param genre O gênero da mídia.
     */
    public Media(String title, int year, Genres genre) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.seen = false;
        this.rating = 0;
        this.review = null;
    }

    /**
     * Construtor no args utilizados pelas subclasses.
     * As subclasses usam este construtor por um super() implícito.
     */
    public Media(){

    }

    /**
     * Obtém a avaliação da mídia.
     *
     * @return A nota da mídia.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Define a avaliação da mídia.
     *
     * @param rating A nota a ser atribuída à mídia.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Obtém o título da mídia.
     *
     * @return O título da mídia.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Obtém o ano de lançamento da mídia.
     *
     * @return O ano de lançamento da mídia.
     */
    public int getYear() {
        return year;
    }

    /**
     * Obtém o gênero da mídia.
     *
     * @return O gênero da mídia.
     */
    public Genres getGenre() {
        return genre;
    }

    /**
     * Verifica se a mídia foi vista.
     *
     * @return true se a mídia foi vista, false caso contrário.
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * Marca a mídia como vista (true).
     */
    public void setSeen() {
        this.seen = true;
    }

    /**
     * Obtém a review da mídia.
     *
     * @return A review da mídia.
     */
    public String getReview() {
        return review;
    }

    /**
     * Define a review da mídia.
     *
     * @param review O texto da review.
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Define o título da mídia.
     *
     * @param title O título a ser atribuído à mídia.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Define o gênero da mídia.
     *
     * @param genre O gênero a ser atribuído à mídia.
     */
    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    /**
     * Define o ano de lançamento da mídia.
     *
     * @param year O ano a ser atribuído à mídia.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Obtém o tipo de mídia.
     * Implementada por suas subclasses.
     *
     * @return Uma string representando o tipo de mídia.
     */
    public abstract String getMediaType();

    /**
     * Obtém o identificador único da mídia com base no
     * hash do título e no ano multiplicado por um primo.
     *
     * @return O identificador único da mídia.
     */
    public int getId() {
        return title.hashCode() + 227 * year;
    }
}
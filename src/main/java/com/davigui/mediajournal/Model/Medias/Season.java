package com.davigui.mediajournal.Model.Medias;

/**
 * A classe Season representa uma temporada de uma série, contendo informações como
 * número da temporada, quantidade de episódios, ano de lançamento, avaliação, review
 * e status de visualização.
 * Implementa a interface Comparable para ser organizada num TreeSet.
 */
public class Season implements Comparable<Season> {
    // Avaliação da temporada
    private int rating;
    // Número da temporada
    private int seasonNumber;
    // Review da temporada
    private String review;
    // Indica se a temporada foi vista
    private boolean seen;
    // Quantidade de episódios na temporada
    private int episodeCount;
    // Ano de lançamento da temporada
    private int year;

    /**
     * Construtor da classe Season.
     * Os parâmetros são os atributos imutáveis da temporada.
     * Os atributos imutáveis são inicializados em 0/null/false.
     *
     * @param seasonNumber O número da temporada.
     * @param episodeCount A quantidade de episódios na temporada.
     * @param year         O ano de lançamento da temporada.
     */
    public Season(int seasonNumber, int episodeCount, int year) {
        this.seasonNumber = seasonNumber;
        this.episodeCount = episodeCount;
        this.year = year;
        this.seen = false;
        this.rating = 0;
        this.review = null;
    }

    /**
     * Construtor no args da classe Season usado na desserialização.
     */
    public Season(){

    }

    /**
     * Sobrescreve o método compareTo da interface Comparable.
     * Compara esta temporada com outra com base no número da temporada.
     *
     * @param other A outra temporada a ser comparada.
     * @return Um valor negativo, zero ou positivo se esta temporada for, respectivamente,
     * menor, igual ou maior que a outra.
     */
    @Override
    public int compareTo(Season other) {
        return Integer.compare(this.seasonNumber, other.seasonNumber);
    }

    /**
     * Sobrecarrega o método  de Object.
     * Retorna uma representação em string da temporada, incluindo o número da temporada,
     * ano de lançamento e quantidade de episódios.
     *
     * @return Uma string representando a temporada.
     */
    public String toString() {
        return "Temporada: " + seasonNumber + " (" + year + ") - " + episodeCount + " Episódios";
    }

    /**
     * Obtém a avaliação da temporada.
     *
     * @return A avaliação da temporada.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Define a avaliação da temporada.
     *
     * @param rating A avaliação a ser atribuída à temporada.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Obtém a review da temporada.
     *
     * @return A review da temporada.
     */
    public String getReview() {
        return review;
    }

    /**
     * Define a review da temporada.
     *
     * @param review A texto da review a ser atribuída à temporada.
     */
    public void setReview(String review) {
        this.review = review;
    }

    /**
     * Marca a temporada como vista.
     */
    public void setSeen() {
        this.seen = true;
    }

    /**
     * Define o ano de lançamento da temporada.
     *
     * @param year O ano de lançamento da temporada.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Verifica se a temporada foi vista.
     *
     * @return true se a temporada foi vista, false caso contrário.
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * Obtém o número da temporada.
     *
     * @return O número da temporada.
     */
    public int getSeasonNumber() {
        return seasonNumber;
    }
}
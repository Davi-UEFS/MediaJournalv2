package com.davigui.mediajournal.Model.Medias;

import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Exceptions.SeasonNotFoundException;

import java.util.List;
import java.util.TreeSet;

/**
 * A classe Series representa uma série como uma mídia, estendendo a classe base Media.
 * Ela contém informações específicas de séries, como ano de término, elenco, temporadas,
 * título original e plataformas onde assistir.
 */
public class Series extends Media {
    // Ano de término da série (9999 indica que está em andamento)
    private int yearOfEnding;
    // Lista com os nomes do elenco da série
    private List<String> cast;
    // Conjunto ordenado de temporadas da série
    private TreeSet<Season> seasons;
    // Título original da série
    private String originalTitle;
    // Lista de plataformas onde a série pode ser assistida
    private List<String> whereToWatch;

    /**
     * Construtor da classe Series.
     *
     * @param name           O nome da série.
     * @param year           O ano de início da série.
     * @param genre          O gênero da série.
     * @param yearOfEnding   O ano de término da série (9999 para séries em andamento).
     * @param cast           A lista com os nomes do elenco da série.
     * @param originalTitle  O título original da série.
     * @param whereToWatch   A lista de plataformas onde a série pode ser assistida.
     */
    public Series(String name, int year, Genres genre, int yearOfEnding, List<String> cast, String originalTitle, List<String> whereToWatch) {
        super(name, year, genre);
        this.yearOfEnding = yearOfEnding;
        this.cast = cast;
        this.seasons = new TreeSet<>();
        this.originalTitle = originalTitle;
        this.whereToWatch = whereToWatch;
    }

    /**
     * Adiciona uma temporada à série.
     *
     * @param season A temporada a ser adicionada.
     */
    public void addSeason(Season season){
        this.seasons.add(season);
    }

    /**
     * Busca uma temporada pelo número da temporada.
     *
     * @param seasonNumber O número da temporada a ser buscada.
     * @return A temporada correspondente ao número fornecido.
     * @throws SeasonNotFoundException Se a temporada não for encontrada.
     */
    public Season findSeason(int seasonNumber) throws SeasonNotFoundException {
        for (Season season : seasons) {
            if (seasonNumber == season.getSeasonNumber())
                return season;
        }
        throw new SeasonNotFoundException("Temporada não encontrada!");
    }

    /**
     * Atualiza a avaliação geral da série com base na média das avaliações das temporadas.
     * Se não houver temporadas, a avaliação será definida como 0.
     */
    public void updateRate() {
        int sum = 0;

        if (seasons.isEmpty())
            setRating(0);
        else {
            for (Season season : seasons) {
                sum += season.getRating();
            }
            setRating(sum / seasons.size());
        }
    }

    /**
     * Obtém o ano de término da série.
     *
     * @return O ano de término da série.
     */
    public int getYearOfEnding() {
        return yearOfEnding;
    }

    /**
     * Obtém a lista com os nomes do elenco da série.
     *
     * @return A lista com os nomes do elenco.
     */
    public List<String> getCast() {
        return cast;
    }

    /**
     * Obtém o tipo de mídia, que neste caso é "Série".
     *
     * @return Uma string representando o tipo de mídia.
     */
    @Override
    public String getMediaType(){
        return "Série";
    }

    /**
     * Retorna uma representação em string da série, incluindo título, anos de início e término,
     * título original, plataformas onde assistir, elenco e avaliação (se disponível).
     *
     * @return Uma string representando a série.
     */
    @Override
    public String toString() {
        String endingYear = (yearOfEnding == 9999) ? "Em andamento" : Integer.toString(yearOfEnding);

        StringBuilder castString = new StringBuilder(cast.toString());
        castString.deleteCharAt(0).deleteCharAt(castString.length() - 1);

        StringBuilder watchString = new StringBuilder(whereToWatch.toString());
        watchString.deleteCharAt(0).deleteCharAt(watchString.length() - 1);

        StringBuilder string = new StringBuilder(
                "\n" + title + " (" + year + " - "  + endingYear + ")" +
                "\nTítulo original: " + originalTitle +
                "\nOnde assistir: " + watchString +
                "\nElenco: " + castString
                );

        if (rating != 0)
            string.append("\nAvaliação: ").append("★".repeat(rating));

        string.append("\nTemporadas:");

        for(Season season : seasons)
            string.append("\n\t").append(season.toString());

        return string.toString();
    }
}
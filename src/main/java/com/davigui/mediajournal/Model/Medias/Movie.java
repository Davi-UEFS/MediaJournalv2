package com.davigui.mediajournal.Model.Medias;
import com.davigui.mediajournal.Model.Enums.Genres;

import java.util.List;

/**
 * A classe Movie representa um filme como uma mídia, estendendo a classe base Media.
 * Ela contém informações específicas de filmes, como elenco, duração, direção, roteiro,
 * título original, plataformas onde assistir e a data em que foi visto.
 */
public class Movie extends Media {
    // Lista com os nomes do elenco do filme
    private List<String> cast;
    // Duração do filme (em minutos)
    private int duration;
    // Nome do diretor do filme
    private String direction;
    // Roteiro do filme
    private String script;
    // Título original do filme
    private String originalTitle;
    // Lista de plataformas onde o filme pode ser assistido
    private List<String> whereToWatch;
    // Data em que o filme foi visto (formato: MÊS de XXXX)
    private String seenDate;

    /**
     * Construtor da classe Movie.
     * Os parâmetros são os atributos imutáveis do filme.
     * Os atributos imutáveis são inicializados pelo construtor
     * da superclasse.
     *
     * @param name           O nome do filme.
     * @param year           O ano de lançamento do filme.
     * @param genre          O gênero do filme.
     * @param cast           A lista com os nomes do elenco do filme.
     * @param duration       A duração do filme.
     * @param direction      O nome do diretor do filme.
     * @param script         Roteiro do filme.
     * @param originalTitle  O título original do filme.
     * @param whereToWatch   A lista de plataformas onde o filme pode ser assistido.
     */
    public Movie(String name, int year, Genres genre, List<String> cast, int duration, String direction,
                 String script, String originalTitle, List<String> whereToWatch) {
        super(name, year, genre);
        this.cast = cast;
        this.duration = duration;
        this.direction = direction;
        this.script = script;
        this.originalTitle = originalTitle;
        this.whereToWatch = whereToWatch;
        this.seenDate = null;
    }

    /**
     * Construtor no args da classe Movie usado na desserialização.
     * Utiliza o construtor da superclasse.
     */
    public Movie(){

    }

    /**
     * Obtém a lista com os nomes do elenco do filme.
     *
     * @return A lista com os nomes do elenco.
     */
    public List<String> getCast() {
        return cast;
    }

    /**
     * Obtém o nome do diretor do filme.
     *
     * @return O nome do diretor.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Define a data em que o filme foi visto.
     *
     * @param seenDate A data em que o filme foi visto.
     */
    public void setSeenDate(String seenDate) {
        this.seenDate = seenDate;
    }

    /**
     * Obtém a data em que o filme foi visto.
     *
     * @return A data em que o filme foi visto.
     */
    public String getSeenDate() {
        return seenDate;
    }

    /**
     * Obtém a lista de plataformas onde o filme pode ser assistido.
     *
     * @return A lista de plataformas onde assistir.
     */
    public List<String> getWhereToWatch() {
        return whereToWatch;
    }

    /**
     * Obtém o título original do filme.
     *
     * @return O título original do filme.
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * Obtém o roteiro do filme.
     *
     * @return O roteiro do filme.
     */
    public String getScript() {
        return script;
    }


    /**
     * Obtém a duração do filme em minutos.
     *
     * @return A duração do filme.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Obtém o tipo de mídia, que neste caso é "Filme".
     *
     * @return Uma string representando o tipo de mídia.
     */
    @Override
    public String getMediaType(){
        return "Filme";
    }

    /**
     * Sobrecarrega o método  de Object.
     * Retorna uma representação em string do filme, incluindo título, ano, duração,
     * direção, título original, elenco, data em que foi visto (se disponível) e avaliação (se disponível).
     * Os colchetes que existem no toString() das listas de elenco e onde
     * assistir são removidos.
     *
     * @return Uma string representando o filme.
     */
    @Override
    public String toString() {
        StringBuilder watchString = new StringBuilder(whereToWatch.toString());
        watchString.deleteCharAt(0).deleteCharAt(watchString.length() - 1);

        StringBuilder castString = new StringBuilder(cast.toString());
        castString.deleteCharAt(0).deleteCharAt(castString.length() - 1);

        StringBuilder string = new StringBuilder(
                "\n" + title + " (" + year + ")" +
                "\nDuração: " + duration + " minutos" +
                "\nDireção: " + direction +
                "\nTítulo original: " + originalTitle +
                "\nOnde assistir: " + watchString
                );

        if(seenDate != null)
            string.append("\nVisto em: ").append(seenDate);

        if(rating != 0)
            string.append("\nAvaliação: ").append("★".repeat(rating));

        string.append("\n\nSinopse: ").append(script)
                .append("\nElenco: ").append(castString);

        return string.toString();
    }
}
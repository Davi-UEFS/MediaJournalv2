package com.davigui.mediajournal.Controller;

import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Repository.Library;
import com.davigui.mediajournal.Model.Medias.Media;
import com.davigui.mediajournal.Model.Result.*;

import java.util.*;

/**
 * A classe abstrata CommonService fornece uma implementação base para serviços que manipulam
 * objetos do tipo Media. Ela define métodos comuns para avaliação,
 * escrita e leitura de reviews, busca e ordenação de mídias.
 *
 * @param <T> O tipo de mídia que será manipulado, que deve estender a classe Media.
 */
public abstract class CommonService<T extends Media> {
    // Biblioteca que armazena as mídias
    protected final Library journal;

    /**
     * Construtor da classe CommonService.
     *
     * @param journal A biblioteca que contém as mídias.
     */
    public CommonService(Library journal) {
        this.journal = journal;
    }

    /**
     * Avalia uma mídia com uma nota.
     * Verifica se a nota está dentro do intervalo permitido (1 a 5),
     * e se a mídia foi marcada como vista.
     *
     * @param media  A mídia a ser avaliada.
     * @param rating A nota a ser atribuída (deve ser entre 1 e 5).
     * @return Um objeto IResult indicando sucesso ou falha da operação.
     */
    public IResult rate(T media, int rating) {
        if (rating <= 0 || rating > 5) {
            return new Failure(media.getMediaType(), "Avaliação deve ser maior que 0 e menor ou igual a 5.");
        } else if (!media.isSeen()) {
            return new Failure(media.getMediaType(), "Marque como visto antes de avaliar");
        } else {
            media.setRating(rating);
            return new Success(media.getMediaType(), "Avaliação salva com sucesso.");
        }
    }

    /**
     * Escreve uma review para uma mídia.
     * Verifica se a mídia foi marcada como vista antes de permitir a escrita da review.
     *
     * @param media  A mídia para a qual a review será escrita.
     * @param review O texto da review.
     * @return Um objeto IResult indicando sucesso ou falha da operação.
     */
    public IResult writeReview(T media, String review) {
        if (media.isSeen()) {
            media.setReview(review);
            return new Success(media.getMediaType(), "Review salva com sucesso.");
        }
        return new Failure(media.getMediaType(), "Marque como visto antes de escrever uma review");
    }

    /**
     * Lê a review de uma mídia.
     * Verifica se a review é nula e exibe uma mensagem apropriada.
     *
     * @param media A mídia cuja review será lida.
     * @return Uma string contendo a review ou uma mensagem indicando que não há review.
     */
    public String readReview(T media) {
        return "Review: " + ((media.getReview() == null) ?
                "Você ainda não escreveu uma review" : media.getReview());
    }

    /**
     * Mostra a nota de uma mídia.
     * Verifica se a nota é zero e exibe uma mensagem apropriada.
     *
     * @param media A mídia cuja nota será exibida.
     * @return Uma string contendo a nota ou uma mensagem indicando que não há nota.
     */
    public String showRating(T media) {
        return "Nota: " + ((media.getRating() == 0) ?
                "Você ainda não avaliou a obra" : "★".repeat(media.getRating()));
    }

    /**
     * Busca mídias por título.
     * Filtra as obras que contêm o título, utilizando o metodo filter da biblioteca Stream.
     *
     * @param title     O título a ser buscado.
     * @return Uma lista de mídias que correspondem ao título, ordenadas de forma crescente.
     */
    public List<T> searchByTitle(String title) {
        String titleLower = title.toLowerCase().trim();
        List<T> filteredMedia = getAll().stream().filter
                (media -> media.getTitle().toLowerCase().contains(titleLower)).toList();

        return sortAscending(filteredMedia);
    }

    /**
     * Busca mídias por ano.
     * Filtra as obras que contêm o ano, utilizando o metodo filter da biblioteca Stream.
     *
     * @param year      O ano a ser buscado.
     * @return Uma lista de mídias que correspondem ao ano, ordenadas de forma crescente.
     */
    public List<T> searchByYear(int year) {
        List<T> filteredMedia = getAll().stream().filter
                (media -> media.getYear() == year).toList();

        return sortAscending(filteredMedia);
    }

    /**
     * Busca mídias por gênero.
     * Filtra as obras que contêm o gênero, utilizando o metodo filter da biblioteca Stream.
     *
     * @param genre     O gênero a ser buscado.
     * @return Uma lista de mídias que correspondem ao gênero, ordenadas de forma crescente.
     */
    public List<T> searchByGenre(Genres genre) {
        List<T> filteredMedia = getAll().stream().filter
                (media -> media.getGenre() == genre).toList();

        return sortAscending(filteredMedia);
    }

    /**
     * Ordena uma lista de mídias de forma crescente com base na nota.
     *
     * @param mediaList A lista de mídias a ser ordenada.
     * @return A lista ordenada de forma crescente.
     */
    public List<T> sortAscending(List<T> mediaList) {
        return mediaList.stream().sorted(Comparator.comparing(Media::getRating)).toList();
    }

    /**
     * Ordena uma lista de mídias de forma decrescente com base na nota.
     *
     * @param mediaList A lista de mídias a ser ordenada.
     * @return A lista ordenada de forma decrescente.
     */
    public List<T> sortDescending(List<T> mediaList) {
        return mediaList.stream().sorted(Comparator.comparing(Media::getRating)).toList().reversed();
    }

    /**
     * Gera um map onde as chaves são os anos em ordem crescente e os valores são listas de mídias
     * correspondentes a cada ano, ordenadas por nota crescente.
     *
     * @return Um mapa contendo as mídias agrupadas por ano em ordem crescente e ordenadas por nota crescente.
     */

    public Map<Integer, List<T>> mapByAscendingYearAscendingRate() {
        Map<Integer, List<T>> mapYearMedia = new LinkedHashMap<>();
        List<Integer> years = new ArrayList<>(journal.getYearsRegistered().keySet());

        for (Integer year : years) {
            List<T> filteredMedia;
            filteredMedia = searchByYear(year);

            if (!filteredMedia.isEmpty())
                mapYearMedia.put(year, filteredMedia);
        }
        return mapYearMedia;
    }

    /**
     * Gera um mapa onde as chaves são os anos em ordem decrescente e os valores são listas de mídias
     * correspondentes a cada ano, ordenadas por nota crescente.
     *
     * @return Um mapa contendo as mídias agrupadas por ano em ordem decrescente e ordenadas por nota crescente.
     */
    public Map<Integer, List<T>> mapByDescendingYearAscendingRate(){
        Map<Integer, List<T>> mapYearMedia = new LinkedHashMap<>();
        List<Integer> years = new ArrayList<>(journal.getYearsRegistered().keySet());

        Collections.reverse(years);

        for (Integer year : years) {
            List<T> filteredMedia = searchByYear(year);
            if (!filteredMedia.isEmpty())
                mapYearMedia.put(year, new ArrayList<>(filteredMedia));
        }
        return mapYearMedia;
    }

    /**
     * Gera um mapa onde as chaves são os anos em ordem crescente e os valores são listas de mídias
     * correspondentes a cada ano, ordenadas por nota decrescente.
     *
     * @return Um mapa contendo as mídias agrupadas por ano em ordem crescente e ordenadas por nota decrescente.
     */
    public Map<Integer, List<T>> mapByAscendingYearDescendingRate(){
        Map<Integer, List<T>> mapYearMedia = new LinkedHashMap<>();
        List<Integer> years = new ArrayList<>(journal.getYearsRegistered().keySet());

        for (Integer year : years) {
            List<T> filteredMedia = sortDescending(searchByYear(year));
            if (!filteredMedia.isEmpty())
                mapYearMedia.put(year, new ArrayList<>(filteredMedia));
        }
        return mapYearMedia;
    }

    /**
     * Gera um mapa onde as chaves são os anos em ordem decrescente e os valores são listas de mídias
     * correspondentes a cada ano, ordenadas por nota decrescente.
     *
     * @return Um mapa contendo as mídias agrupadas por ano em ordem decrescente e ordenadas por nota decrescente.
     */
    public Map<Integer, List<T>> mapByDescendingYearDescendingRate(){
        Map<Integer, List<T>> mapYearMedia = new LinkedHashMap<>();
        List<Integer> years = new ArrayList<>(journal.getYearsRegistered().keySet());

        Collections.reverse(years);

        for (Integer year : years) {
            List<T> filteredMedia = sortDescending(searchByYear(year));
            if (!filteredMedia.isEmpty())
                mapYearMedia.put(year, new ArrayList<>(filteredMedia));
        }
        return mapYearMedia;
    }

    /**
     * Gera um mapa onde as chaves são os gêneros e os valores são listas de mídias
     * correspondentes a cada gênero, ordenadas por nota crescente.
     *
     * @return Um mapa contendo as mídias agrupadas por gênero.
     */
    public Map<Genres, List<T>> mapByGenreAscendingRate() {
        Map<Genres, List<T>> mapGenreMedia = new EnumMap<>(Genres.class);

        for (Genres genre : Genres.values()) {
            List<T> filteredMedia;
            filteredMedia = searchByGenre(genre);

            if (!filteredMedia.isEmpty())
                mapGenreMedia.put(genre, filteredMedia);
        }
        return mapGenreMedia;
    }

    /**
     * Gera um mapa onde as chaves são os gêneros e os valores são listas de mídias
     * correspondentes a cada gênero, ordenadas por nota decrescente.
     *
     * @return Um mapa contendo as mídias agrupadas por gênero.
     */
    public Map<Genres, List<T>> mapByGenreDescendingRate() {
        Map<Genres, List<T>> mapGenreMedia = new EnumMap<>(Genres.class);

        for (Genres genre : Genres.values()) {
            List<T> filteredMedia;
            filteredMedia = sortDescending(searchByGenre(genre));

            if (!filteredMedia.isEmpty())
                mapGenreMedia.put(genre, filteredMedia);
        }
        return mapGenreMedia;
    }


    /**
     * Obtém a lista de mídias do controlador correspondente por sobrecarga.
     *
     * @return A lista de livros/filmes/séries cadastrados na biblioteca.
     */
    public abstract List<T> getAll();

}
package com.davigui.Controller;

import com.davigui.Model.Enums.Genres;
import com.davigui.Model.Medias.Media;
import com.davigui.Model.Result.IResult;

import java.util.List;
import java.util.Map;

/**
 * Interface que define os serviços genéricos para gerenciamento de mídias.
 *
 * @param <T> O tipo de mídia que será gerenciado, que deve estender a classe Media.
 */
public interface IMediaService <T extends Media> {

    /**
     * Avalia uma mídia com uma nota.
     *
     * @param media A mídia a ser avaliada.
     * @param rating A nota atribuída à mídia.
     * @return Um resultado indicando sucesso ou falha na operação.
     */
    IResult rate(T media, int rating);

    /**
     * Escreve uma review para uma mídia.
     *
     * @param media A mídia para a qual a review será escrita.
     * @param review A review a ser escrita.
     * @return Um resultado indicando sucesso ou falha na operação.
     */
    IResult writeReview(T media, String review);

    /**
     * Exibe a nota de uma mídia.
     *
     * @param media A mídia cuja nota será exibida.
     * @return A nota da mídia como uma string.
     */
    String showRating(T media);

    /**
     * Lê a review de uma mídia.
     *
     * @param media A mídia cuja review será lida.
     * @return A review da mídia como uma string.
     */
    String readReview(T media);

    /**
     * Busca mídias pelo título.
     *
     * @param title O título a ser buscado.
     * @param mediaList A lista de mídias onde a busca será realizada.
     * @return Uma lista de mídias que correspondem ao título.
     */
    List<T> searchByTitle(String title, List<T> mediaList);

    /**
     * Busca mídias pelo ano.
     *
     * @param year O ano a ser buscado.
     * @param mediaList A lista de mídias onde a busca será realizada.
     * @return Uma lista de mídias que correspondem ao ano.
     */
    List<T> searchByYear(int year, List<T> mediaList);

    /**
     * Busca mídias pelo gênero.
     *
     * @param genre O gênero a ser buscado.
     * @param mediaList A lista de mídias onde a busca será realizada.
     * @return Uma lista de mídias que correspondem ao gênero.
     */
    List<T> searchByGenre(Genres genre, List<T> mediaList);

    /**
     * Ordena uma lista de mídias em ordem crescente.
     *
     * @param mediaList A lista de mídias a ser ordenada.
     * @return A lista de mídias ordenada em ordem crescente.
     */
    List<T> sortAscending(List<T> mediaList);

    /**
     * Ordena uma lista de mídias em ordem decrescente.
     *
     * @param mediaList A lista de mídias a ser ordenada.
     * @return A lista de mídias ordenada em ordem decrescente.
     */
    List<T> sortDescending(List<T> mediaList);

    /**
     * Mapeia as mídias por ano e nota.
     *
     * @param mediaList A lista de mídias a ser mapeada.
     * @param ascendingYear Indica se o mapeamento deve ser ordenado por ano de forma crescente.
     * @param ascendingRate Indica se o mapeamento deve ser ordenado por nota de forma crescente.
     * @return Um mapa onde a chave é o ano e o valor é uma lista de mídias.
     */
    Map<Integer, List<T>> mapByYearRate(List<T> mediaList, boolean ascendingYear, boolean ascendingRate);

    /**
     * Mapeia as mídias por gênero e nota.
     *
     * @param mediaList A lista de mídias a ser mapeada.
     * @param ascendingRate Indica se o mapeamento deve ser ordenado por nota de forma crescente.
     * @return Um mapa onde a chave é o gênero e o valor é uma lista de mídias.
     */
    Map<Genres, List<T>> mapByGenreRate(List<T> mediaList, boolean ascendingRate);
}
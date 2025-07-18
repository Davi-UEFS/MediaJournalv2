package com.davigui.mediajournal.Controller;

import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Exceptions.MediaAlreadyExistsException;
import com.davigui.mediajournal.Model.Exceptions.SeasonNotFoundException;
import com.davigui.mediajournal.Model.Exceptions.UnsupportedOperationException;
import com.davigui.mediajournal.Model.Repository.Library;
import com.davigui.mediajournal.Model.Medias.Season;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.Model.Result.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A classe SeriesService trata do gerenciamento de séries.
 * Ela herda da classe CommonService e utiliza a biblioteca para registrar, buscar e manipular séries.
 */
public class SeriesService extends CommonService<Series> {

    /**
     * Construtor da classe SeriesService.
     *
     * @param journal A biblioteca que será utilizada para gerenciar as séries.
     */
    public SeriesService(Library journal){
        super(journal);
    }

    /**
     * Cria um objeto série e salva na biblioteca, verificando se ele já existe.
     * Prepara as arraylists de elenco e plataformas.
     * Adiciona o ano da série à lista de anos registrados.
     * Adiciona a primeira temporada à série.
     *
     * @param title O título da série.
     * @param year O ano de início da série.
     * @param genre O gênero da série.
     * @param yearOfEnding O ano de término da série.
     * @param castBuffer O elenco da série.
     * @param originalTitle O título original da série.
     * @param whereToWatchBuffer As plataformas onde a série pode ser assistida.
     * @param seasonNumber O número da temporada inicial.
     * @param episodeCount A quantidade de episódios da temporada inicial.
     * @param seasonYear O ano da temporada inicial.
     * @return Um resultado indicando sucesso ou falha no registro.
     */
    public IResult register(String title, int year, Genres genre, int yearOfEnding,
                            String[] castBuffer, String originalTitle, String[] whereToWatchBuffer,
                            int seasonNumber, int episodeCount, int seasonYear) {

        if(seasonYear < year || seasonYear > yearOfEnding)
            return new Failure("Série", "Ano de temporada inválido");

        ArrayList<String> cast = new ArrayList<>(Arrays.asList(castBuffer));
        ArrayList<String> whereToWatch = new ArrayList<>(Arrays.asList(whereToWatchBuffer));

        Season season = new Season(seasonNumber, episodeCount, seasonYear);

        Series series = new Series(title, year, genre, yearOfEnding, cast,
                originalTitle, whereToWatch);

        try {
            journal.exists(series);
            series.addSeason(season);
            journal.add(series);
            journal.addYear(year);
            return new Success("Série","Registrada com sucesso.");
        }catch (MediaAlreadyExistsException e){
            return new Failure("Série",e.getMessage());
        }
    }

    /**
     * Registra uma nova temporada para uma série existente.
     * Verifica se o ano da temporada está dentro do intervalo permitido.
     * Verifica se a temporada já existe.
     *
     * @param series A série à qual a temporada será adicionada.
     * @param seasonNumber O número da temporada.
     * @param episodeCount A quantidade de episódios da temporada.
     * @param seasonYear O ano da temporada.
     * @return Um resultado indicando sucesso ou falha no registro.
     */
    public IResult registerSeason(Series series, int seasonNumber, int episodeCount, int seasonYear){

        if (seasonYear < series.getYear() || seasonYear > series.getYearOfEnding())
            return new Failure("Temporada", "Ano inválido");

        Season season = new Season(seasonNumber, episodeCount, seasonYear);

        try{
            series.findSeason(seasonNumber);
            return new Failure("Temporada", "Já existe");
        } catch (SeasonNotFoundException e){
            series.addSeason(season);
            return new Success("Temporada", "Registrada com sucesso.");
        }
    }



    /**
     * Lança uma exceção para indicar que a avaliação de uma série não é suportada.
     *
     * @param series A série a ser avaliada.
     * @param rating A nota atribuída à série.
     * @return Nunca retorna, pois lança uma exceção.
     * @throws UnsupportedOperationException Sempre que o metodo é chamado.
     */
    @Override
    public IResult rate(Series series, int rating) {
        throw new UnsupportedOperationException("Método não suportado para séries.");
    }

    /**
     * Lança uma exceção para indicar que a escrita de uma review para uma série não é suportada.
     *
     * @param series A série para a qual a review seria escrita.
     * @param review A review a ser escrita.
     * @return Nunca retorna, pois lança uma exceção.
     * @throws UnsupportedOperationException Sempre que o metodo é chamado.
     */
    @Override
    public IResult writeReview(Series series, String review) {
        throw new UnsupportedOperationException("Método não suportado para séries.");
    }

    /**
     * Lança uma exceção para indicar que a leitura de uma review de uma série não é suportada.
     *
     * @param series A série cuja review seria lida.
     * @return Nunca retorna, pois lança uma exceção.
     * @throws UnsupportedOperationException Sempre que o metodo é chamado.
     */
    @Override
    public String readReview(Series series) {
        throw new UnsupportedOperationException("Método não suportado para séries.");
    }

    /**
     * Marca uma temporada como vista.
     * Verifica se a temporada existe e se já foi vista.
     *
     * @param series A série que contém a temporada.
     * @param seasonNumber O número da temporada a ser marcada como vista.
     * @return Um resultado indicando sucesso ou falha na operação.
     */
    public IResult markAsSeenSeason(Series series, int seasonNumber) {
        try{
            Season season = series.findSeason(seasonNumber);
            if(season.isSeen()) {
                return new Failure("Temporada", "Já marcado como visto");
            }
            season.setSeen();
            return new Success("Temporada", "Marcado como visto");

        }catch (SeasonNotFoundException e){
            return new Failure("Temporada",e.getMessage());
        }
    }

    /**
     * Avalia uma temporada específica de uma série.
     * Verifica se a temporada existe e já foi vista antes de permitir a avaliação.
     * Atualiza a avaliação geral da série através do metodo updateRate.
     *
     * @param series A série que contém a temporada a ser avaliada.
     * @param seasonNumber O número da temporada a ser avaliada.
     * @param rating A nota atribuída à temporada.
     * @return Um objeto IResult indicando o sucesso ou falha da operação:
     */
    public IResult rateSeason(Series series, int seasonNumber, int rating) {
        try {

            Season season = series.findSeason(seasonNumber);
            if(season.isSeen()) {
                season.setRating(rating);
                series.updateRate();
                return new Success("Temporada", "Avaliação salva com sucesso");
            }
            return new Failure("Temporada", "Marque como visto antes de avaliar");

        }catch (SeasonNotFoundException e){
            return new Failure("Temporada",e.getMessage());
        }
    }

    /**
     * Escreve uma review para uma temporada de uma série.
     * Verifica se a temporada existe e já foi vista antes de permitir a escrita da review.
     *
     * @param series A série que contém a temporada.
     * @param seasonNumber O número da temporada.
     * @param review A review a ser escrita.
     * @return Um resultado indicando sucesso ou falha na operação.
     */
    public IResult writeReviewSeason(Series series, int seasonNumber, String review) {
        try {
            Season season = series.findSeason(seasonNumber);
            if(season.isSeen()) {
                season.setReview(review);
                return new Success("Temporada", "Review salva com sucesso");
            }
            return new Failure("Temporada", "Marque como visto antes de escrever uma review");
        } catch (SeasonNotFoundException e) {
            return new Failure("Temporada",e.getMessage());
        }
    }

    /**
     * Lê a review de uma temporada de uma série.
     * Verifica se a temporada existe e se uma review foi escrita.
     *
     * @param series A série que contém a temporada.
     * @param seasonNumber O número da temporada.
     * @return A review da temporada ou uma mensagem indicando que não há review.
     */
    public String readReviewSeason(Series series, int seasonNumber) {

        try {

            Season season = series.findSeason(seasonNumber);
            return "Review: " + ((season.getReview() == null) ?
                    "Você ainda não escreveu uma review desta temporada" : season.getReview());
        } catch (SeasonNotFoundException e) {
            return e.getMessage();

        }
    }

    /**
     * Exibe a nota geral de uma série.
     *
     * @param series A série cuja nota será exibida.
     * @return A nota geral da série ou uma mensagem indicando que não há avaliações.
     */
    @Override
    public String showRating(Series series) {

        return "Nota geral: " + ((series.getRating() == 0) ?
                "Sem temporadas avaliadas" : series.getRating());

    }

    /**
     * Exibe a nota de uma temporada específica de uma série.
     * Verifica se a temporada existe e se uma avaliação foi feita.
     *
     * @param series A série que contém a temporada.
     * @param seasonNumber O número da temporada.
     * @return A nota da temporada ou uma mensagem indicando que não há avaliação.
     */
    public String showRatingSeason(Series series, int seasonNumber) {

        try {
            Season season = series.findSeason(seasonNumber);
            return "Nota: " + ((season.getRating() == 0) ?
                    "Você ainda não avaliou esta temporada" : season.getRating());
        } catch (SeasonNotFoundException e) {
            return e.getMessage();

        }
    }

    /**
     * Busca séries pelo nome de um ator.
     * Filtra as séries que contêm o nome do ator, utilizando o metodo filter da biblioteca Stream.
     *
     * @param name O nome do ator a ser buscado.
     * @return Uma lista de séries que possuem o ator no elenco.
     */
    public List<Series> searchByActor(String name){
        String actorLower = name.toLowerCase().trim();
        List<Series> filteredSeries = journal.getSeriesList().stream().filter
                (series -> series.getCast().stream().anyMatch(
                        actor-> actor.toLowerCase().contains(actorLower))).toList();

        return sortAscending(filteredSeries);
    }

    /**
     * Obtém todas as séries registradas na biblioteca.
     *
     * @return Uma lista contendo todas as séries.
     */
    @Override
    public ArrayList<Series> getAll(){
        return journal.getSeriesList();
    }

    /**
     * Remove uma série da biblioteca.
     * Verifica se a série existe na biblioteca antes de removê-la.
     * Remove o ano da série da lista de anos registrados.
     *
     * @param series A série a ser removida.
     * @return Um resultado indicando sucesso ou falha na remoção.
     */
    public IResult deleteSeries(Series series) {
        if(journal.getSeriesList().contains(series)) {
            journal.remove(series);
            journal.removeYear(series.getYear());
            return new Success("Série", "Removida com sucesso!");
        }else{
            return new Failure("Série", "Essa série não existe na biblioteca!");
        }
    }
}
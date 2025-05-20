package com.davigui.mediajournal.Controller;

import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Enums.Months;
import com.davigui.mediajournal.Model.Exceptions.MediaAlreadyExistsException;
import com.davigui.mediajournal.Model.Repository.Library;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.Model.Result.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A classe MovieService trata do gerenciamento de filmes.
 * Ela herda da classe CommonService e utiliza a biblioteca para registrar, buscar e manipular filmes.
 */
public class MovieService extends CommonService<Movie> {

    /**
     * Construtor da classe MovieService.
     *
     * @param journal A biblioteca que será utilizada para gerenciar os filmes.
     */
    public MovieService(Library journal){
        super(journal);
    }

    /**
     * Cria um objeto filme e salva na biblioteca, verificando se ele já existe.
     * Prepara as arraylists de elenco e plataformas.
     * Adiciona o ano do filme à lista de anos registrados.
     *
     * @param name O nome do filme.
     * @param year O ano de lançamento do filme.
     * @param genre O gênero do filme.
     * @param castBuffer O elenco do filme.
     * @param duration A duração do filme.
     * @param direction O diretor do filme.
     * @param script O roteiro do filme.
     * @param originalTitle O título original do filme.
     * @param whereToWatchBuffer As plataformas onde o filme pode ser assistido.
     * @return Um resultado indicando sucesso ou falha no registro.
     */
    public IResult register(String name, int year, Genres genre, String[] castBuffer,
                            int duration, String direction, String script,
                            String originalTitle, String[] whereToWatchBuffer){

        ArrayList<String> cast = new ArrayList<>(Arrays.asList(castBuffer)); // Adiciona todos os nomes ao elenco
        ArrayList<String> whereToWatch = new ArrayList<>(Arrays.asList(whereToWatchBuffer));

        Movie movie = new Movie(name, year, genre, cast, duration, direction,
                script, originalTitle, whereToWatch);

        try {
            journal.exists(movie);
            journal.add(movie);
            journal.addYear(year);
            return new Success("Filme","Registrado com sucesso!");

        }catch (MediaAlreadyExistsException e){
            return new Failure("Filme",e.getMessage());
        }
    }

    /**
     * Busca filmes pelo nome do diretor.
     * Filtra os filmes que contêm o nome do diretor, utilizando o metodo filter da biblioteca Stream.
     *
     * @param director O nome do diretor a ser buscado.
     * @return Uma lista de filmes que possuem o diretor especificado.
     */
    public List<Movie> searchByDirector(String director){
        String directorLower = director.toLowerCase().trim();
        List<Movie> filteredMovies = journal.getMovieList().stream().filter
                (movie -> movie.getDirection().toLowerCase().contains(directorLower)).toList();

        return sortAscending(filteredMovies);
    }

    /**
     * Busca filmes pelo nome de um ator.
     * Filtra os filmes que contêm o nome do ator, utilizando o metodo filter da biblioteca Stream.
     *
     * @param name O nome do ator a ser buscado.
     * @return Uma lista de filmes que possuem o ator no elenco.
     */
    public List<Movie> searchByActor(String name){
        String actorLower = name.toLowerCase().trim();
        List<Movie> filteredMovies = journal.getMovieList().stream().filter
                (movie -> movie.getCast().stream().anyMatch(
                        actor-> actor.toLowerCase().contains(actorLower))).toList();

        return sortAscending(filteredMovies);
    }

    /**
     * Marca um filme como visto e registra a data de visualização.
     * Verifica se o livro já foi lido e se o ano é válido.
     * Cria uma string com o mês e ano da leitura por extenso.
     *
     * @param movie O filme a ser marcado como visto.
     * @param year O ano em que o filme foi visto.
     * @param month O mês em que o filme foi visto.
     * @return Um resultado indicando sucesso ou falha na operação.
     */
    public IResult markAsSeen(Movie movie, int year, Months month){

        if(movie.isSeen())
            return new Failure("Filme", "Já marcado como visto");

        if(year < movie.getYear() || year > LocalDate.now().getYear())
            return new Failure("Filme", "Ano inválido!");

        String date = month.toString() + " de " + year;
        movie.setSeen();
        movie.setSeenDate(date);
        return new Success("Filme", "Marcado como visto e data registrada.");
    }

    /**
     * Obtém todos os filmes registrados na biblioteca.
     *
     * @return Uma lista contendo todos os filmes.
     */
    public ArrayList<Movie> getAllMovies(){
        return journal.getMovieList();
    }

    /**
     * Remove um filme da biblioteca.
     * Verifica se o filme existe na biblioteca antes de removê-lo.
     * Remove o ano do filme da lista de anos registrados.
     *
     * @param movie O filme a ser removido.
     * @return Um resultado indicando sucesso ou falha na remoção.
     */
    public IResult deleteMovie(Movie movie){
        if(journal.getMovieList().contains(movie)){
            journal.remove(movie);
            journal.removeYear(movie.getYear());
            return new Success("Filme", "Removido com sucesso!");
        }else{
            return new Failure("Filme", "Esse filme não existe na biblioteca!");
        }
    }
}
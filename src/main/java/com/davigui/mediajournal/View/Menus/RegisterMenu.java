package com.davigui.mediajournal.View.Menus;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.View.Prompts.*;

import java.util.Scanner;

/**
 * A classe RegisterMenu é responsável por gerenciar o menu de registro de mídias.
 * Permite ao usuário registrar livros, filmes, séries e temporadas de séries.
 * As entradas são validadas pelas classes do pacote Prompts.
 */
public class RegisterMenu {
    private final Scanner scanner; // Scanner para leitura de entradas do usuário.
    private final BookService bookService; // Serviço para gerenciamento de livros.
    private final MovieService movieService; // Serviço para gerenciamento de filmes.
    private final SeriesService seriesService; // Serviço para gerenciamento de séries.

    /**
     * Construtor da classe RegisterMenu.
     * Possui agregação com as classes de serviço.
     *
     * @param bookService Serviço para gerenciamento de livros.
     * @param movieService Serviço para gerenciamento de filmes.
     * @param seriesService Serviço para gerenciamento de séries.
     * @param scanner Objeto Scanner para leitura de entradas do usuário.
     */
    public RegisterMenu(BookService bookService, MovieService movieService,
                        SeriesService seriesService, Scanner scanner) {
        this.bookService = bookService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de registro e gerencia as interações do usuário.
     * O menu permite registrar livros, filmes, séries e temporadas de séries.
     * Cada opção leva a um método auxiliar.
     * O loop continua até que o usuário escolha a opção de voltar.
     */
    public void show() {
        int option;
        do {
            System.out.println(Colors.purple + "--== MENU DE REGISTRO ==--" + Colors.rst);
            System.out.println("1 - Registrar livro");
            System.out.println("2 - Registrar filme");
            System.out.println("3 - Registrar série");
            System.out.println("4 - Registrar temporada");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);
            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    handleRegisterBook();
                    break;
                case 2:
                    handleRegisterMovie();
                    break;
                case 3:
                    handleRegisterSeries();
                    break;
                case 4:
                    handleRegisterSeason();
                    break;
                case 0:
                    System.out.println("Retornando...");
                    break;
                default:
                    System.out.println(Colors.red + "Opção inválida" + Colors.rst);
                    break;
            }
        } while (option != 0);
    }

    /**
     * Registra um novo livro no sistema.
     * Solicita ao usuário informações como título, ano, gênero, ISBN, autor, editora e posse
     * através dos métodos estáticos de AskInput e, então, exibe o
     * resultado da operação de registro.
     */
    private void handleRegisterBook() {
        String title = AskInput.askForTitle(scanner);
        int year = AskInput.askForYear(scanner);
        Genres genre = AskInput.askForGenre(scanner);
        String isbn = AskInput.askForISBN(scanner);
        String author = AskInput.askForAuthor(scanner);
        String publisher = AskInput.askForPublisher(scanner);
        boolean owned = AskInput.askForOwned(scanner);

        IResult result = bookService.register(
                title, year, genre, isbn, author, publisher, owned
        );
        System.out.println(result.getMessage());
    }

    /**
     * Registra um novo filme no sistema.
     * Solicita ao usuário informações como título, ano, gênero, elenco,
     * duração, diretor, roteiro, título original e onde assistir
     * através dos métodos estáticos de AskInput e, então, exibe o
     * resultado da operação de registro.
     */
    private void handleRegisterMovie() {
        String title = AskInput.askForTitle(scanner);
        int year = AskInput.askForYear(scanner);
        Genres genre = AskInput.askForGenre(scanner);
        String[] castBuffer = AskInput.askForCast(scanner);
        int duration = AskInput.askForDuration(scanner);
        String director = AskInput.askForDirector(scanner);
        String script = AskInput.askForScript(scanner);
        String originalTitle = AskInput.askForOriginalTitle(scanner);
        String[] whereToWatch = AskInput.askForWhereToWatch(scanner);

        IResult result = movieService.register(
                title, year, genre, castBuffer, duration, director,
                script, originalTitle, whereToWatch
        );
        System.out.println(result.getMessage());
    }

    /**
     * Registra uma nova série no sistema.
     * Solicita ao usuário informações como título, ano, gênero, ano de
     * término, elenco, título original, onde assistir, número de
     * temporadas, número de episódios e ano da temporada através dos métodos
     * estáticos de AskInput e, então, exibe o
     * resultado da operação de registro.
     */
    private void handleRegisterSeries() {
        String title = AskInput.askForTitle(scanner);
        int year = AskInput.askForYear(scanner);
        Genres genre = AskInput.askForGenre(scanner);
        int yearOfEnding = AskInput.askForYearOfEnding(scanner);
        String[] castBuffer = AskInput.askForCast(scanner);
        String originalTitle = AskInput.askForOriginalTitle(scanner);
        String[] whereToWatch = AskInput.askForWhereToWatch(scanner);
        int seasonNumber = AskInput.askForSeasonNumber(scanner);
        int episodeCount = AskInput.askForEpisodeCount(scanner);
        int seasonYear = AskInput.askForSeasonYear(scanner);

        IResult result = seriesService.register(
                title, year, genre, yearOfEnding, castBuffer,
                originalTitle, whereToWatch, seasonNumber, episodeCount, seasonYear
        );
        System.out.println(result.getMessage());
    }

    /**
     * Registra uma nova temporada para uma série existente no sistema.
     * A série é escolhida usando o método selectFromList() de AskInput.
     * Solicita ao usuário informações como número da temporada, número
     * de episódios e ano da temporada através dos métodos
     * estáticos de AskInput e, então, exibe o
     * resultado da operação de registro.
     * Caso não existam séries cadastradas, exibe uma mensagem de erro.
     */
    private void handleRegisterSeason() {
        if (seriesService.getAllSeries().isEmpty()) {
            System.out.println("Você não possui séries cadastradas");
            return;
        }

        Series selectedSeries = AskInput.selectFromList(scanner, seriesService.getAllSeries());
        int seasonNumber = AskInput.askForSeasonNumber(scanner);
        int episodeCount = AskInput.askForEpisodeCount(scanner);
        int seasonYear = AskInput.askForSeasonYear(scanner);

        IResult result = seriesService.registerSeason(selectedSeries, seasonNumber, episodeCount, seasonYear);
        System.out.println(result.getMessage());
    }
}
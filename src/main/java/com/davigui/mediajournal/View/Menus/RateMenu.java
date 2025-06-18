package com.davigui.mediajournal.View.Menus;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Enums.Months;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.View.Prompts.*;

import java.util.Scanner;

/**
 * A classe RateMenu é responsável por gerenciar o menu de avaliação de mídias.
 * Permite ao usuário avaliar livros, filmes e temporadas de séries, escrever reviews
 * e marcar mídias como vistas.
 * As entradas são validadas pelas classes do pacote Prompts.
 */
public class RateMenu {
    private final Scanner scanner; // Scanner para leitura de entradas do usuário.
    private final BookService bookService; // Serviço para gerenciamento de livros.
    private final MovieService movieService; // Serviço para gerenciamento de filmes.
    private final SeriesService seriesService; // Serviço para gerenciamento de séries.

    /**
     * Construtor da classe RateMenu.
     * Possui agregação com as classes de serviço.
     *
     * @param bookService Serviço para gerenciamento de livros.
     * @param movieService Serviço para gerenciamento de filmes.
     * @param seriesService Serviço para gerenciamento de séries.
     * @param scanner Objeto Scanner para leitura de entradas do usuário.
     */
    public RateMenu(BookService bookService, MovieService movieService,
                    SeriesService seriesService, Scanner scanner) {
        this.bookService = bookService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de avaliação e gerencia as interações do usuário.
     * O menu permite avaliar mídias, escrever reviews e marcar mídias como vistas.
     * Cada opção leva a um método auxiliar.
     * O loop continua até que o usuário escolha a opção de voltar.
     */
    public void show() {
        int option;
        do {
            System.out.println(Colors.blue + "--== MENU DE AVALIAÇÃO ==--" + Colors.rst);
            System.out.println("1 - Avaliar livro");
            System.out.println("2 - Avaliar filme");
            System.out.println("3 - Avaliar temporada");
            System.out.println("4 - Escrever review (livro)");
            System.out.println("5 - Escrever review (filme)");
            System.out.println("6 - Escrever review (temporada)");
            System.out.println("7 - Marcar como visto (livro)");
            System.out.println("8 - Marcar como visto (filme)");
            System.out.println("9 - Marcar como visto (temporada)");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    handleRateBook();
                    break;
                case 2:
                    handleRateMovie();
                    break;
                case 3:
                    handleRateSeason();
                    break;
                case 4:
                    handleWriteBookReview();
                    break;
                case 5:
                    handleWriteMovieReview();
                    break;
                case 6:
                    handleWriteSeasonReview();
                    break;
                case 7:
                    handleMarkBookAsSeen();
                    break;
                case 8:
                    handleMarkMovieAsSeen();
                    break;
                case 9:
                    handleMarkSeasonAsSeen();
                    break;
                case 0:
                    System.out.println("Retornando...");
                    break;
                default:
                    System.out.println(Colors.red + "Opção inválida." + Colors.rst);
            }
        } while (option != 0);
    }

    /**
     * Gerencia a avaliação de um livro.
     * A seleção do livro é feita pelo método selectFromList() de AskInput.
     * A entrada da nota é feita pelo método askForRate() de AskInput.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de livros estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleRateBook() {
        if (bookService.getAll().isEmpty()) {
            System.out.println("Você não possui livros cadastrados.");
            return;
        }
        Book selectedBook = AskInput.selectFromList(scanner, bookService.getAll());
        int rating = AskInput.askForRate(scanner);
        IResult result = bookService.rate(selectedBook, rating);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a avaliação de um filme.
     * A seleção do filme é feita pelo método selectFromList() de AskInput.
     * A entrada da nota é feita pelo método askForRate() de AskInput.
     * Solicita ao usuário selecionar um filme e atribuir uma nota.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de filmes estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleRateMovie() {
        if (movieService.getAll().isEmpty()) {
            System.out.println("Você não possui filmes cadastrados.");
            return;
        }
        Movie selectedMovie = AskInput.selectFromList(scanner, movieService.getAll());
        int rating = AskInput.askForRate(scanner);
        IResult result = movieService.rate(selectedMovie, rating);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a avaliação de uma temporada de série.
     * A seleção da série é feita pelo método selectFromList() de AskInput.
     * A entrada da nota e do número da temporada é feita pelos
     * métodos askForRate() e askForSeasonNumber() de AskInput.
     * Solicita ao usuário selecionar uma série, uma temporada e atribuir uma nota.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de séries estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleRateSeason() {
        if (seriesService.getAll().isEmpty()) {
            System.out.println("Você não possui séries cadastradas.");
            return;
        }
        Series selectedSeries = AskInput.selectFromList(scanner, seriesService.getAll());
        int rating = AskInput.askForRate(scanner);
        int seasonNumber = AskInput.askForSeasonNumber(scanner);
        IResult result = seriesService.rateSeason(selectedSeries, seasonNumber, rating);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a escrita de uma review para um livro.
     * A seleção do livro é feita pelo método selectFromList() de AskInput.
     * A entrada do texto da review é feita pelo método askForReview() de AskInput.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de livros estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleWriteBookReview() {
        if (bookService.getAll().isEmpty()) {
            System.out.println("Você não possui livros cadastrados.");
            return;
        }
        Book selectedBook = AskInput.selectFromList(scanner, bookService.getAll());
        String review = AskInput.askForReview(scanner);
        IResult result = bookService.writeReview(selectedBook, review);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a escrita de uma review para um filme.
     * A seleção do filme é feita pelo método selectFromList() de AskInput.
     * A entrada do texto da review é feita pelo método askForReview() de AskInput.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de filmes estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleWriteMovieReview() {
        if (movieService.getAll().isEmpty()) {
            System.out.println("Você não possui filmes cadastrados.");
            return;
        }
        Movie selectedMovie = AskInput.selectFromList(scanner, movieService.getAll());
        String review = AskInput.askForReview(scanner);
        IResult result = movieService.writeReview(selectedMovie, review);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a escrita de uma review para uma temporada de série.
     * A seleção da série é feita pelo método selectFromList() de AskInput.
     * A entrada do número da temporada e do texto da review é feita pelos
     * métodos askForSeasonNumber() e askForReview() de AskInput.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de séries estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleWriteSeasonReview() {
        if (seriesService.getAll().isEmpty()) {
            System.out.println("Você não possui séries cadastradas.");
            return;
        }
        Series selectedSeries = AskInput.selectFromList(scanner, seriesService.getAll());
        int seasonNumber = AskInput.askForSeasonNumber(scanner);
        String review = AskInput.askForReview(scanner);
        IResult result = seriesService.writeReviewSeason(selectedSeries, seasonNumber, review);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a marcação de um livro como visto.
     * A seleção do livro é feita pelo método selectFromList() de AskInput.
     * A entrada do ano e mês é feita pelos métodos
     * askForSeenYear() e askForSeenMonth() de AskInput.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de livros estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleMarkBookAsSeen() {
        if (bookService.getAll().isEmpty()) {
            System.out.println("Você não possui livros cadastrados.");
            return;
        }
        Book selectedBook = AskInput.selectFromList(scanner, bookService.getAll());
        int year = AskInput.askForSeenYear(scanner);
        Months month = AskInput.askForSeenMonth(scanner);
        IResult result = bookService.markAsSeen(selectedBook, year, month);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a marcação de um filme como visto.
     * A seleção do filme é feita pelo método selectFromList() de AskInput.
     * A entrada do ano e mês é feita pelos métodos
     * askForSeenYear() e askForSeenMonth() de AskInput.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de filmes estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleMarkMovieAsSeen() {
        if (movieService.getAll().isEmpty()) {
            System.out.println("Você não possui filmes cadastrados.");
            return;
        }
        Movie selectedMovie = AskInput.selectFromList(scanner, movieService.getAll());
        int year = AskInput.askForSeenYear(scanner);
        Months month = AskInput.askForSeenMonth(scanner);
        IResult result = movieService.markAsSeen(selectedMovie, year, month);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a marcação de uma temporada de série como vista.
     * A seleção da série é feita pelo método selectFromList() de AskInput.
     * A entrada do número da temporada é feita pelo métodos
     * askForSeasonNumber() de AskInput.
     * Exibe uma mensagem com o resultado da operação.
     * Se a lista de séries estiver vazia, apenas exibe uma mensagem de erro.
     */
    private void handleMarkSeasonAsSeen() {
        if (seriesService.getAll().isEmpty()) {
            System.out.println("Você não possui séries cadastradas.");
            return;
        }
        Series selectedSeries = AskInput.selectFromList(scanner, seriesService.getAll());
        int seasonNumber = AskInput.askForSeasonNumber(scanner);
        IResult result = seriesService.markAsSeenSeason(selectedSeries, seasonNumber);
        System.out.println(result.getMessage());
    }
}
package com.davigui.mediajournal.View.Menus;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.CommonService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Media;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.View.Prompts.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A classe DisplayMenu é responsável por gerenciar o menu de exibição de mídias.
 * Permite ao usuário visualizar avaliações, reviews e listas de mídias cadastradas,
 * com diferentes critérios de ordenação.
 * As entradas são validadas pelas classes do pacote Prompts.
 */
public class DisplayMenu {
    private final Scanner scanner; // Scanner para leitura de entradas do usuário.
    private final BookService bookService; // Serviço para gerenciamento de livros.
    private final MovieService movieService; // Serviço para gerenciamento de filmes.
    private final SeriesService seriesService; // Serviço para gerenciamento de séries.

    /**
     * Construtor da classe DisplayMenu.
     * Possui agregação com as classes de serviço.
     *
     * @param bookService Serviço para gerenciamento de livros.
     * @param movieService Serviço para gerenciamento de filmes.
     * @param seriesService Serviço para gerenciamento de séries.
     * @param scanner Objeto Scanner para leitura de entradas do usuário.
     */
    public DisplayMenu(BookService bookService, MovieService movieService,
                       SeriesService seriesService, Scanner scanner) {
        this.bookService = bookService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.scanner = scanner;
    }

    /**
     * Exibe as opções menu de display e gerencia as interações do usuário.
     * Cada opção leva a um método auxiliar.
     * O menu permite visualizar avaliações, reviews e listas de mídias cadastradas.
     * O loop continua até que o usuário escolha a opção de voltar.
     */
    public void show() {
        int option;
        do {
            System.out.println(Colors.green + "--== MENU DE DISPLAY ==--" + Colors.rst);
            System.out.println("1 - Ver avaliações/reviews (livro)");
            System.out.println("2 - Ver avaliações/reviews (filme)");
            System.out.println("3 - Ver avaliações/reviews (série)");
            System.out.println("4 - Ver livros cadastrados");
            System.out.println("5 - Ver filmes cadastrados");
            System.out.println("6 - Ver séries cadastradas");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    showBookReviewsAndRatings();
                    break;

                case 2:
                    showMovieReviewsAndRatings();
                    break;

                case 3:
                    showSeriesReviewsAndRatings();
                    break;

                case 4:
                    showAllBooks();
                    break;

                case 5:
                    showAllMovies();
                    break;

                case 6:
                    showAllSeries();
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
     * Exibe as avaliações e reviews de um livro existente na lista de livros cadastrados.
     * A seleção do livro é feita pelo método estático selectFromList() de AskInput.
     * Caso não existam livros cadastrados, exibe uma mensagem de erro.
     */
    private void showBookReviewsAndRatings() {
        if (bookService.getAllBooks().isEmpty()) {
            System.out.println("Você não possui livros cadastrados.");
            return;
        }
        Book selectedBook = AskInput.selectFromList(scanner, bookService.getAllBooks());
        System.out.println(bookService.readReview(selectedBook));
        System.out.println(bookService.showRating(selectedBook));
    }

    /**
     * Exibe as avaliações e reviews de um filme existente na lista de filmes cadastrados.
     * A seleção do filme é feita pelo método estático selectFromList() de AskInput.
     * Caso não existam filmes cadastrados, exibe uma mensagem de erro.
     */
    private void showMovieReviewsAndRatings() {
        if (movieService.getAllMovies().isEmpty()) {
            System.out.println("Você não possui filmes cadastrados");
            return;
        }
        Movie selectedMovie = AskInput.selectFromList(scanner, movieService.getAllMovies());
        System.out.println(movieService.readReview(selectedMovie));
        System.out.println(movieService.showRating(selectedMovie));
    }

    /**
     * Exibe as avaliações e reviews de uma temporada de série existente na lista de séries cadastradas.
     * A seleção é da série feita pelo método estático selectFromList() de AskInput.
     * Caso não existam séries cadastradas, exibe uma mensagem de erro.
     * Caso a temporada não for encontrada, exibe uma mensagem de erro.
     */
    private void showSeriesReviewsAndRatings() {
        if (seriesService.getAllSeries().isEmpty()) {
            System.out.println("Você não possui séries cadastradas");
            return;
        }
        Series selectedSeries = AskInput.selectFromList(scanner, seriesService.getAllSeries());
        int seasonNumber = AskInput.askForSeasonNumber(scanner);
        System.out.println(seriesService.readReviewSeason(selectedSeries, seasonNumber));
        System.out.println(seriesService.showRatingSeason(selectedSeries, seasonNumber));
        System.out.println(seriesService.showRating(selectedSeries));
    }

    /**
     * Exibe todos os livros cadastrados, com opções de ordenação.
     * As opções são feitas no minimenu de listagem.
     * Caso não existam livros cadastrados, exibe uma mensagem de erro.
     */
    private void showAllBooks() {
        if (bookService.getAllBooks().isEmpty()) {
            System.out.println("Você não possui livros cadastrados.");
            return;
        }
        listByMiniMenu(bookService, bookService.getAllBooks());
    }

    /**
     * Exibe todos os filmes cadastrados, com opções de ordenação.
     * As opções são feitas no minimenu de listagem.
     * Caso não existam filmes cadastrados, exibe uma mensagem de erro.
     */
    private void showAllMovies() {
        if (movieService.getAllMovies().isEmpty()) {
            System.out.println("Você não possui filmes cadastrados");
            return;
        }
        listByMiniMenu(movieService, movieService.getAllMovies());
    }

    /**
     * Exibe todas as séries cadastradas, com opções de ordenação.
     * As opções são feitas no minimenu de listagem.
     * Caso não existam séries cadastradas, exibe uma mensagem de erro.
     */
    private void showAllSeries() {
        if (seriesService.getAllSeries().isEmpty()) {
            System.out.println("Você não possui séries cadastradas");
            return;
        }
        listByMiniMenu(seriesService, seriesService.getAllSeries());
    }

    /**
     * Exibe um menu secundário para listar mídias com diferentes critérios de ordenação.
     * A listagem consiste iterar o enum de gêneros ou o mapa de anos registrados e
     * fazer uma busca para cada gênero/ano.
     * É feita usando os métodos genéricos de CommonService. O parâmetro T
     * define qual obra estamos listando.
     * O loop continua até que o usuário escolha a opção de voltar.
     *
     * @param service Serviço responsável pelo gerenciamento das mídias.
     *                Referente à superclasse abstrata CommonService
     * @param mediaList Lista de mídias a ser exibida.
     * @param <T> Tipo de mídia que estende a classe Media.
     */
    private <T extends Media> void listByMiniMenu(CommonService<T> service, List<T> mediaList) {
        int option;
        Map<Genres, List<T>> mapGenreMedia;
        Map<Integer, List<T>> mapIntMedia;

        do {
            showListOptions();
            option = Validate.validateInt(scanner);

            switch (option) {

                case 1:
                    List<T> listAsc = service.sortAscending(mediaList);
                    for (Media media : listAsc) {
                        System.out.println(media);
                        System.out.println("---------------------");
                    }
                    break;

                case 2:
                    List<T> listDesc = service.sortDescending(mediaList);
                    for (Media media : listDesc) {
                        System.out.println(media);
                        System.out.println("---------------------");
                    }
                    break;

                case 3:
                    mapGenreMedia = service.mapByGenreAscendingRate(mediaList);
                    printMapGenreMedia(mapGenreMedia);
                    break;

                case 4:
                    mapGenreMedia = service.mapByGenreDescendingRate(mediaList);
                    printMapGenreMedia(mapGenreMedia);
                    break;

                case 5:
                    mapIntMedia = service.mapByAscendingYearAscendingRate(mediaList);
                    printMapYearMedia(mapIntMedia);
                    break;

                case 6:
                    mapIntMedia = service.mapByAscendingYearDescendingRate(mediaList);
                    printMapYearMedia(mapIntMedia);
                    break;

                case 7:
                    mapIntMedia = service.mapByDescendingYearAscendingRate(mediaList);
                    printMapYearMedia(mapIntMedia);
                    break;

                case 8:
                    mapIntMedia = service.mapByDescendingYearDescendingRate(mediaList);
                    printMapYearMedia(mapIntMedia);
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
     * Imprime um mapa de gènero - lista de mídias.
     * Itera cada par chave-valor no mapa e na lista desse valor.
     * Assim, as obras de um gênero X são impressas em sequência.
     *
     * @param mapGenreMedia Mapa contendo gêneros como chave e listas de mídias como valor.
     * @param <T> Tipo de mídia que estende a classe Media.
     */
    private <T extends Media> void printMapGenreMedia(Map<Genres, List<T>> mapGenreMedia) {

        for (Map.Entry<Genres, List<T>> thisGenreMedia : mapGenreMedia.entrySet()) {
            System.out.println(thisGenreMedia.getKey());
            for (Media media : thisGenreMedia.getValue()) {
                System.out.println(media);
                System.out.println("---------------------");
            }
        }
    }

    /**
     * Imprime um mapa de ano - lista de mídias.
     * Itera cada par chave-valor no mapa e na lista desse valor.
     * Assim, as obras de um ano XXXX são impressas em sequência.
     *
     * @param mapYearMedia Mapa contendo anos como chave e listas de mídias como valor.
     * @param <T> Tipo de mídia que estende a classe Media.
     */
    private <T extends Media> void printMapYearMedia(Map<Integer, List<T>> mapYearMedia) {
        for (Map.Entry<Integer, List<T>> thisYearMedia : mapYearMedia.entrySet()) {

            System.out.println(thisYearMedia.getKey());
            for (Media media : thisYearMedia.getValue()){
                System.out.println(media);
                System.out.println("------------------------");
            }
        }
    }

    /**
     * Exibe as opções de listagem disponíveis no menu secundário.
     */
    private void showListOptions() {
        System.out.println(Colors.green + "--== MENU DE DISPLAY ==--" + Colors.rst);
        System.out.println("1 - Ver todos (crescente) ");
        System.out.println("2 - Ver todos (decrescente)");
        System.out.println("3 - Por gênero (crescente)");
        System.out.println("4 - Por gênero (decrescente)");
        System.out.println("5 - Por mais recente (crescente)");
        System.out.println("6 - Por mais recente (decrescente)");
        System.out.println("7 - Por menos recente (crescente)");
        System.out.println("8 - Por menos recente (decrescente)");
        System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

    }

}
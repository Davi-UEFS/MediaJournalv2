package com.davigui.mediajournal.View.Menus;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Enums.Genres;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.View.Prompts.*;

import java.util.List;
import java.util.Scanner;

/**
 * A classe SearchMenu é responsável por gerenciar o menu de busca de mídias.
 * Permite ao usuário buscar livros, filmes e séries com base em diferentes critérios.
 * As entradas são validadas pelas classes do pacote Prompts.
 */
public class SearchMenu {
    private final Scanner scanner; // Scanner para leitura de entradas do usuário.
    private final BookService bookService; // Serviço para gerenciamento de livros.
    private final MovieService movieService; // Serviço para gerenciamento de filmes.
    private final SeriesService seriesService; // Serviço para gerenciamento de séries.

    /**
     * Construtor da classe SearchMenu.
     * Possui agregação com as classes de serviço.
     *
     * @param bookService Serviço para gerenciamento de livros.
     * @param movieService Serviço para gerenciamento de filmes.
     * @param seriesService Serviço para gerenciamento de séries.
     * @param scanner Objeto Scanner para leitura de entradas do usuário.
     */
    public SearchMenu(BookService bookService, MovieService movieService,
                      SeriesService seriesService, Scanner scanner) {
        this.bookService = bookService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de busca e gerencia as interações do usuário.
     * O menu permite buscar livros, filmes e séries com base em critérios específicos.
     * Se a lista de livro/filme/série estiver vazia, exibe uma mensagem de erro. Caso
     * contrário, chama o devido método auxiliar.
     * O loop continua até que o usuário escolha a opção de voltar.
     */
    public void show() {
        int option;

        do {
            System.out.println(Colors.green + "--== MENU DE DISPLAY ==--" + Colors.rst);
            System.out.println("1 - Buscar livros");
            System.out.println("2 - Buscar filmes");
            System.out.println("3 - Buscar séries");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    if (bookService.getAll().isEmpty())
                        System.out.println("Você não possui livros cadastrados.");
                    else
                        searchBookMiniMenu();
                    break;

                case 2:
                    if (movieService.getAll().isEmpty())
                        System.out.println("Você não possui filmes cadastrados");
                    else
                        searchMovieMiniMenu();
                    break;

                case 3:
                    if (seriesService.getAll().isEmpty())
                        System.out.println("Você não possui séries cadastradas");
                    else
                        searchSeriesMiniMenu();
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
     * Exibe o menu de busca de livros e gerencia as interações do usuário.
     * Permite buscar livros por título, ano, gênero, autor ou ISBN.
     * A entrada do filtro escolhido é feita pelos métodos estáticos de AskInput
     * e a lista filtrada é impressa.
     * O loop continua até que o usuário escolha a opção de voltar.
     */
    private void searchBookMiniMenu() {
        int option;
        String title;
        int year;
        Genres genre;
        String author;
        String isbn;
        List<Book> filteredBookList;

        do {
            System.out.println(Colors.green + "--== BUSCAR LIVRO ==--" + Colors.rst);
            System.out.println("1 - Buscar por título");
            System.out.println("2 - Buscar por ano");
            System.out.println("3 - Buscar por gênero");
            System.out.println("4 - Buscar por autor");
            System.out.println("5 - Buscar por ISBN");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    title = AskInput.askForTitle(scanner);
                    filteredBookList = bookService.searchByTitle(title);
                    printBookList(filteredBookList);
                    break;

                case 2:
                    year = AskInput.askForYear(scanner);
                    filteredBookList = bookService.searchByYear(year);
                    printBookList(filteredBookList);
                    break;

                case 3:
                    genre = AskInput.askForGenre(scanner);
                    filteredBookList = bookService.searchByGenre(genre);
                    printBookList(filteredBookList);
                    break;

                case 4:
                    author = AskInput.askForAuthor(scanner);
                    filteredBookList = bookService.searchBookByAuthor(author);
                    printBookList(filteredBookList);
                    break;

                case 5:
                    isbn = AskInput.askForISBN(scanner);
                    filteredBookList = bookService.searchBookByIsbn(isbn);
                    printBookList(filteredBookList);
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
     * Exibe o menu de busca de filmes e gerencia as interações do usuário.
     * Permite buscar filmes por título, ano, gênero, diretor ou ator no elenco.
     * A entrada do filtro escolhido é feita pelos métodos estáticos de AskInput
     * e a lista filtrada é impressa.
     * O loop continua até que o usuário escolha a opção de voltar.
     */
    private void searchMovieMiniMenu() {
        int option;
        String title;
        int year;
        Genres genre;
        String director;
        String actor;
        List<Movie> filteredMovieList;

        do {
            System.out.println(Colors.green + "--== BUSCAR FILME ==--" + Colors.rst);
            System.out.println("1 - Buscar por título");
            System.out.println("2 - Buscar por ano");
            System.out.println("3 - Buscar por gênero");
            System.out.println("4 - Buscar por diretor");
            System.out.println("5 - Buscar por ator no elenco");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    title = AskInput.askForTitle(scanner);
                    filteredMovieList = movieService.searchByTitle(title);
                    printMovieList(filteredMovieList);
                    break;

                case 2:
                    year = AskInput.askForYear(scanner);
                    filteredMovieList = movieService.searchByYear(year);
                    printMovieList(filteredMovieList);
                    break;

                case 3:
                    genre = AskInput.askForGenre(scanner);
                    filteredMovieList = movieService.searchByGenre(genre);
                    printMovieList(filteredMovieList);
                    break;

                case 4:
                    director = AskInput.askForDirector(scanner);
                    filteredMovieList = movieService.searchByDirector(director);
                    printMovieList(filteredMovieList);
                    break;

                case 5:
                    actor = AskInput.askForActor(scanner);
                    filteredMovieList = movieService.searchByActor(actor);
                    printMovieList(filteredMovieList);
                    break;

                case 0:
                    System.out.println("Retornando...");
                    break;

                default:
                    System.out.println(Colors.red + "Opção inválida " + Colors.rst);
                    break;
            }
        } while (option != 0);
    }

    /**
     * Exibe o menu de busca de séries e gerencia as interações do usuário.
     * Permite buscar séries por título, ano de lançamento, gênero ou ator no elenco.
     * A entrada do filtro escolhido é feita pelos métodos estáticos de AskInput
     * e a lista filtrada é impressa.
     * O loop continua até que o usuário escolha a opção de voltar.
     */
    private void searchSeriesMiniMenu() {
        int option;
        String title;
        int year;
        Genres genre;
        String actor;
        List<Series> filteredSeriesList;

        do {
            System.out.println(Colors.green + "--== BUSCAR SÉRIE ==--" + Colors.rst);
            System.out.println("1 - Buscar por título");
            System.out.println("2 - Buscar por ano de lançamento");
            System.out.println("3 - Buscar por gênero");
            System.out.println("4 - Buscar por ator no elenco");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    title = AskInput.askForTitle(scanner);
                    filteredSeriesList = seriesService.searchByTitle(title);
                    printSeriesList(filteredSeriesList);
                    break;

                case 2:
                    year = AskInput.askForYear(scanner);
                    filteredSeriesList = seriesService.searchByYear(year);
                    printSeriesList(filteredSeriesList);
                    break;

                case 3:
                    genre = AskInput.askForGenre(scanner);
                    filteredSeriesList = seriesService.searchByGenre(genre);
                    printSeriesList(filteredSeriesList);
                    break;

                case 4:
                    actor = AskInput.askForActor(scanner);
                    filteredSeriesList = seriesService.searchByActor(actor);
                    printSeriesList(filteredSeriesList);
                    break;

                case 0:
                    System.out.println("Retornando...");
                    break;

                default:
                    System.out.println(Colors.red + "Opção inválida " + Colors.rst);
                    break;
            }
        } while (option != 0);
    }

    /**
     * Imprime a lista de livros encontrados pela busca.
     * Se estiver vazia, imprime uma mensagem de nenhum livro encontrado.
     *
     * @param bookList Lista de livros a ser exibida.
     */
    private void printBookList(List<Book> bookList) {
        if (bookList.isEmpty())
            System.out.println(Colors.red + "Nenhum livro encontrado!" + Colors.rst);
        for (Book book : bookList) {
            System.out.println(book);
            System.out.println("---------------------");
        }
    }

    /**
     * Imprime a lista de filmes encontrados pela busca.
     * Se estiver vazia, imprime uma mensagem de nenhum filme encontrado.
     *
     * @param movieList Lista de filmes a ser exibida.
     */
    private void printMovieList(List<Movie> movieList) {
        if (movieList.isEmpty())
            System.out.println(Colors.red + "Nenhum filme encontrado!" + Colors.rst);
        for (Movie movie : movieList) {
            System.out.println(movie);
            System.out.println("---------------------");
        }
    }

    /**
     * Imprime a lista de séries encontradas, incluindo suas temporadas.
     * Se estiver vazia, imprime uma mensagem de nenhuma série encontrada.
     *
     * @param seriesList Lista de séries a ser exibida.
     */
    private void printSeriesList(List<Series> seriesList) {
        if (seriesList.isEmpty())
            System.out.println(Colors.red + "Nenhuma série encontrada!" + Colors.rst);
        for (Series series : seriesList) {
            System.out.println(series);
            System.out.println("---------------------");
        }
    }
}
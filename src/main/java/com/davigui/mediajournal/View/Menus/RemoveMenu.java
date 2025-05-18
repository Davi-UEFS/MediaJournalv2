package com.davigui.mediajournal.View.Menus;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Medias.Book;
import com.davigui.mediajournal.Model.Medias.Movie;
import com.davigui.mediajournal.Model.Medias.Series;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.View.Prompts.*;

import java.util.Scanner;

/**
 * Classe responsável por exibir o menu de remoção de mídias e gerenciar as operações de exclusão.
 */
public class RemoveMenu {
    private final Scanner scanner; // Scanner para leitura de entradas do usuário.
    private final BookService bookService; // Serviço para gerenciamento de livros.
    private final MovieService movieService; // Serviço para gerenciamento de filmes.
    private final SeriesService seriesService; // Serviço para gerenciamento de séries.

    /**
     * Construtor da classe RemoveMenu.
     *
     * @param bookService Serviço para gerenciamento de livros.
     * @param movieService Serviço para gerenciamento de filmes.
     * @param seriesService Serviço para gerenciamento de séries.
     * @param scanner Scanner para leitura de entradas do usuário.
     */
    public RemoveMenu(BookService bookService, MovieService movieService,
                      SeriesService seriesService, Scanner scanner) {
        this.bookService = bookService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.scanner = scanner;
    }

    /**
     * Exibe o menu de remoção e gerencia as opções selecionadas pelo usuário.
     */
    public void show() {
        int option;

        do {
            System.out.println(Colors.red + "--== MENU DE REMOÇÃO ==--" + Colors.rst);
            System.out.println("1 - Remover livro");
            System.out.println("2 - Remover filme");
            System.out.println("3 - Remover série");
            System.out.println(Colors.red + "0 - Voltar" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    handleDeleteBook();
                    break;
                case 2:
                    handleDeleteMovie();
                    break;
                case 3:
                    handleDeleteSeries();
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
     * Gerencia a remoção de um livro selecionado pelo usuário.
     * Apenas exibe uma mensagem caso não existam livros cadastrados.
     */
    private void handleDeleteBook() {
        if (bookService.getAllBooks().isEmpty()) {
            System.out.println("Você não possui livros cadastrados.");
            return;
        }
        Book selectedBook = AskInput.selectFromList(scanner, bookService.getAllBooks());
        IResult result = bookService.deleteBook(selectedBook);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a remoção de um filme selecionado pelo usuário.
     * Apenas exibe uma mensagem caso não existam filmes cadastrados.
     */
    private void handleDeleteMovie() {
        if (movieService.getAllMovies().isEmpty()) {
            System.out.println("Você não possui filmes cadastrados.");
            return;
        }
        Movie selectedMovie = AskInput.selectFromList(scanner, movieService.getAllMovies());
        IResult result = movieService.deleteMovie(selectedMovie);
        System.out.println(result.getMessage());
    }

    /**
     * Gerencia a remoção de uma série selecionada pelo usuário.
     * Apenas exibe uma mensagem caso não existam séries cadastradas.
     */
    private void handleDeleteSeries() {
        if (seriesService.getAllSeries().isEmpty()) {
            System.out.println("Você não possui séries cadastradas.");
            return;
        }
        Series selectedSeries = AskInput.selectFromList(scanner, seriesService.getAllSeries());
        IResult result = seriesService.deleteSeries(selectedSeries);
        System.out.println(result.getMessage());
    }
}
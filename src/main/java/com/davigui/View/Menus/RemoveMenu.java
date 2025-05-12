package com.davigui.View.Menus;

import com.davigui.Controller.BookService;
import com.davigui.Controller.MovieService;
import com.davigui.Controller.SeriesService;
import com.davigui.Model.Medias.Book;
import com.davigui.Model.Medias.Movie;
import com.davigui.Model.Medias.Series;
import com.davigui.Model.Result.IResult;
import com.davigui.View.Prompts.AskInput;
import com.davigui.View.Prompts.Colors;
import com.davigui.View.Prompts.Validate;

import java.util.Scanner;

public class RemoveMenu {
    private final Scanner scanner; // Scanner para leitura de entradas do usuário.
    private final BookService bookService; // Serviço para gerenciamento de livros.
    private final MovieService movieService; // Serviço para gerenciamento de filmes.
    private final SeriesService seriesService; // Serviço para gerenciamento de séries.

    public RemoveMenu(BookService bookService, MovieService movieService,
                    SeriesService seriesService, Scanner scanner) {
        this.bookService = bookService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.scanner = scanner;
    }

    public void show() {
        int option;

        do {
            System.out.println(Colors.red + "--== MENU DE REMOÇÃO ==--" + Colors.rst);
            System.out.println("1 - Remover livro");
            System.out.println("2 - Remover filme");
            System.out.println("3 - Remover série");
            System.out.println("0 - Voltar");

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
        }while(option != 0);
    }

    private void handleDeleteBook() {
        if (bookService.getAllBooks().isEmpty()) {
            System.out.println("Você não possui livros cadastrados.");
            return;
        }
        Book selectedBook = AskInput.selectFromList(scanner, bookService.getAllBooks());
        IResult result = bookService.deleteBook(selectedBook);
        System.out.println(result.getMessage());
    }

    private void handleDeleteMovie() {
        if (movieService.getAllMovies().isEmpty()) {
            System.out.println("Você não possui filmes cadastrados.");
            return;
        }
        Movie selectedMovie = AskInput.selectFromList(scanner, movieService.getAllMovies());
        IResult result = movieService.deleteMovie(selectedMovie);
        System.out.println(result.getMessage());
    }

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

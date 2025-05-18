package com.davigui.mediajournal.View.Menus;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.View.Prompts.Colors;
import com.davigui.mediajournal.View.Prompts.Validate;

import java.util.Scanner;

/**
 * A classe MainMenu é responsável por exibir o menu principal do sistema e gerenciar
 * a navegação entre os diferentes submenus, como registro, avaliação, exibição e busca.
 */
public class MainMenu {
    private final Scanner scanner;  // Scanner para leitura de entradas do usuário.
    private final DisplayMenu displayMenu;  // Menu para exibição de informações.
    private final RegisterMenu registerMenu;  // Menu para registro de mídias.
    private final RateMenu rateMenu;  // Menu para avaliação de mídias.
    private final SearchMenu searchMenu;  // Menu para busca de mídias.
    private final RemoveMenu deleteMenu; // Menu para remoção de mídias.

    /**
     * Construtor da classe MainMenu.
     *
     * @param bookService Serviço para gerenciamento de livros.
     * @param movieService Serviço para gerenciamento de filmes.
     * @param seriesService Serviço para gerenciamento de séries.
     * @param scanner Objeto Scanner para leitura de entradas do usuário.
     */
    public MainMenu(BookService bookService, MovieService movieService,
                    SeriesService seriesService, Scanner scanner) {

        this.displayMenu = new DisplayMenu(bookService, movieService, seriesService, scanner);
        this.rateMenu = new RateMenu(bookService, movieService, seriesService, scanner);
        this.registerMenu = new RegisterMenu(bookService, movieService, seriesService, scanner);
        this.searchMenu = new SearchMenu(bookService, movieService, seriesService, scanner);
        this.deleteMenu = new RemoveMenu(bookService, movieService, seriesService, scanner);
        this.scanner = scanner;
    }

    /**
     * Exibe o menu principal e gerencia a navegação entre os submenus.
     * O menu principal oferece opções para registrar, avaliar, visualizar e buscar mídias.
     * O loop continua até que o usuário escolha a opção de encerrar.
     */
    public void showMenu(){
        int option;

        do{
            System.out.println(Colors.cyan + "--== DIÁRIO CULTURAL ==--" + Colors.rst);
            System.out.println("1 - Registrar ");
            System.out.println("2 - Avaliar ");
            System.out.println("3 - Ver");
            System.out.println("4 - Buscar");
            System.out.println("5 - Remover");
            System.out.println(Colors.red + "0 - Salvar e sair" + Colors.rst);

            option = Validate.validateInt(scanner);

            switch (option) {
                case 1:
                    registerMenu.show();
                    break;

                case 2:
                    rateMenu.show();
                    break;

                case 3:
                    displayMenu.show();
                    break;

                case 4:
                    searchMenu.show();
                    break;

                case 5:
                    deleteMenu.show();
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println(Colors.red + "Opção inválida " + Colors.rst);
                    break;
            }

        }while(option != 0);
        scanner.close();
    }
}
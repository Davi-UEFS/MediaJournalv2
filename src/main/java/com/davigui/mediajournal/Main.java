package com.davigui.mediajournal;

import com.davigui.mediajournal.Controller.BookService;
import com.davigui.mediajournal.Controller.MovieService;
import com.davigui.mediajournal.Controller.SeriesService;
import com.davigui.mediajournal.Model.Repository.DataOperations;
import com.davigui.mediajournal.Model.Repository.Library;
import com.davigui.mediajournal.Model.Result.IResult;
import com.davigui.mediajournal.View.Menus.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //Model (listas de midias)
        Library journal = new Library();

        //Carregar biblioteca
        DataOperations.load(journal);

        //Controller (add, avaliar, etc)
        BookService bookService = new BookService(journal);
        MovieService movieService = new MovieService(journal);
        SeriesService seriesService = new SeriesService(journal);

        //View (print e prompt)
        MainMenu menivis = new MainMenu(bookService, movieService, seriesService, scanner);
        menivis.showMenu();

        //Salvar biblioteca
        IResult saveResult = DataOperations.save(journal);
        System.out.println(saveResult.getMessage());

    }
}

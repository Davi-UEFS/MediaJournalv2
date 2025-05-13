package com.davigui;

import com.davigui.Controller.BookService;
import com.davigui.Controller.MovieService;
import com.davigui.Controller.SeriesService;
import com.davigui.Model.Repository.DAO;
import com.davigui.Model.Repository.Library;
import com.davigui.Model.Result.IResult;
import com.davigui.View.Menus.MainMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //Model (listas de midias)
        Library journal = new Library();

        //TODO: TIRAR ISSO DAQUI
        //TODO: PRINTAR TEMPORADA AO BUSCAR SERIES (NAO TA PRINTANDO)
        IResult resultLoad = DAO.load(journal);

        System.out.println(resultLoad.getMessage());
        //Controller (add, avaliar, etc)
        BookService bookService = new BookService(journal);
        MovieService movieService = new MovieService(journal);
        SeriesService seriesService = new SeriesService(journal);
        //View (print e prompt)
        MainMenu menivis = new MainMenu(bookService, movieService, seriesService, scanner);
        menivis.showMenu();
        IResult resultSave = DAO.save(journal);
        System.out.println(resultSave.getMessage());
    }
}

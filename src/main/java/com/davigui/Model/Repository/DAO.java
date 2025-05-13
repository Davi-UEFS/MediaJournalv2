package com.davigui.Model.Repository;

import com.davigui.Model.Result.*;
import com.davigui.Model.Medias.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class DAO {
    private static final String BOOKSPATH = "src/main/data/books.json";
    private static final String MOVIESPATH = "src/main/data/movies.json";
    private static final String SERIESPATH = "src/main/data/series.json";
    private static final String YEARSPATH = "src/main/data/years.json";

    private static final Gson gson = new Gson();

    public static IResult save(Library journal) {

        String booksJson = gson.toJson(journal.getBookList());
        String moviesJson = gson.toJson(journal.getMovieList());
        String seriesJson = gson.toJson(journal.getSeriesList());
        String yearJson = gson.toJson(journal.getYearsRegistered());

        try{
            saveFile(BOOKSPATH, booksJson);
            saveFile(MOVIESPATH, moviesJson);
            saveFile(SERIESPATH, seriesJson);
            saveFile(YEARSPATH, yearJson);
            return new Success("Biblioteca", "Salva com sucesso.");
        }catch (IOException e){
            return new Failure("Biblioteca", "Exceção de IO");
        }

    }

    public static IResult load(Library journal) throws IOException{

            Type bookType = new TypeToken<ArrayList<Book>>() {}.getType();
            Type movieType = new TypeToken<ArrayList<Movie>>() {}.getType();
            Type seriesType = new TypeToken<ArrayList<Series>>() {}.getType();
            Type yearType = new TypeToken<TreeSet<Integer>>() {}.getType();

            String booksJson = loadFile(BOOKSPATH);
            String moviesJson = loadFile(MOVIESPATH);
            String seriesJson = loadFile(SERIESPATH);
            String yearJson = loadFile(YEARSPATH);

            ArrayList<Book> books = gson.fromJson(booksJson, bookType);
            ArrayList<Movie> movies = gson.fromJson(moviesJson, movieType);
            ArrayList<Series> series = gson.fromJson(seriesJson,seriesType);
            TreeSet<Integer> years = gson.fromJson(yearJson, yearType);

            System.out.println(books);
            System.out.println("Esta e a lista de livros.");

            journal.setBookList(books);
            journal.setMovieList(movies);
            journal.setSeriesList(series);
            journal.setYearsRegistered(years);

            return new Success("Biblioteca", "Carregada com sucesso.");


    }

    private static void saveFile(String path, String json) throws IOException{

        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(json);
        fileWriter.close();
    }

    public static String loadFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }
        bufferedReader.close();
        System.out.println(stringBuilder);
        return stringBuilder.toString();
    }
}

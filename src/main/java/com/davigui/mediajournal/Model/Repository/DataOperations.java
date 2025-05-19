package com.davigui.mediajournal.Model.Repository;

import com.davigui.mediajournal.Model.Result.*;
import com.davigui.mediajournal.Model.Medias.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Classe estatica responsável por operações de manipulação de dados da biblioteca.
 */
public class DataOperations {
    //Caminho do arquivo JSON onde os dados dos livros estão armazenados.
    private static final String BOOKSPATH = "src/main/data/books.json";
    //Caminho do arquivo JSON onde os dados dos filmes estão armazenados.
    private static final String MOVIESPATH = "src/main/data/movies.json";
    //Caminho do arquivo JSON onde os dados das séries estão armazenados.
    private static final String SERIESPATH = "src/main/data/series.json";
    //Caminho do arquivo JSON onde os dados dos anos registrados estão armazenados.
    private static final String YEARSPATH = "src/main/data/years.json";

    private static final Gson gson = new Gson();

    /**
     * Salva os dados da biblioteca em arquivos JSON.
     *
     * @param journal A biblioteca cujos dados serão salvos.
     * @return Um resultado indicando sucesso ou falha na operação de salvamento.
     */
    public static IResult save(Library journal) {

        String booksJson = gson.toJson(journal.getBookList());
        String moviesJson = gson.toJson(journal.getMovieList());
        String seriesJson = gson.toJson(journal.getSeriesList());
        String yearJson = gson.toJson(journal.getYearsRegistered());

        try {
            saveFile(BOOKSPATH, booksJson);
            saveFile(MOVIESPATH, moviesJson);
            saveFile(SERIESPATH, seriesJson);
            saveFile(YEARSPATH, yearJson);
            return new Success("Biblioteca", "Salva com sucesso.");
        } catch (IOException e) {
            return new Failure("Biblioteca", "Exceção de IO");
        }
    }

    /**
     * Carrega os dados da biblioteca a partir de arquivos JSON.
     *
     * @param journal A biblioteca onde os dados serão carregados.
     * @return Um resultado indicando sucesso na operação de carregamento.
     * @throws IOException Se ocorrer um erro ao acessar os arquivos.
     */
    public static IResult load(Library journal) throws IOException {

        Type bookType = new TypeToken<ArrayList<Book>>() {}.getType();
        Type movieType = new TypeToken<ArrayList<Movie>>() {}.getType();
        Type seriesType = new TypeToken<ArrayList<Series>>() {}.getType();
        Type yearType = new TypeToken<TreeMap<Integer, Integer>>() {}.getType();

        Reader booksReader = loadFile(BOOKSPATH);
        Reader moviesReader = loadFile(MOVIESPATH);
        Reader seriesReader = loadFile(SERIESPATH);
        Reader yearReader = loadFile(YEARSPATH);

        ArrayList<Book> books = gson.fromJson(booksReader, bookType);
        ArrayList<Movie> movies = gson.fromJson(moviesReader, movieType);
        ArrayList<Series> series = gson.fromJson(seriesReader, seriesType);
        TreeMap<Integer, Integer> years = gson.fromJson(yearReader, yearType);

        journal.setBookList(books);
        journal.setMovieList(movies);
        journal.setSeriesList(series);
        journal.setYearsRegistered(years);

        booksReader.close();
        moviesReader.close();
        seriesReader.close();
        yearReader.close();

        return new Success("Biblioteca", "Carregada com sucesso.");
    }

    /**
     * Salva um arquivo JSON no caminho especificado.
     *
     * @param path O caminho do arquivo onde os dados serão salvos.
     * @param json O conteúdo JSON a ser salvo.
     * @throws IOException Se ocorrer um erro ao escrever no arquivo.
     */
    private static void saveFile(String path, String json) throws IOException {

        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(json);
        fileWriter.close();
    }

    /**
     * Carrega um arquivo JSON do caminho especificado.
     * Se o arquivo não for encontrado, retorna um leitor com um JSON vazio.
     *
     * @param path O caminho do arquivo a ser carregado.
     * @return Um leitor para o conteúdo do arquivo.
     */
    private static Reader loadFile(String path) {

        try {
            return new FileReader(path);
        } catch (FileNotFoundException e) {
            return new StringReader("[]");
        }
    }

}

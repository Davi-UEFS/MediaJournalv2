package com.davigui.Controller;

import com.davigui.Model.Enums.Genres;
import com.davigui.Model.Enums.Months;
import com.davigui.Model.Exceptions.MediaAlreadyExistsException;
import com.davigui.Model.Repository.Library;
import com.davigui.Model.Medias.Book;
import com.davigui.Model.Result.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe BookService fornece serviços relacionados ao gerenciamento de livros.
 * Ela estende a classe CommonService e utiliza a biblioteca para registrar, buscar e manipular livros.
 */
public class BookService extends CommonService<Book> {

    /**
     * Construtor da classe BookService.
     *
     * @param journal A biblioteca que será utilizada para gerenciar os livros.
     */
    public BookService(Library journal) {
        super(journal);
    }

    /**
     * Registra um novo livro na biblioteca.
     *
     * @param name O nome do livro.
     * @param year O ano de publicação do livro.
     * @param genre O gênero do livro.
     * @param isbn O ISBN do livro.
     * @param author O autor do livro.
     * @param publisher A editora do livro.
     * @param owned Indica se o livro é de propriedade do usuário.
     * @return Um resultado indicando sucesso ou falha no registro.
     */
    public IResult register(String name, int year, Genres genre, String isbn,
                            String author, String publisher, boolean owned) {

        Book book = new Book(name, year, genre, isbn, author, publisher, owned);

        try {
            journal.exists(book);
            journal.add(book);
            journal.addYear(year);
            return new Success("Livro", "Registrado com sucesso!");
        } catch (MediaAlreadyExistsException e) {
            return new Failure("Livro", e.getMessage());
        }
    }

    /**
     * Busca livros pelo ISBN. Filtra os livros que contêm o ISBN, utilizando o metodo filter da biblioteca Stream.
     *
     * @param isbn O ISBN a ser buscado.
     * @return Uma lista de livros que correspondem ao ISBN.
     */
    public List<Book> searchBookByIsbn(String isbn) {
        String isbnLower = isbn.toLowerCase().trim();
        List<Book> filteredBooks = journal.getBookList().stream().filter
                (book -> book.getIsbn().toLowerCase().contains(isbnLower)).toList();

        return sortAscending(filteredBooks);
    }

    /**
     * Busca livros pelo nome do autor. Filtra os livros que contêm o nome do autor, utilizando o metodo filter da biblioteca Stream.
     *
     * @param author O nome do autor a ser buscado.
     * @return Uma lista de livros que possuem o autor especificado.
     */
    public List<Book> searchBookByAuthor(String author) {
        String authorLower = author.toLowerCase().trim();
        List<Book> filteredBooks = journal.getBookList().stream().filter
                (book -> book.getAuthor().toLowerCase().contains(authorLower)).toList();

        return sortAscending(filteredBooks);
    }

    /**
     * Marca um livro como lido e registra a data de leitura.
     *
     * @param book O livro a ser marcado como lido.
     * @param year O ano em que o livro foi lido.
     * @param month O mês em que o livro foi lido.
     * @return Um resultado indicando sucesso ou falha na operação.
     */
    public IResult markAsSeen(Book book, int year, Months month) {

        if (book.isSeen())
            return new Failure("Livro", "Já marcado como lido");

        if (year < book.getYear() || year > LocalDate.now().getYear())
            return new Failure("Livro", "Ano inválido!");

        String date = month.toString() + " de " + year;
        book.setSeen(true);
        book.setSeenDate(date);
        return new Success("Livro", "Data de leitura registrada.");
    }

    /**
     * Obtém todos os livros registrados na biblioteca.
     *
     * @return Uma lista contendo todos os livros.
     */
    public ArrayList<Book> getAllBooks() {
        return journal.getBookList();
    }

    /**
     * Remove um livro da biblioteca.
     *
     * @param book O livro a ser removido.
     * @return Um resultado indicando sucesso ou falha na remoção.
     */
    public IResult deleteBook(Book book) {
        if (journal.getBookList().contains(book)) {
            journal.remove(book);
            journal.removeYear(book.getYear());
            return new Success("Livro", "Removido com sucesso!");
        } else {
            return new Failure("Livro", "Esse livro não existe na biblioteca!");
        }
    }
}
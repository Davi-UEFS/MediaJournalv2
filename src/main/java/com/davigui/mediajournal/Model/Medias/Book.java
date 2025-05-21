package com.davigui.mediajournal.Model.Medias;
import com.davigui.mediajournal.Model.Enums.Genres;

/**
 * A classe Book representa um livro como uma mídia, estendendo a classe base Media.
 * Ela contém informações específicas de livros, como ISBN, autor, editora,
 * se o usuário possui um exemplar e a data em que foi visto.
 */
public class Book extends Media {
    // O ISBN do livro
    private String isbn;
    // O autor do livro
    private String author;
    // A editora do livro
    private String publisher;
    // Indica se o livro é de propriedade do usuário
    private boolean owned;
    // A data em que o livro foi visto (formato: MES de XXXX)
    private String seenDate;

    /**
     * Construtor da classe Book.
     * Os parâmetros são os atributos imutáveis do livro.
     * Os atributos imutáveis são inicializados pelo construtor
     * da superclasse.
     *
     * @param title     O título do livro.
     * @param year      O ano de publicação do livro.
     * @param genre     O gênero do livro.
     * @param isbn      O ISBN do livro.
     * @param author    O autor do livro.
     * @param publisher A editora do livro.
     * @param owned     Indica se o livro é de propriedade do usuário.
     */
    public Book(String title, int year, Genres genre, String isbn, String author, String publisher, boolean owned) {
        super(title, year, genre);
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.owned = owned;
        this.seenDate = null;
    }

    /**
     * Construtor no args da classe Book usado na desserialização.
     * Utiliza o construtor da superclasse.
     */
    public Book(){

    }

    /**
     * Obtém o ISBN do livro.
     *
     * @return O ISBN do livro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Obtém o autor do livro.
     *
     * @return O autor do livro.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Define a data em que o livro foi visto.
     *
     * @param readDate A data em que o livro foi visto.
     */
    public void setSeenDate(String readDate) {
        this.seenDate = readDate;
    }

    /**
     * Obtém o identificador único do livro com base no hash do ISBN.
     * Sobrescreve o método de Media.
     *
     * @return O identificador único do livro.
     */
    @Override
    public int getId() {
        return isbn.hashCode();
    }

    /**
     * Obtém o tipo de mídia, que neste caso é "Livro".
     *
     * @return Uma string representando o tipo de mídia.
     */
    @Override
    public String getMediaType() {
        return "Livro";
    }

    /**
     * Sobrecarrega o método  de Object.
     * Retorna uma representação em string do livro, incluindo título, ano,
     * autor, editora, ISBN, se possui exemplar, data em que foi
     * visto (se disponível) e avaliação (se disponível).
     *
     * @return Uma string representando o livro.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(
                "\n" + title + " (" + this.year + ")" +
                "\nAutor: " + author +
                "\nEditora: " + publisher +
                "\nISBN: " + isbn +
                "\nPossui? " + (owned ? "Sim" : "Não")
                );

        if (seenDate != null)
            string.append("\nVisto em: ").append(seenDate);

        if (rating != 0)
            string.append("\nAvaliação: ").append("★".repeat(rating));

        return string.toString();
    }
}
package com.davigui.View.Prompts;

import com.davigui.Model.Enums.Genres;
import com.davigui.Model.Enums.Months;
import com.davigui.Model.Medias.Media;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * A classe AskInput fornece métodos utilitários para solicitar e validar entradas do usuário
 * relacionadas a diferentes atributos de mídias, como título, autor, gênero, elenco, entre outros.
 */
public final class AskInput {

    /**
     * Solicita ao usuário o título de uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O título da obra como uma string.
     */
    public static String askForTitle(Scanner scanner) {
        System.out.print("Digite o título da obra: ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário o nome do autor.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O nome do autor como uma string.
     */
    public static String askForAuthor(Scanner scanner) {
        System.out.print("Digite o nome do autor: ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário o ISBN de uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O ISBN da obra como uma string.
     */
    public static String askForISBN(Scanner scanner) {
        System.out.print("Digite o ISBN da obra (XXX-9999999-9): ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário o nome da editora.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O nome da editora como uma string.
     */
    public static String askForPublisher(Scanner scanner) {
        System.out.print("Digite o nome do editora: ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário a duração de um filme em minutos.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return A duração do filme como um objeto Duration.
     */
    public static int askForDuration(Scanner scanner) {
        System.out.print("Digite a duração do filme (em minutos): ");
        return Validate.validateInt(scanner);
    }

    /**
     * Solicita ao usuário o nome do diretor.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O nome do diretor como uma string.
     */
    public static String askForDirector(Scanner scanner) {
        System.out.print("Digite o nome do diretor: ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário o ano de lançamento de uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O ano de lançamento como um inteiro.
     */
    public static int askForYear(Scanner scanner) {
        System.out.print("Digite o ano de lançamento (YYYY): ");
        int year = Validate.validateInt(scanner);
        while (year > LocalDate.now().getYear()) {
            System.out.println("Ano inválido! Digite novamente: ");
            year = Validate.validateInt(scanner);
        }
        return year;
    }

    /**
     * Solicita ao usuário o ano de encerramento de uma série.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O ano de encerramento como um inteiro.
     */
    public static int askForYearOfEnding(Scanner scanner) {
        System.out.print("Digite o ano de encerramento (9999 se ainda está em lançamento): ");
        int year = Validate.validateInt(scanner);
        while ((year > LocalDate.now().getYear() && year < 9999) || (year > 9999)) {
            System.out.println("Ano inválido! Digite novamente: ");
            year = Validate.validateInt(scanner);
        }
        return year;
    }

    /**
     * Solicita ao usuário se ele possui uma cópia de um livro.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return True se o usuário possui uma cópia, false caso contrário.
     */
    public static boolean askForOwned(Scanner scanner) {
        System.out.print("Você possui uma cópia deste livro? (S/N): ");
        return Validate.validateBoolean(scanner);
    }

    /**
     * Solicita ao usuário o elenco de uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return Um array de strings contendo os nomes do elenco.
     */
    public static String[] askForCast(Scanner scanner) {
        System.out.println("Digite o elenco da obra: (Fulano, Ciclano, ...)");
        return Validate.validateString(scanner).split(", ");
    }

    /**
     * Solicita ao usuário o nome de um ator ou atriz.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O nome do ator ou atriz como uma string.
     */
    public static String askForActor(Scanner scanner) {
        System.out.println("Digite o nome do ator/atriz:");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário o título original de uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O título original da obra como uma string.
     */
    public static String askForOriginalTitle(Scanner scanner) {
        System.out.print("Digite o título original da obra: ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário a sinopse de um filme.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return A sinopse do filme como uma string.
     */
    public static String askForScript(Scanner scanner) {
        System.out.println("Digite a sinopse do filme: ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário as plataformas onde uma obra está disponível.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return Um array de strings contendo os nomes das plataformas.
     */
    public static String[] askForWhereToWatch(Scanner scanner) {
        System.out.println("Digite as plataformas onde a obra está disponível: (Streaming1, Streaming2, ...)");
        return Validate.validateString(scanner).split(", ");
    }

    /**
     * Solicita ao usuário o número de uma temporada.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O número da temporada como um inteiro.
     */
    public static int askForSeasonNumber(Scanner scanner) {
        System.out.print("Digite o número da temporada: ");
        return Validate.validateInt(scanner);
    }

    /**
     * Solicita ao usuário o gênero de uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O gênero selecionado como um valor do enum Genres.
     */
    public static Genres askForGenre(Scanner scanner) {
        Genres.showGenres();
        System.out.print("Digite um dos gêneros acima: ");
        int wantedGenre = Validate.validateInt(scanner);
        while (wantedGenre < 1 || wantedGenre > 12) {
            System.out.println("Opção inválida! Digite novamente: ");
            wantedGenre = Validate.validateInt(scanner);
        }
        return Genres.values()[wantedGenre - 1];
    }

    /**
     * Solicita ao usuário uma nota para uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return A nota como um valor int.
     */
    public static int askForRate(Scanner scanner) {
        System.out.println("Digite a nota (1 a 5) ");
        return Validate.validateInt(scanner);
    }

    /**
     * Solicita ao usuário uma review para uma obra.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return A review como uma string.
     */
    public static String askForReview(Scanner scanner) {
        System.out.println("Digite sua review: ");
        return Validate.validateString(scanner);
    }

    /**
     * Solicita ao usuário a quantidade de episódios de uma temporada.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return A quantidade de episódios como um inteiro.
     */
    public static int askForEpisodeCount(Scanner scanner) {
        System.out.print("Digite a quantidade de episódios: ");
        return Validate.validateInt(scanner);
    }

    /**
     * Solicita ao usuário o ano de uma temporada.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O ano da temporada como um inteiro.
     */
    public static int askForSeasonYear(Scanner scanner) {
        System.out.print("Digite o ano da temporada: ");
        return Validate.validateInt(scanner);
    }

    /**
     * Permite ao usuário selecionar uma obra de uma lista.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @param mediaList A lista de mídias disponíveis para seleção.
     * @param <T> O tipo de mídia que estende a classe Media.
     * @return A mídia selecionada pelo usuário.
     */
    public static <T extends Media> T selectFromList(Scanner scanner, List<T> mediaList) {
        int choice;
        System.out.println("Por favor, selecione uma obra entre as seguintes:");

        for (int i = 0; i < mediaList.size(); i++) {
            Media media = mediaList.get(i);
            System.out.printf("%d - %s (%s)\n", i + 1, media.getTitle(), media.getMediaType());
        }

        choice = Validate.validateInt(scanner);
        while (choice < 1 || choice > mediaList.size()) {
            System.out.printf("Escolha de 1 a %d.\n", mediaList.size());
            choice = Validate.validateInt(scanner);
        }
        return mediaList.get(choice - 1);
    }

    /**
     * Solicita ao usuário o ano em que uma obra foi lida ou assistida.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O ano como um inteiro.
     */
    public static int askForSeenYear(Scanner scanner) {
        System.out.print("Digite o ano em que foi lido: ");
        return Validate.validateInt(scanner);
    }

    /**
     * Solicita ao usuário o mês em que uma obra foi lida ou assistida.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O mês como um valor do enum Months.
     */
    public static Months askForSeenMonth(Scanner scanner) {
        System.out.print("Digite o mes em que foi lido (número do mês): ");
        int wantedValue = Validate.validateInt(scanner);
        if (wantedValue < 1 || wantedValue > 12) {
            System.out.print("Mês inválido! Digite novamente: ");
            wantedValue = Validate.validateInt(scanner);
        }
        return Months.values()[wantedValue - 1];
    }
}
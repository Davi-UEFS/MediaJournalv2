package com.davigui.mediajournal.View.Prompts;

import java.util.Scanner;

/**
 * A classe Validate fornece métodos utilitários para validar entradas do usuário.
 * Ela garante que os dados inseridos sejam do tipo esperado, como inteiros,
 * doubles, strings ou booleanos, prevenindo exceções.
 */
public final class Validate {

    /**
     * Valida a entrada do usuário como um número inteiro.
     * Solicita repetidamente até que uma entrada válida seja fornecida.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O número inteiro validado.
     */
    public static int validateInt(Scanner scanner) {
        boolean validInt;
        int num = 0;
        do {
            validInt = true;
            try {
                num = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                validInt = false;
                System.out.println("Erro. Digite um numero");
            }
        } while (!validInt);

        return num;
    }

    /**
     * Valida a entrada do usuário como uma string não vazia.
     * Solicita repetidamente até que uma entrada válida seja fornecida.
     * Retira os espações em branco no final da String.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return A string validada, sem espaços em branco no início ou no final.
     */
    public static String validateString(Scanner scanner) {
        String str;

        str = scanner.nextLine();
        while (str.isBlank()) {
            System.out.println("Erro. Digite um texto válido");
            str = scanner.nextLine();
        }
        return str.trim();
    }

    /**
     * Valida a entrada do usuário como um valor booleano.
     * Aceita "s", "sim" ou "true" para true e "n", "nao" ou "false" para false.
     * Solicita repetidamente até que uma entrada válida seja fornecida.
     *
     * @param scanner O objeto Scanner para leitura da entrada do usuário.
     * @return O valor booleano validado.
     */
    public static boolean validateBoolean(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("s") || input.equals("sim") || input.equals("true")) {
                return true;

            } else if (input.equals("n") || input.equals("nao") || input.equals("false")) {
                return false;
            }

            System.out.println("Erro: Digite apenas s/n ou sim/nao.");
        }
    }
}
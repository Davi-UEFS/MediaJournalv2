package com.davigui.mediajournal.Model.Enums;

/**
 * O enum Genres representa os diferentes gêneros de mídia disponíveis.
 * Ele inclui gêneros como Terror, Ação, Aventura, Suspense, Romance, entre outros.
 */
public enum Genres {
    TERROR,       // Gênero de terror
    AÇÃO,         // Gênero de ação
    AVENTURA,     // Gênero de aventura
    SUSPENSE,     // Gênero de suspense
    ROMANCE,      // Gênero de romance
    FICÇÃO,       // Gênero de ficção
    ESPORTES,     // Gênero de esportes
    COMÉDIA,      // Gênero de comédia
    MISTÉRIO,     // Gênero de mistério
    CRIMINAL,     // Gênero criminal
    INFANTIL,     // Gênero infantil
    OUTROS;       // Demais gêneros

    /**
     * Exibe todos os gêneros disponíveis no console, numerados sequencialmente.
     * Cada gênero é exibido no formato "número - nome do gênero".
     */
    public static void showGenres() {
        for (int i = 0; i < Genres.values().length; ++i) {
            System.out.println(i + 1 + " - " + Genres.values()[i].name());
        }
    }
}
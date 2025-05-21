package com.davigui.mediajournal.Model.Exceptions;

/**
 * A exceção SeasonNotFoundException é lançada pelo Model (Series) da aplicação.
 * Indica que a temporada dada não existe na lista de temporadas de uma série.
 */
public class SeasonNotFoundException extends RuntimeException {
    public SeasonNotFoundException(String message) {
        super(message);
    }
}

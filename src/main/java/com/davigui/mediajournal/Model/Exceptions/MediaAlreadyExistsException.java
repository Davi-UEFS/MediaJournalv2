package com.davigui.mediajournal.Model.Exceptions;

/**
 * A exceção MediaAlreadyExistsException é lançada pelo Model (Library) da aplicação.
 * Indica que uma obra com aquele ID já existe no repositório.
 */
public class MediaAlreadyExistsException extends RuntimeException {
    public MediaAlreadyExistsException(String message) {
        super(message);
    }
}

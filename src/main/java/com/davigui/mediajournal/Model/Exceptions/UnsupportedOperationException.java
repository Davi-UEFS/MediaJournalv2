package com.davigui.mediajournal.Model.Exceptions;

/**
 * A exceção UnsupportedOperationException é lançada pelo Controller (SeriesService) da aplicação.
 * É lançada ao se tentar usar alguns métodos de SeriesService que foram
 * herdados de CommonService
 */
public class UnsupportedOperationException extends RuntimeException {
    public UnsupportedOperationException(String message) {
        super(message);
    }
}

package com.davigui.Model.Exceptions;

public class SeasonNotFoundException extends RuntimeException {
    public SeasonNotFoundException(String message) {
        super(message);
    }
}

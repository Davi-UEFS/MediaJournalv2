package com.davigui.mediajournal.Model.Result;
import com.davigui.mediajournal.View.Prompts.Colors;

/**
 * A classe Failure implementa a interface IResult e representa um resultado de falha.
 * Ela contém informações sobre o tipo de objeto e uma mensagem de erro formatada.
 */
public class Failure implements IResult {
    // Mensagem de erro associada à falha
    private final String message;
    // Tipo de objeto relacionado à falha
    private final String objectType;

    /**
     * Construtor da classe Failure.
     *
     * @param objectType O tipo de objeto relacionado à falha.
     * @param message   A mensagem de erro associada à falha.
     */
    public Failure(String objectType, String message) {
        this.message = message;
        this.objectType = objectType;
    }

    /**
     * Obtém a mensagem de erro formatada associada à falha.
     * A mensagem inclui o tipo de objeto, o prefixo "ERRO." e a mensagem de erro,
     * formatados com a cor vermelha.
     *
     * @return Uma string representando a mensagem de erro formatada.
     */
    public String getMessage() {
        return objectType + " : " + "ERRO. " + message;
    }
}
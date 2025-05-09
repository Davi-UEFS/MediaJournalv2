package com.davigui.Model.Result;
import com.davigui.View.Prompts.Colors;

/**
 * A classe Failure implementa a interface IResult e representa um resultado de falha.
 * Ela contém informações sobre o tipo de mídia e uma mensagem de erro formatada.
 */
public class Failure implements IResult {
    // Mensagem de erro associada à falha
    private final String message;
    // Tipo de mídia relacionado à falha
    private final String mediaType;

    /**
     * Construtor da classe Failure.
     *
     * @param mediaType O tipo de mídia relacionado à falha.
     * @param message   A mensagem de erro associada à falha.
     */
    public Failure(String mediaType, String message) {
        this.message = message;
        this.mediaType = mediaType;
    }

    /**
     * Obtém a mensagem de erro formatada associada à falha.
     * A mensagem inclui o tipo de mídia, o prefixo "ERRO." e a mensagem de erro,
     * formatados com a cor vermelha.
     *
     * @return Uma string representando a mensagem de erro formatada.
     */
    public String getMessage() {
        return Colors.red + mediaType + " : " + "ERRO. " + message + Colors.rst;
    }
}
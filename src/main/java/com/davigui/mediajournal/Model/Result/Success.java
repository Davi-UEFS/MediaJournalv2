package com.davigui.mediajournal.Model.Result;
import com.davigui.mediajournal.View.Prompts.Colors;

/**
 * A classe Success implementa a interface IResult e representa um resultado de sucesso.
 * Ela contém informações sobre o tipo de objeto e uma mensagem de sucesso formatada.
 */
public class Success implements IResult {
    // Mensagem de sucesso associada ao resultado
    private final String message;
    // Tipo de objeto relacionado ao sucesso
    private final String objectType;

    /**
     * Construtor da classe Success.
     *
     * @param objectType O tipo de objeto relacionado ao sucesso.
     * @param message   A mensagem de sucesso associada ao resultado.
     */
    public Success(String objectType, String message) {
        this.message = message;
        this.objectType = objectType;
    }

    /**
     * Obtém a mensagem de sucesso formatada associada ao resultado.
     * A mensagem inclui o tipo de objeto, o prefixo da mensagem e o símbolo de sucesso,
     * formatados com a cor verde.
     *
     * @return Uma string representando a mensagem de sucesso formatada.
     */
    public String getMessage() {
        return objectType + " : " + message + " ✔";
    }
}
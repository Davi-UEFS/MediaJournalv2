package com.davigui.mediajournal.Model.Result;
import com.davigui.mediajournal.View.Prompts.Colors;

/**
 * A classe Success implementa a interface IResult e representa um resultado de sucesso.
 * Ela contém informações sobre o tipo de mídia e uma mensagem de sucesso formatada.
 */
public class Success implements IResult {
    // Mensagem de sucesso associada ao resultado
    private final String message;
    // Tipo de mídia relacionado ao sucesso
    private final String mediaType;

    /**
     * Construtor da classe Success.
     *
     * @param mediaType O tipo de mídia relacionado ao sucesso.
     * @param message   A mensagem de sucesso associada ao resultado.
     */
    public Success(String mediaType, String message) {
        this.message = message;
        this.mediaType = mediaType;
    }

    /**
     * Obtém a mensagem de sucesso formatada associada ao resultado.
     * A mensagem inclui o tipo de mídia, o prefixo da mensagem e o símbolo de sucesso,
     * formatados com a cor verde.
     *
     * @return Uma string representando a mensagem de sucesso formatada.
     */
    public String getMessage() {
        return Colors.green + mediaType + " : " + message + " ✔" + Colors.rst;
    }
}
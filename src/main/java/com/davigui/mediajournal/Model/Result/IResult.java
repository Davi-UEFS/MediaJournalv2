package com.davigui.mediajournal.Model.Result;

/**
 * A interface IResult define um contrato para classes que fornecem uma mensagem de resultado.
 * Ela contém um único método para obter a mensagem associada ao resultado.
 */
public interface IResult {
    /**
     * Obtém a mensagem associada ao resultado.
     *
     * @return Uma string representando a mensagem do resultado.
     */
    String getMessage();
}
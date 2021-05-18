package br.com.unibh.compiler.pasc.lexic.exceptions;

/**
 * Exceção criada para quando não houver caracteres válidos
 *
 * @author Lucas Dutra
 * @since 04 de maio de 2021
 */
public class IllegalCharacterException extends RuntimeException {

    private static final long serialVersionUID = 970662883126579483L;

    public IllegalCharacterException(String message) {
        super(message);
    }
}

package br.com.unibh.compiler.pasc.lexic.model;

import java.text.MessageFormat;

/**
 * Classe especifica para armazenar os erros encontrados no reconhecedor lexico
 * Será utilizado para ativar o PanicMode também
 *
 * @author Lucas Dutra
 * @since 16 de maio 2021
 */
public class TokeError extends Token {

    public TokeError(String name, String value, int line, int column) {
        super(name, value, line, column);
    }

    @Override
    public String toString() {
        return MessageFormat.format("[{0},{1}] {2}", getLine(), getColumn(), getValue());
    }
}

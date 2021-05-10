package br.com.unibh.compiler.pasc.lexic.exceptions;

import java.io.Serial;

public class OperatorNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -9059764467414750671L;

    public OperatorNotFoundException() {
        super("Operador não encontrado!");
    }
}

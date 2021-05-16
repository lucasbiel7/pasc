package br.com.unibh.compiler.pasc.lexic.exceptions;

import br.com.unibh.compiler.pasc.lexic.service.LanguageLexer;

import java.io.Serial;

public class UnexpectedSymbolException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3448955172637459405L;


    public UnexpectedSymbolException(String message, Object... args) {
        super(LanguageLexer.getInstance().message(message, args));
    }
}

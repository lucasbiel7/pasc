package br.com.unibh.compiler.pasc.lexic.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Constants implements TokenName {

    NUM_CONST("NUM_CONST"),
    CHAR_CONST("CHAR_CONST"),
    IDENTIFIER("ID");

    private final String tokenName;

    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Constantes não possuem valor fixo!");
    }
}

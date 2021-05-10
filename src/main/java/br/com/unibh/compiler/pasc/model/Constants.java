package br.com.unibh.compiler.pasc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Constants implements TokenName {

    NUM_CONST("NUM_CONST"),
    CHAR_CONST("CHAR_CONST");

    private final String tokenName;

    @Override
    public String getTokenName() {
        return null;
    }

    @Override
    public String getValue() {
        throw new UnsupportedOperationException("Constantes não possuem valor fixo!");
    }
}

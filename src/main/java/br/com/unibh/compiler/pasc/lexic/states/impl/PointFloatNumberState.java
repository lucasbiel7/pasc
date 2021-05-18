package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.UnexpectedSymbolException;
import br.com.unibh.compiler.pasc.lexic.states.State;

public class PointFloatNumberState implements State {
    private String value;

    public PointFloatNumberState(String value, char value1) {
        this.value = value + value1;
    }

    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new FloatNumberState(this.value, value);
        throw new UnexpectedSymbolException("MSG010");
    }
}

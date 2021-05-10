package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.IllegalCharacterException;
import br.com.unibh.compiler.pasc.lexic.states.State;

public class PointFloatNumberState implements State {
    private String value;

    public PointFloatNumberState(String value, char value1) {
        this.value = value + value1;
    }

    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new FloatNumberState(this.value, value);
        throw new IllegalCharacterException("Estávamos aguardando um número após o ponto.");
    }
}

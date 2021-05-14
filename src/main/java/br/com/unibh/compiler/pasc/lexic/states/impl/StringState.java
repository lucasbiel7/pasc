package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;

public class StringState implements State {
    private StringBuilder value;
    private char delimiter;

    public StringState(char delimiter) {
        this.delimiter = delimiter;
        this.value = new StringBuilder();

    }

    @Override
    public State nextState(char value) {
        if (value == delimiter){
            return new ClosedStringState(this.value.toString());
        }
        if (value == '\n'){
            throw new UnsupportedOperationException("quebra de linha '\\n' não é válido ");
        }
        if (value < 128){
            this.value.append(value);
            return this;
        }
        throw new UnsupportedOperationException("Caracter inválido "+ value);
    }
}

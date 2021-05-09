package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.states.FinalStateBeforeNext;
import br.com.unibh.compiler.pasc.states.State;
import lombok.Getter;

@Getter
public class NumberState implements FinalStateBeforeNext {

    private final String value;

    NumberState(char value) {
        this.value = String.valueOf(value);
    }

    NumberState(NumberState numberState, char value) {
        this.value = numberState.value + value;
    }

    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new NumberState(this, value);
        if (value == '.')
            return new FloatNumberState(this.value, value);
        return null;
    }
}

package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.states.FinalStateBeforeNext;
import br.com.unibh.compiler.pasc.states.State;

public class NumberState implements FinalStateBeforeNext {

    private final String world;

    NumberState(char value) {
        this.world = String.valueOf(value);
    }

    NumberState(NumberState numberState, char value) {
        this.world = numberState.world + value;
    }

    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new NumberState(this, value);
        return null;
    }
}

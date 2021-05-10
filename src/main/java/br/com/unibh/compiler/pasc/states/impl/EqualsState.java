package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.model.Operators;
import br.com.unibh.compiler.pasc.states.FinalState;

public class EqualsState implements FinalState {

    @Override
    public String name() {
        return Operators.OP_EQ.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_EQ.getValue();
    }
}

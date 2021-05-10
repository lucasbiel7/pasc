package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;

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

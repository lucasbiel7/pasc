package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;

import java.util.Objects;

public class GreaterThanOrEqualsState implements FinalState {
    private static GreaterThanState instance;

    public static GreaterThanState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new GreaterThanState();
        }
        return instance;
    }

    @Override
    public String name() {
        return Operators.OP_GE.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_GE.getValue();
    }
}

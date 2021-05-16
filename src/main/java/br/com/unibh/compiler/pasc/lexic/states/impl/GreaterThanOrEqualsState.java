package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;

import java.util.Objects;

public class GreaterThanOrEqualsState implements FinalState {
    private static GreaterThanOrEqualsState instance;

    public static GreaterThanOrEqualsState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new GreaterThanOrEqualsState();
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

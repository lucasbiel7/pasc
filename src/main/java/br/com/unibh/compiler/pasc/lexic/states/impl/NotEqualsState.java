package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;

import java.util.Objects;

public class NotEqualsState implements FinalState {
    private static NotEqualsState instance;

    public static NotEqualsState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new NotEqualsState();
        }
        return instance;
    }

    @Override
    public String name() {
        return Operators.OP_NE.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_NE.getValue();
    }
}

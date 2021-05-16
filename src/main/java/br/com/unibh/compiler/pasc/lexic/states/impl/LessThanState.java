package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LessThanState implements FinalState {
    private static LessThanState instance;

    public static LessThanState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new LessThanState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '=' -> LessThanOrEqualsState.getInstance();
            default -> FinalState.super.nextState(value);
        };
    }

    @Override
    public String name() {
        return Operators.OP_LT.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_LT.getValue();
    }
}

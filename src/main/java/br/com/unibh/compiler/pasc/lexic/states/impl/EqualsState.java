package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EqualsState implements FinalState {

    private static EqualsState instance;

    public static EqualsState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new EqualsState();
        }
        return instance;
    }

    @Override
    public String name() {
        return Operators.OP_EQ.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_EQ.getValue();
    }
}

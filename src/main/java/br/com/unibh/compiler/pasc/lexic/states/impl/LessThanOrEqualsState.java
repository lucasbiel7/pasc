package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LessThanOrEqualsState implements FinalState {

    private static LessThanOrEqualsState instance;

    public static LessThanOrEqualsState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new LessThanOrEqualsState();
        }
        return instance;
    }

    @Override
    public String name() {
        return Operators.OP_LE.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_LE.getValue();
    }
}

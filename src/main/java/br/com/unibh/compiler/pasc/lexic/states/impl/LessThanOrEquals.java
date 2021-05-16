package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LessThanOrEquals implements FinalState {

    private static LessThanOrEquals instance;

    public static LessThanOrEquals getInstance() {
        if (Objects.isNull(instance)) {
            instance = new LessThanOrEquals();
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

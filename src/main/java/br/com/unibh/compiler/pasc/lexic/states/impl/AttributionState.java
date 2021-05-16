package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Lucas Dutra
 * @since 04 maio 2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AttributionState implements FinalState {
    private static AttributionState instance;

    public static AttributionState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AttributionState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '=' -> EqualsState.getInstance();
            default -> EmptyState.getInstance();
        };
    }

    @Override
    public String name() {
        return Operators.OP_ATRIB.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_ATRIB.getValue();
    }
}

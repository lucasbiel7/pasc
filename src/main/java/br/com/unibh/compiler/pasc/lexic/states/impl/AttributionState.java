package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;

/**
 * @author Lucas Dutra
 * @since 04 maio 2021
 */
public class AttributionState implements FinalState {

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '=' -> new EqualsState();
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

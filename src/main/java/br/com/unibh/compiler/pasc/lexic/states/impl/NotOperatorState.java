package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.UnexpectedSymbolException;
import br.com.unibh.compiler.pasc.lexic.states.State;

import java.util.Objects;

public class NotOperatorState implements State {

    private static NotOperatorState instance;

    public static NotOperatorState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new NotOperatorState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '=' -> NotEqualsState.getInstance();
            default -> throw new UnexpectedSymbolException("MSG005", value, "=");
        };
    }

}

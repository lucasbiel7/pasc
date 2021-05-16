package br.com.unibh.compiler.pasc.lexic.states.impl;


import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PreCloseMultilineCommendState implements State {

    private static PreCloseMultilineCommendState instance;

    public static PreCloseMultilineCommendState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PreCloseMultilineCommendState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '/' -> InitialState.getInstance();
            case '*' -> this;
            default -> CommentMultilineState.getInstance();
        };
    }
}
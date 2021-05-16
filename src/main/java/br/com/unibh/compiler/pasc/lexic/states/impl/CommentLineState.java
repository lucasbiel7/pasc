package br.com.unibh.compiler.pasc.lexic.states.impl;


import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLineState implements State {

    private static CommentLineState instance;

    public static CommentLineState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CommentLineState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '\n' -> InitialState.getInstance();
            default -> this;
        };
    }
}

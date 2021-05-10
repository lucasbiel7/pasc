package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;

public class CommentMultilineState implements State {

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '*' -> new PreCloseMultilineCommendState();
            default -> this;
        };
    }
}
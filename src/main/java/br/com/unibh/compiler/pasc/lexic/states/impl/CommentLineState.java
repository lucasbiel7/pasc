package br.com.unibh.compiler.pasc.lexic.states.impl;


import br.com.unibh.compiler.pasc.lexic.states.State;

public class CommentLineState implements State {

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '\n' -> InitialState.getInstance();
            default -> this;
        };
    }
}

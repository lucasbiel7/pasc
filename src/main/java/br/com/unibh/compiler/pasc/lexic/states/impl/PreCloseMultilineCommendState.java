package br.com.unibh.compiler.pasc.lexic.states.impl;


import br.com.unibh.compiler.pasc.lexic.states.State;

public class PreCloseMultilineCommendState implements State {

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '/' -> InitialState.getInstance();
            case '*' -> this;
            default -> new CommentMultilineState();
        };
    }
}
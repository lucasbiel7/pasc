package br.com.unibh.compiler.pasc.states.impl;


import br.com.unibh.compiler.pasc.states.State;

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
package br.com.unibh.compiler.pasc.states.impl;


import br.com.unibh.compiler.pasc.states.State;

public class CommentLineState implements State {

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '\n' -> new InitialState();
            default -> this;
        };
    }
}

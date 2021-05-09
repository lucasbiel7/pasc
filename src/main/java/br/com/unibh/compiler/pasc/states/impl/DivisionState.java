package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.states.FinalState;
import br.com.unibh.compiler.pasc.states.State;

public class DivisionState implements FinalState {
    @Override
    public State nextState(char value) {
        return switch (value) {
            case '/' -> new CommentLineState();
            case '*' -> new CommentMultilineState();
            default -> FinalState.super.nextState(value);
        };
    }
}

package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.model.Operators;
import br.com.unibh.compiler.pasc.states.FinalState;
import br.com.unibh.compiler.pasc.states.State;

public class DivisionState implements FinalState {
    @Override
    public State nextState(char value) {
        return switch (value) {
            case '/' -> new CommentLineState();
            case '*' -> new CommentMultilineState();
            default -> EmptyState.getInstance();
        };
    }

    @Override
    public String name() {
        return Operators.OP_DIV.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_DIV.getValue();
    }
}

package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;

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

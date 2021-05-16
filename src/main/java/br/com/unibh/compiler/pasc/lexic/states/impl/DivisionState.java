package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DivisionState implements FinalState {
    private static DivisionState instance;

    public static final DivisionState getInstance() {
        if (instance == null) {
            instance = new DivisionState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '/' -> CommentLineState.getInstance();
            case '*' -> CommentMultilineState.getInstance();
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

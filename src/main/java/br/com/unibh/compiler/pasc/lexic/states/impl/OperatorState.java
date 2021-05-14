package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.OperatorNotFoundException;
import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;

import java.util.Arrays;

public record OperatorState(char operator) implements FinalState {

    @Override
    public String name() {
        return Arrays.stream(Operators.values())
                .filter(operators -> operators.getValue().equals(String.valueOf(operator)))
                .findFirst()
                .orElseThrow(OperatorNotFoundException::new)
                .getTokenName();
    }

    @Override
    public String value() {
        return String.valueOf(this.operator);
    }
}

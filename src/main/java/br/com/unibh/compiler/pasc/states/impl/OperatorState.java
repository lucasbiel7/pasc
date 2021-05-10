package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.exceptions.OperatorNotFoundException;
import br.com.unibh.compiler.pasc.model.Operators;
import br.com.unibh.compiler.pasc.states.FinalState;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class OperatorState implements FinalState {

    private final char operator;

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
        return String.valueOf(operator);
    }
}

package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.states.FinalState;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OperatorState implements FinalState {

    private final char operator;

}

package br.com.unibh.compiler.pasc.states;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OperatorState implements FinalState {

    private final char operator;

}

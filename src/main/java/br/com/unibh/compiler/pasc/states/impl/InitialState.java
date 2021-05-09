package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.states.State;

/**
 * Estado inicial para validar os caracteres
 *
 * @author Lucas Dutra
 * @since 04 maio 2021
 */
public class InitialState implements State {

    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new NumberState(value);
        return switch (value) {
            case ' ', '\n' -> new InitialState();
            case '/' -> new BarState();
            case '=' -> new AttributionState();
            case '+', '-', '*' -> new OperatorState(value);
            default -> throw new UnsupportedOperationException("Caracter não é valido");
        };
    }
}

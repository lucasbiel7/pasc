package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Estado inicial para validar os caracteres
 *
 * @author Lucas Dutra
 * @since 04 maio 2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InitialState implements State {

    private static InitialState instance;


    public static InitialState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new InitialState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new NumberState(value);
        return switch (value) {
            case ' ', '\n' -> InitialState.getInstance();
            case '/' -> new DivisionState();
            case '=' -> new AttributionState();
            case '+', '-', '*' -> new OperatorState(value);
            default -> throw new UnsupportedOperationException("Caracter não é valido");
        };
    }
}

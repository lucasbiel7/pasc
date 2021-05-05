package br.com.unibh.compiler.pasc.states;

import br.com.unibh.compiler.pasc.exceptions.IllegalCharacterException;

/**
 * @author Lucas Dutra
 * @since 04 maio 2021
 */
public class EqualState implements State {

    @Override
    public State nextState(char value) {
        if (value == '=') {
            return new EqualsState();
        }
        throw new IllegalCharacterException();
    }
}

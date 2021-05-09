package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.exceptions.IllegalCharacterException;
import br.com.unibh.compiler.pasc.states.FinalState;
import br.com.unibh.compiler.pasc.states.State;

/**
 * @author Lucas Dutra
 * @since 04 maio 2021
 */
public class AttributionState implements FinalState {

    @Override
    public State nextState(char value) {
        if (value == '=') {
            return new EqualsState();
        }
        //TODO verificar caso não tenha outra estado válido retornar nulo
        throw new IllegalCharacterException();
    }
}

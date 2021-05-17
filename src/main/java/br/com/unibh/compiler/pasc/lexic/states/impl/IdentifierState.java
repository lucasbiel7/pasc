package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;

public class IdentifierState implements FinalState {

    private String value;

    public IdentifierState(char value) {
        this.value = String.valueOf(value);
    }

    public IdentifierState(IdentifierState identifierState, char value) {
        this.value = identifierState.value + value;
    }


    @Override
    public State nextState(char value) {
        if (Character.isLetterOrDigit(value)) return new IdentifierState(this, value);
        return FinalState.super.nextState(value);
    }

    @Override
    public String name() {
        return KeyWorld.getKeyWorld(this.value.toLowerCase())
                .orElse(Constants.IDENTIFIER)
                .getTokenName();
    }

    @Override
    public String value() {
        return this.value;
    }
}

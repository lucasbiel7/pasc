package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.model.TokenName;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;

import java.util.Arrays;
import java.util.Optional;

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
        final Optional<TokenName> searchTokenName = Arrays.stream(KeyWorld.values())
                .filter(keyWorld -> keyWorld.getValue().equals(value))
                .map(TokenName.class::cast)
                .findFirst();
        TokenName tokenId = Constants.IDENTIFIER;
        return searchTokenName
                .orElse(tokenId)
                .getTokenName();
    }

    @Override
    public String value() {
        return this.value;
    }
}

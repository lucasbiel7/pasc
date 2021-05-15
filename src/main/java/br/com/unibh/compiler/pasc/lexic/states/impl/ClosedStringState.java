package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;

public record ClosedStringState(String value) implements FinalState {

    @Override
    public String name() {
        return Constants.CHAR_CONST.getTokenName();
    }

    @Override
    public String value() {
        return this.value;
    }
}

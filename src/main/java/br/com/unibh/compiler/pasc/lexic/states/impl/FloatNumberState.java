package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.Getter;

@Getter
public class FloatNumberState implements FinalState {

    private String value;

    public FloatNumberState(String previousNumber, char value) {
        this.value = previousNumber + value;
    }

    public FloatNumberState(FloatNumberState previousNumber, char value) {
        this.value = previousNumber.value + value;
    }


    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new FloatNumberState(this, value);
        return null;
    }

    @Override
    public String name() {
        return Constants.NUM_CONST.getTokenName();
    }

    @Override
    public String value() {
        return value;
    }
}

package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;

import java.util.Objects;

public class GreaterThanState implements FinalState {
    private static GreaterThanState instance;

    public static  GreaterThanState getInstance(){
        if(Objects.isNull(instance)){
            instance = new GreaterThanState();
        }
        return instance;
    }
    @Override
    public State nextState(char value) {
        return switch (value){
            case '=' -> GreaterThanOrEquals.getInstance();
            default -> FinalState.super.nextState(value);
        };

    }

    @Override
    public String name() {
        return Operators.OP_GT.getTokenName();
    }

    @Override
    public String value() {
        return Operators.OP_GT.getValue();
    }
}

package br.com.unibh.compiler.pasc.lexic.states;

import br.com.unibh.compiler.pasc.lexic.states.impl.InitialState;

public class ValidateStateHelperTest {

    protected State runProgram(String value) {
        final char[] chars = value.toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(chars[i]);
            i++;
        } while (i < chars.length);
        return actualState;
    }
}

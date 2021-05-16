package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste para identificar números MENOR através dos estados")
class LessThanStateTest {
    @Test
    @DisplayName("Verificando se é MENOR QUE")
    void testWhenLessThan(){
        final char[] lessWorld = "<".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(lessWorld[i]);
            i++;
        } while (i < lessWorld.length);
        assertTrue(actualState instanceof LessThanState);
    }

}
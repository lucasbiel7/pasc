package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Teste unitário para verificar se o programa consegue alcançar o estado de MENOR IGUAL")
class LessThanOrEqualsTest {
    @Test
    @DisplayName("Verificando se valida MENOR IGUAL")
    void testWhenHasEqualsState() {
        final char[] lessEqualWorld = "<=>".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(lessEqualWorld[i]);
            i++;
        } while (i < lessEqualWorld.length);

        assertTrue(actualState instanceof LessThanOrEquals);
    }

}
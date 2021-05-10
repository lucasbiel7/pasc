package br.com.unibh.compiler.pasc.lexic.states.impl;


import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste -Unitário para verificação se o programa consegue chegar no estrado de atribuição ")
class AttributionStateTest {
    @Test
    @DisplayName("Teste de atribuição")
    void testWhenHasAttributionState() {
        final char[] equalsWorld = "=".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(equalsWorld[i]);
            i++;
        } while (i < equalsWorld.length);
        assertTrue(actualState instanceof AttributionState);
    }

}
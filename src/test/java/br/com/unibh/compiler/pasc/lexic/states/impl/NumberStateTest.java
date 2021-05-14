package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Teste para identificar números através dos estados")
class NumberStateTest {

    @Test
    @DisplayName("Encontrando um número e verificando seu valor")
    void testWhenFindANumber() {
        final char[] numberWorld = "48643".toCharArray();
        int i = 0;
        State actualState = InitialState.getInstance();
        do {
            actualState = actualState.nextState(numberWorld[i++]);
        } while (i < numberWorld.length);
        assertFalse(actualState instanceof InitialState);
        assertTrue(actualState instanceof NumberState);
        NumberState numberState = (NumberState) actualState;
        assertNotEquals("8643", numberState.getValue());
        assertEquals("48643", numberState.getValue());
    }
}
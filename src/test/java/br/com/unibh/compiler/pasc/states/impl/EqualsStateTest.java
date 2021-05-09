package br.com.unibh.compiler.pasc.states.impl;

import br.com.unibh.compiler.pasc.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste unitário para verificar se o programa consegue alcançar o estado e igualdade")
class EqualsStateTest {

    @Test
    @DisplayName("Verificando se valida igualdade")
    void testWhenHasEqualsState() {
        final char[] equalsWorld = "==".toCharArray();
        State actualState = new InitialState();
        int i = 0;
        do {
            actualState = actualState.nextState(equalsWorld[i]);
            i++;
        } while (i < equalsWorld.length);

        assertTrue(actualState instanceof EqualsState);
    }

}
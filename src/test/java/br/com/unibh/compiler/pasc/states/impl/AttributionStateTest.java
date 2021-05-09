package br.com.unibh.compiler.pasc.states.impl;


import br.com.unibh.compiler.pasc.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Teste -Unitário para verificação se o programa consegue chegar no estrado de atribuição ")
class AttributionStateTest {
    @Test
    @DisplayName("Teste de atribuição")
    void testWhenHasAttributionState(){
        final  char [] equalsWorld = "=".toCharArray();
        State actualState = new InitialState();
        int i = 0;
        do{
            actualState = actualState.nextState(equalsWorld[i]);
            i++;
        }while (i < equalsWorld.length);
        assertTrue(actualState instanceof AttributionState);
    }

}
package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Teste para identificar números MAIOR através dos estados")
class GreaterThanStateTest {
    @Test
    @DisplayName("Verificando se é MAIOR QUE")
    void testWhenBiggerThan(){
        final char[] biggerWorld = ">".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(biggerWorld[i]);
            i++;
        } while (i < biggerWorld.length);
        assertTrue(actualState instanceof GreaterThanState);
    }
}
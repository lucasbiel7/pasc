package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste para identificar números MAIOR IGUAL através dos estados")
class GreaterThanOrEqualsTest {
    @Test
    @DisplayName("Verificando se é MAIOR IGUAL QUE")
    void testWhenGreaterThanOrEqual(){
        final char[] biggerOrEqualWorld = ">=".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(biggerOrEqualWorld[i]);
            i++;
        } while (i < biggerOrEqualWorld.length);
        assertTrue(actualState instanceof GreaterThanOrEquals);
    }

}
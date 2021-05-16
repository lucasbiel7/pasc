package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import lombok.experimental.Helper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste para identificar números MAIOR IGUAL através dos estados")
class GreaterThanOrEqualsTest extends ValidateStateHelperTest {
    @Test
    @DisplayName("Verificando se é MAIOR IGUAL QUE")
    void testWhenGreaterThanOrEqual(){
        State state = runProgram(">=");
        assertTrue(state instanceof GreaterThanOrEqualsState);
    }

}
package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste para identificar números MAIOR IGUAL através dos estados")
class GreaterThanOrEqualsTest extends ValidateStateHelperTest {
    @Test
    @DisplayName("Verificando se é MAIOR IGUAL QUE")
    void testWhenGreaterThanOrEqual() {
        State state = runProgram(">=");
        assertTrue(state instanceof GreaterThanOrEqualsState);
        if (state instanceof GreaterThanOrEqualsState greaterThanOrEqualsState) {
            assertEquals(Operators.OP_GT.getValue(), greaterThanOrEqualsState.value());
            assertEquals(Operators.OP_GT.getTokenName(), greaterThanOrEqualsState.name());
        }
    }

}
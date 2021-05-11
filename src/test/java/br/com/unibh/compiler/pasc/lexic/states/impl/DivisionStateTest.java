package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste para validar quando aparece um operador de divisão")
class DivisionStateTest extends ValidateStateHelperTest {

    @DisplayName("Teste do operador de divisão")
    @Test
    void testWhenFindADivisionOperator() {
        final State state = assertDoesNotThrow(() -> runProgram("/"));
        assertTrue(state instanceof FinalState);
        assertTrue(state instanceof DivisionState);
        if (state instanceof FinalState finalState) {
            assertEquals("/", finalState.value());
            assertEquals(Operators.OP_DIV.getTokenName(), finalState.name());
        }
    }
}
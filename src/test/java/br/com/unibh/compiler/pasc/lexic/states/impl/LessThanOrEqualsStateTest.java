package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste para menor e menor ou igual")
class LessThanOrEqualsStateTest extends ValidateStateHelperTest {


    @Test
    @DisplayName("Teste de menor")
    void testWhenUseLessThan() {
        final State state = runProgram("<");
        assertTrue(state instanceof FinalState);
        if (state instanceof FinalState finalState) {
            assertEquals(Operators.OP_LT.getValue(), finalState.value());
            assertEquals(Operators.OP_LT.getTokenName(), finalState.name());
        }
        assertTrue(state instanceof LessThanState);

    }

    @Test
    @DisplayName("Teste de menor ou igual")
    void testWhenUseLessThanOrEquals() {
        final State state = runProgram("<=");
        assertTrue(state instanceof FinalState);
        if (state instanceof FinalState finalState) {
            assertEquals(Operators.OP_LE.getValue(), finalState.value());
            assertEquals(Operators.OP_LE.getTokenName(), finalState.name());
        }
        assertTrue(state instanceof LessThanOrEqualsState);

    }
}
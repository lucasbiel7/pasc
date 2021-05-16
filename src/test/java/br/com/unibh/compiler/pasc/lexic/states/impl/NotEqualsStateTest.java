package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.UnexpectedSymbolException;
import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste para validar operador diferente")
class NotEqualsStateTest extends ValidateStateHelperTest {

    @Test
    @DisplayName("Teste operador diferente")
    void testWhenUseDifferentOperator() {
        final State state = runProgram("!=");
        assertTrue(state instanceof NotEqualsState);
        if (state instanceof NotEqualsState notEqualsState) {
            assertEquals(Operators.OP_NE.getTokenName(), notEqualsState.name());
            assertEquals(Operators.OP_NE.getValue(), notEqualsState.value());
        }
    }

    @Test
    @DisplayName("Teste operador diferente")
    void testWhenUseDifferentOperatorWithWrongChar() {
        assertThrows(UnexpectedSymbolException.class, () -> runProgram("!@="));
    }
}
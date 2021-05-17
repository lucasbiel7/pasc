package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste de operadores")
class OperatorStateTest extends ValidateStateHelperTest {

    @Test
    @DisplayName("Teste operadores de soma")
    void testWhenUserOperatorPlus() {
        final State state = runProgram("+");
        assertTrue(state instanceof OperatorState);
        if (state instanceof OperatorState operatorState) {
            assertEquals(Operators.OP_AD.getTokenName(), operatorState.name());
            assertEquals(Operators.OP_AD.getValue(), operatorState.value());
        }
    }

    @Test
    @DisplayName("Teste operadores de subtração")
    void testWhenUserOperatorMinus() {
        final State state = runProgram("-");
        assertTrue(state instanceof OperatorState);
        if (state instanceof OperatorState operatorState) {
            assertEquals(Operators.OP_MIN.getTokenName(), operatorState.name());
            assertEquals(Operators.OP_MIN.getValue(), operatorState.value());
        }
    }

    @Test
    @DisplayName("Teste operadores de multiplicação")
    void testWhenUserOperatorMulti() {
        final State state = runProgram("*");
        assertTrue(state instanceof OperatorState);
        if (state instanceof OperatorState operatorState) {
            assertEquals(Operators.OP_MUL.getTokenName(), operatorState.name());
            assertEquals(Operators.OP_MUL.getValue(), operatorState.value());
        }
    }


}
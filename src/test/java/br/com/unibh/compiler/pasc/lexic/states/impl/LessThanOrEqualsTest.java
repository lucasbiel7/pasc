package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Teste unitário para verificar se o programa consegue alcançar o estado de MENOR IGUAL")
class LessThanOrEqualsTest extends ValidateStateHelperTest {
    @Test
    @DisplayName("Verificando se valida MENOR IGUAL")
    void testWhenHasEqualsState() {
        State state = runProgram("<=");
        assertTrue(state instanceof LessThanOrEqualsState);
    }

}
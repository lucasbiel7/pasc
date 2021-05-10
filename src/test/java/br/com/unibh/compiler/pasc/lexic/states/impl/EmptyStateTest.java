package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Teste para estado vazios")
class EmptyStateTest extends ValidateStateHelperTest {

    @Test
    @DisplayName("Quando tentar encontrar o proximo estado através de um " +
            "estado vazio deve ocorrer um eror, porque ele é apenas de marcação")
    void testWhenNextStateOnEmpty() {
        final UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class, () -> runProgram("123aa"));
        assertEquals("Do nothing", unsupportedOperationException.getMessage());
    }
}
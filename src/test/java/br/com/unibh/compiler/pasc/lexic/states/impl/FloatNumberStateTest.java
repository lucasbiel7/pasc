package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.IllegalCharacterException;
import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste para identificar números flutuantes através dos estados")
class FloatNumberStateTest extends ValidateStateHelperTest {

    @Test
    @DisplayName("Encontrando números decimal e verificando seus valores")
    void testWhenFindNumberFloat() {
        State actualState = runProgram("4587.25");
        assertFalse(actualState instanceof InitialState);
        assertTrue(actualState instanceof FloatNumberState);
        FloatNumberState numberStateFloat = (FloatNumberState) actualState;
        assertNotEquals("587.25", numberStateFloat.value());
        assertEquals("4587.25", numberStateFloat.value());
        assertEquals(Constants.NUM_CONST.getTokenName(), numberStateFloat.name());
    }

    @Test
    @DisplayName("Verificar se existe pelo menos um número depois do . para torna-lo flutuante")
    void testWhenNumberAfterThePeriod() {
        State actualState = runProgram("4587.");
        assertFalse(actualState instanceof FinalState);
    }

    @Test
    @DisplayName("Verificar se existe símbolo inválido após o ponto")
    void testWhenSymbolAfterThePeriod() {
        assertThrows(IllegalCharacterException.class, () -> runProgram("4587.u"));
    }
}

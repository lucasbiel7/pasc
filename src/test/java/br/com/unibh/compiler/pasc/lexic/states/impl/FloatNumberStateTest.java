package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.IllegalCharacterException;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste para identificar números flutuantes através dos estados")
class FloatNumberStateTest {
    @Test
    @DisplayName("Encontrando números decimal e verificando seus valores")
    void testWhenFindNumberFloat() {
        final char[] numberFloatWorld = "4587.25".toCharArray();
        int i = 0;
        State actualState = InitialState.getInstance();
        do {
            actualState = actualState.nextState(numberFloatWorld[i]);
            i++;
        } while (i < numberFloatWorld.length);
        assertFalse(actualState instanceof InitialState);
        assertTrue(actualState instanceof FloatNumberState);
        FloatNumberState numberStateFloat = (FloatNumberState) actualState;
        assertNotEquals("587.25", numberStateFloat.getValue());
        assertEquals("4587.25", numberStateFloat.getValue());
    }

    @Test
    @DisplayName("Verificar se existe pelo menos um númeor depois do . para torna-lo flutuante")
    void testWhenNumberAfterThePeriod() {
        final char[] numberFloatWorld = "4587.".toCharArray();
        int i = 0;
        State actualState = InitialState.getInstance();
        do {
            actualState = actualState.nextState(numberFloatWorld[i]);
            i++;
        } while (i < numberFloatWorld.length);
        assertFalse(actualState instanceof FinalState);
    }

    @Test
    @DisplayName("Verificar se existe símbolo inválido após o ponto")
    void testWhenSymbolAfterThePeriod() {
        final char[] numberFloatWorld = "4587.u".toCharArray();
        assertThrows(IllegalCharacterException.class, () -> {
            int i = 0;
            State actualState = InitialState.getInstance();
            do {
                actualState = actualState.nextState(numberFloatWorld[i]);
                i++;
            } while (i < numberFloatWorld.length);
        });
    }
}

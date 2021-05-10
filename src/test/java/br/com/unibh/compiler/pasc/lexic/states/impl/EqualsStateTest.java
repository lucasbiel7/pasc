package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste unitário para verificar se o programa consegue alcançar o estado e igualdade")
class EqualsStateTest {

    @Test
    @DisplayName("Verificando se valida igualdade")
    void testWhenHasEqualsState() {
        final char[] equalsWorld = "==".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(equalsWorld[i]);
            i++;
        } while (i < equalsWorld.length);

        assertTrue(actualState instanceof EqualsState);
    }

    @Test
    @DisplayName("Verificando se valida igualdade com espaço anterior")
    void testWhenHasEqualsStateWithSpace() {
        final char[] equalsWorld = "  ==".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(equalsWorld[i]);
            i++;
        } while (i < equalsWorld.length);

        assertTrue(actualState instanceof EqualsState);
    }

    @Test
    @DisplayName("Verificando se valida igualdade com quebra de linha")
    void testWhenHasEqualsStateWithLineBreak() {
        final char[] equalsWorld = "\n==".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(equalsWorld[i]);
            i++;
        } while (i < equalsWorld.length);

        assertTrue(actualState instanceof EqualsState);
    }

    @Test
    @DisplayName("Verificando se valida igualdade com quebra de linha")
    void testWhenHasEqualsStateButThrowsException() {
        final char[] equalsWorld = "\n===".toCharArray();
        State actualState = InitialState.getInstance();
        int i = 0;
        do {
            actualState = actualState.nextState(equalsWorld[i]);
            i++;
        } while (i < 3);
        final State errorState = actualState;
        assertTrue(actualState instanceof EqualsState);
        assertTrue(errorState.nextState(equalsWorld[3]) instanceof EmptyState);
    }

}
package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testando quando aparecer um identificador")
class IdentifierStateTest extends ValidateStateHelperTest {
    @DisplayName("Teste Identificador de Palavra Reservada")
    @Test
    void testWhenFindIdentifierKW() {
        State state = runProgram("if");
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals("if", identifierState.value());
            assertEquals(KeyWorld.IF.getTokenName(), identifierState.name());
        }
    }

    @DisplayName("Teste Identificador de palavra")
    @Test
    void testWhenFindIdentifier() {
        State state = runProgram("var");
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals("var", identifierState.value());
            assertEquals("ID", identifierState.name());
        }
    }

    @DisplayName("Testando todas as palavras reservadas")
    @ParameterizedTest(name = "keyword")
    @EnumSource(value = KeyWorld.class, names = {
            "PROGRAM",
            "IF",
            "ELSE",
            "WHILE",
            "WRITE",
            "READ",
            "NUM",
            "CHAR",
            "NOT",
            "OR",
            "AND"
    })
    void testWhenUseAllKeyWorlds(KeyWorld keyWorld) {
        State state = runProgram(keyWorld.getValue());
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals(keyWorld.getValue(), identifierState.value());
            assertEquals(keyWorld.getTokenName(), identifierState.name());
        }
    }

    @DisplayName("Testando todas as palavras reservadas")
    @ParameterizedTest(name = "keyword")
    @EnumSource(value = KeyWorld.class, names = {
            "PROGRAM",
            "IF",
            "ELSE",
            "WHILE",
            "WRITE",
            "READ",
            "NUM",
            "CHAR",
            "NOT",
            "OR",
            "AND"
    })
    void testWhenUseAllKeyWorldsUpperCase(KeyWorld keyWorld) {
        State state = runProgram(keyWorld.getValue().toUpperCase(Locale.ROOT));
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals(keyWorld.getValue().toUpperCase(Locale.ROOT), identifierState.value());
            assertEquals(keyWorld.getTokenName(), identifierState.name());
        }
    }

    @DisplayName("Teste Identificador de palavra com numeros")
    @Test
    void testWhenFindIdentifierWithNumber() {
        State state = runProgram("var1");
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals("var1", identifierState.value());
            assertEquals("ID", identifierState.name());
        }
    }

    @DisplayName("Teste Identificador de palavra com símbolos ")
    @Test
    void testWhenFindIdentifierWithSymbol() {
        assertThrows(UnsupportedOperationException.class, () -> runProgram("var@v"));
    }
}
package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.model.Identifier;
import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Testando quando aparecer um identificador")
class IdentifierStateTest extends ValidateStateHelperTest {
    @DisplayName("Teste Identificador de Palavra Reservada")
    @Test
    void testWhenFindIdentifierKW(){
        State state = runProgram("if");
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals("if", identifierState.value());
            assertEquals("KW", identifierState.name());
        }
    }
    @DisplayName("Teste Identificador de palavra")
    @Test
    void testWhenFindIdentifier(){
        State state = runProgram("var");
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals("var", identifierState.value());
            assertEquals("ID", identifierState.name());
        }
    }
    @DisplayName("Teste Identificador de palavra com numeros")
    @Test
    void testWhenFindIdentifierWithNumber(){
        State state = runProgram("var1");
        assertTrue(state instanceof IdentifierState);
        if (state instanceof IdentifierState identifierState) {
            assertEquals("var1", identifierState.value());
            assertEquals("ID", identifierState.name());
        }
    }

    @DisplayName("Teste Identificador de palavra com símbolos ")
    @Test
    void testWhenFindIdentifierWithSymbol(){
        assertThrows(UnsupportedOperationException.class,() -> runProgram("var@v"));
    }
}
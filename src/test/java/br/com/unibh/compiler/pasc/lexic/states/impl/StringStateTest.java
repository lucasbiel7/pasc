package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringStateTest extends ValidateStateHelperTest {
    @DisplayName("Teste lendo uma String")
    @Test
    void testWhenReadStringDoubleQuotes(){
        State state = runProgram("\"Teste de Mensagem\"");
        assertTrue(state instanceof ClosedStringState);
        if(state instanceof ClosedStringState closedStringState){
            assertEquals("Teste de Mensagem", closedStringState.value());
            assertEquals("CHAR_CONST", closedStringState.name());
        }
    }
    @DisplayName("Teste lendo uma String")
    @Test
    void testWhenReadStringSingleQuotes(){
        State state = runProgram("'Teste de Mensagem'");
        assertTrue(state instanceof ClosedStringState);
        if(state instanceof ClosedStringState closedStringState){
            assertEquals("Teste de Mensagem", closedStringState.value());
            assertEquals("CHAR_CONST", closedStringState.name());
        }
    }
//    @DisplayName("Teste lendo uma String simples sem fechar \'")
//    @Test
//    void testWhenFindIdentifierWithSymbol(){
//        assertThrows(UnsupportedOperationException.class,() -> runProgram("'Palavra"));
//
//    }

}
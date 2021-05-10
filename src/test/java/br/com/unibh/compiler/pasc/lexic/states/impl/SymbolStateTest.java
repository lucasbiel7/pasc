package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.ValidateStateHelperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Teste para verificar se está encontrando todos os simbolos em um texto")
class SymbolStateTest extends ValidateStateHelperTest {

    @Test
    @DisplayName("Validando se ele identifica uma abertura de chaves")
    void testWhenOpenBraces() {
        State state = runProgram("{");
        assertTrue(state instanceof SymbolState);
        if (state instanceof SymbolState symbolState) {
            assertEquals(Symbols.SMB_OBC.getValue(), symbolState.value());
            assertEquals(Symbols.SMB_OBC.getTokenName(), symbolState.name());
        }
    }

    @Test
    @DisplayName("Validando se ele identifica um fechamento de chaves")
    void testWhenCloseBraces() {
        State state = runProgram("}");
        assertTrue(state instanceof SymbolState);
        if (state instanceof SymbolState symbolState) {
            assertEquals(Symbols.SMB_CBC.getValue(), symbolState.value());
            assertEquals(Symbols.SMB_CBC.getTokenName(), symbolState.name());
        }
    }

    @Test
    @DisplayName("Validando se ele identifica uma abertura de parentese")
    void testWhenOpenParentheses() {
        State state = runProgram("(");
        assertTrue(state instanceof SymbolState);
        if (state instanceof SymbolState symbolState) {
            assertEquals(Symbols.SMB_OPA.getValue(), symbolState.value());
            assertEquals(Symbols.SMB_OPA.getTokenName(), symbolState.name());
        }
    }

    @Test
    @DisplayName("Validando se ele identifica um fechamento de parentese")
    void testWhenCloseParentheses() {
        State state = runProgram(")");
        assertTrue(state instanceof SymbolState);
        if (state instanceof SymbolState symbolState) {
            assertEquals(Symbols.SMB_CPA.getValue(), symbolState.value());
            assertEquals(Symbols.SMB_CPA.getTokenName(), symbolState.name());
        }
    }

    @Test
    @DisplayName("Validando se ele identifica uma virgula")
    void testWhenFindComma() {
        State state = runProgram(",");
        assertTrue(state instanceof SymbolState);
        if (state instanceof SymbolState symbolState) {
            assertEquals(Symbols.SMB_COM.getValue(), symbolState.value());
            assertEquals(Symbols.SMB_COM.getTokenName(), symbolState.name());
        }
    }

    @Test
    @DisplayName("Validando se ele identifica um ponto e virgula")
    void testWhenFindSemicolon() {
        State state = runProgram(";");
        assertTrue(state instanceof SymbolState);
        if (state instanceof SymbolState symbolState) {
            assertEquals(Symbols.SMB_SEM.getValue(), symbolState.value());
            assertEquals(Symbols.SMB_SEM.getTokenName(), symbolState.name());
        }
    }

}
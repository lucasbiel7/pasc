package br.com.unibh.compiler.pasc.lexic.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Criação de testes para validar constantes como string numero e nome de identificadores")
class ConstantsTest {

    @Test
    @DisplayName("Ao pegar valor deve soltar uma exceção de operação não suportada")
    void testeWhenGetValueOfConstantThrowsAndException() {
        assertThrows(UnsupportedOperationException.class, () -> Constants.CHAR_CONST.getValue());
    }
}
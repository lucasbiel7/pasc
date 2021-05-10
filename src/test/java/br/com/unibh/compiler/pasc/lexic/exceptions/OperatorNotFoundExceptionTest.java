package br.com.unibh.compiler.pasc.lexic.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testando exceção de operadores não encontrados")
class OperatorNotFoundExceptionTest {

    @Test
    @DisplayName("Testando se a mensagem está sendo atribuida corretamente")
    void testWhenPassMessageToException() {
        final OperatorNotFoundException operatorNotFoundException = new OperatorNotFoundException();
        assertEquals("Operador não encontrado!", operatorNotFoundException.getMessage());
    }
}
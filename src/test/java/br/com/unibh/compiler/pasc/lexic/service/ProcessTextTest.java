package br.com.unibh.compiler.pasc.lexic.service;

import br.com.unibh.compiler.pasc.lexic.configuration.FileConfig;
import br.com.unibh.compiler.pasc.lexic.configuration.PanicModeConfig;
import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.model.TokeError;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.model.TokenName;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Processamento de códigos nos recursos")
class ProcessTextTest {

    private ProcessText processText;

    @BeforeEach
    void setup() {
        processText = new ProcessText();
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste um programa que está nos recursos")
    void testWhenAProgramaOnResource() {
        try (final InputStream resource = getResource("program1.pasc")) {
            processText.process(resource);
            final List<Token> tokens = processText.getTokens();
            assertEquals(4, tokens.size());
            final Token first = tokens.get(0);
            final Token second = tokens.get(1);
            //TODO melhorar os testes com os privates metodos criados
            assertAll(
                    () -> assertNotNull(first),
                    () -> assertEquals("123", first.getValue()),
                    () -> assertEquals(Constants.NUM_CONST.getTokenName(), first.getName()),
                    () -> assertEquals(1, first.getLine()),
                    () -> assertEquals(1, first.getColumn()),
                    () -> assertEquals(Operators.OP_EQ.getTokenName(), second.getName()),
                    () -> assertEquals(Operators.OP_EQ.getValue(), second.getValue()),
                    () -> assertEquals(1, second.getLine()),
                    () -> assertEquals(5, second.getColumn())
            );
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste um programa que está nos recursos")
    void testWhenAProgramaOnResourceWithCommentLine() {
        try (final InputStream resource = getResource("program2.pasc")) {
            processText.process(resource);
            final List<Token> tokens = processText.getTokens();
            assertEquals(4, tokens.size());
            final Token first = tokens.get(0);
            assertNotNull(first);
            assertEquals("123", first.getValue());
            assertEquals(Constants.NUM_CONST.getTokenName(), first.getName());
            assertEquals(2, first.getLine());
            assertEquals(1, first.getColumn());
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Testando o panic mode configurado")
    void testWhenProgramFailsButPanicSaveTheState() {
        try (final InputStream resource = getResource("program3.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(11, tokens.size());
            // Assert tokens
            assertAll(
                    () -> assertToken(tokens.get(0), 2, 1, "if", KeyWorld.IF.getTokenName()),
                    () -> assertToken(tokens.get(1), 2, 4, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(2), 2, 9, "<", Operators.OP_LT.getTokenName()),
                    () -> assertToken(tokens.get(3), 2, 11, "v", Constants.IDENTIFIER.getTokenName()),
                    () -> assertTokenError(tokens.get(4), 2, 12, PanicModeConfig.TOKEN_ERROR_NAME),
                    () -> assertToken(tokens.get(5), 2, 13, "r2", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(6), 2, 16, "{", Symbols.SMB_OBC.getTokenName()),
                    () -> assertToken(tokens.get(7), 3, 4, "write", KeyWorld.WRITE.getTokenName()),
                    () -> assertToken(tokens.get(8), 3, 10, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(9), 4, 1, "}", Symbols.SMB_CBC.getTokenName()),
                    () -> assertToken(tokens.get(10), 4, 2, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Verificando se o programa para a execução com a quantidade de erros configurada no panic mode")
    void testWhenPanicRetryExceeded() {
        try (final InputStream resource = getResource("program4.pasc")) {
            assertThrows(RuntimeException.class, () -> processText.process(resource));
            assertEquals(PanicModeConfig.RETRY, processText.getTokens().size());
        }
    }

    private void assertToken(final Token assertToken, int line, int column, String value, String tokenName) {
        assertAll(
                () -> assertNotNull(assertToken),
                () -> assertEquals(line, assertToken.getLine()),
                () -> assertEquals(column, assertToken.getColumn()),
                () -> assertEquals(value, assertToken.getValue()),
                () -> assertEquals(tokenName, assertToken.getName())
        );
    }

    private void assertToken(final Token assertToken, int line, int column, String value, TokenName tokenName) {
        assertAll(
                () -> assertNotNull(assertToken),
                () -> assertEquals(line, assertToken.getLine()),
                () -> assertEquals(column, assertToken.getColumn()),
                () -> assertEquals(value, assertToken.getValue()),
                () -> assertEquals(tokenName.getTokenName(), assertToken.getName())
        );
    }

    private void assertTokenError(final Token assertToken, int line, int column, String tokenName) {
        assertAll(
                () -> assertNotNull(assertToken),
                () -> assertEquals(line, assertToken.getLine()),
                () -> assertEquals(column, assertToken.getColumn()),
                () -> assertEquals(tokenName, assertToken.getName()),
                () -> assertTrue(assertToken instanceof TokeError)
        );
    }

    private void assertTokenError(final Token assertToken, int line, int column, TokenName tokenEnum) {
        assertAll(
                () -> assertNotNull(assertToken),
                () -> assertEquals(line, assertToken.getLine()),
                () -> assertEquals(column, assertToken.getColumn()),
                () -> assertEquals(tokenEnum.getTokenName(), assertToken.getName()),
                () -> assertTrue(assertToken instanceof TokeError)
        );
    }

    private InputStream getResource(String name) throws URISyntaxException, IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(name);
        Path path = Path.of(resource.toURI());
        return Files.newInputStream(path);
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste um programa que está nos recursos")
    void testWhenProgramNotEqualsOperator() {
        try (final InputStream resource = getResource("program5.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(4, tokens.size());
            assertAll(
                    () -> assertToken(tokens.get(0), 4, 1, "5", Constants.NUM_CONST.getTokenName()),
                    () -> assertToken(tokens.get(1), 4, 2, "!=", Operators.OP_NE.getTokenName()),
                    () -> assertToken(tokens.get(2), 4, 4, "4", Constants.NUM_CONST.getTokenName()),
                    () -> assertToken(tokens.get(3), 4, 64, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );

        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste um programa Operações entre dois números")
    void testWhenProgramMultOperators() {
        try (final InputStream resource = getResource("program7.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(37, tokens.size());
            assertAll(
                    () -> assertToken(tokens.get(0), 2, 1, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(1), 2, 6, "=", Operators.OP_ATRIB.getTokenName()),
                    () -> assertToken(tokens.get(2), 2, 8, "10", Constants.NUM_CONST.getTokenName()),
                    () -> assertToken(tokens.get(3), 3, 1, "var2", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(4), 3, 6, "=", Operators.OP_ATRIB.getTokenName()),
                    () -> assertToken(tokens.get(5), 3, 8, "20", Constants.NUM_CONST.getTokenName()),
                    () -> assertToken(tokens.get(6), 4, 1, "soma", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(7), 4, 6, "=", Operators.OP_ATRIB.getTokenName()),
                    () -> assertToken(tokens.get(8), 4, 8, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(9), 4, 12, "+", Operators.OP_AD.getTokenName()),
                    () -> assertToken(tokens.get(10), 4, 13, "var2", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(11), 5, 1, "subtracao", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(12), 5, 11, "=", Operators.OP_ATRIB.getTokenName()),
                    () -> assertToken(tokens.get(13), 5, 13, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(14), 5, 17, "-", Operators.OP_MIN.getTokenName()),
                    () -> assertToken(tokens.get(15), 5, 18, "var2", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(16), 6, 1, "multiplicacao", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(17), 6, 15, "=", Operators.OP_ATRIB.getTokenName()),
                    () -> assertToken(tokens.get(18), 6, 17, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(19), 6, 21, "*", Operators.OP_MUL.getTokenName()),
                    () -> assertToken(tokens.get(20), 6, 22, "var2", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(21), 7, 1, "divisao", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(22), 7, 9, "=", Operators.OP_ATRIB.getTokenName()),
                    () -> assertToken(tokens.get(23), 7, 11, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(24), 7, 15, "/", Operators.OP_DIV.getTokenName()),
                    () -> assertToken(tokens.get(25), 7, 16, "var2", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(26), 8, 1, "if", KeyWorld.IF.getTokenName()),
                    () -> assertToken(tokens.get(27), 8, 3, "(", Symbols.SMB_OPA.getTokenName()),
                    () -> assertToken(tokens.get(28), 8, 4, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(29), 8, 8, ">", Operators.OP_GT.getTokenName()),
                    () -> assertToken(tokens.get(30), 8, 9, "var2", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(31), 8, 13, ")", Symbols.SMB_CPA.getTokenName()),
                    () -> assertToken(tokens.get(32), 8, 14, "{", Symbols.SMB_OBC.getTokenName()),
                    () -> assertToken(tokens.get(33), 9, 5, "write", KeyWorld.WRITE.getTokenName()),
                    () -> assertToken(tokens.get(34), 9, 11, "VAR1 EH MAIOR QUE VAR2", Constants.CHAR_CONST.getTokenName()),
                    () -> assertToken(tokens.get(35), 10, 1, "}", Symbols.SMB_CBC.getTokenName()),
                    () -> assertToken(tokens.get(36), 10, 51, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );


        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste um programa que está nos recursos")
    void testWhenUnclosedString() {
        try (final InputStream resource = getResource("program6.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(2, tokens.size());
            assertAll(
                    () -> assertToken(tokens.get(0), 1, 31, "A String deve ser fechada com \"", PanicModeConfig.TOKEN_ERROR_NAME),
                    () -> assertToken(tokens.get(1), 1, 31, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste um programa que irá imprimir o maior número")
    void testWhenCompileAGreaterNumberProgram() {
        try (final InputStream resource = getResource("program8.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(16, tokens.size());
            assertAll(
                    () -> assertToken(tokens.get(0), 1, 1, "if", KeyWorld.IF.getTokenName()),
                    () -> assertToken(tokens.get(1), 1, 3, "(", Symbols.SMB_OPA.getTokenName()),
                    () -> assertToken(tokens.get(2), 1, 4, "a", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(3), 1, 6, ">=", Operators.OP_GE.getTokenName()),
                    () -> assertToken(tokens.get(4), 1, 9, "b", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(5), 1, 10, ")", Symbols.SMB_CPA.getTokenName()),
                    () -> assertToken(tokens.get(6), 1, 11, "{", Symbols.SMB_OBC),

                    () -> assertToken(tokens.get(7), 2, 5, "write", KeyWorld.WRITE),
                    () -> assertToken(tokens.get(8), 2, 11, "a", Constants.IDENTIFIER),

                    () -> assertToken(tokens.get(9), 3, 1, "}", Symbols.SMB_CBC),
                    () -> assertToken(tokens.get(10), 3, 2, "else", KeyWorld.ELSE),
                    () -> assertToken(tokens.get(11), 3, 6, "{", Symbols.SMB_OBC),

                    () -> assertToken(tokens.get(12), 4, 5, "write", KeyWorld.WRITE),
                    () -> assertToken(tokens.get(13), 4, 11, "b", Constants.IDENTIFIER),

                    () -> assertToken(tokens.get(14), 5, 1, "}", Symbols.SMB_CBC),


                    () -> assertToken(tokens.get(15), 5, 2, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste um programa que validar caracteres que não são ASCII na string")
    void testWhenCompileANonAsciiCharacter() {
        try (final InputStream resource = getResource("program9.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(4, tokens.size());
            assertAll(
                    () -> assertTokenError(tokens.get(0), 1, 2, PanicModeConfig.TOKEN_ERROR_NAME),
                    () -> assertTokenError(tokens.get(1), 1, 3, PanicModeConfig.TOKEN_ERROR_NAME),
                    () -> assertTokenError(tokens.get(2), 1, 4, PanicModeConfig.TOKEN_ERROR_NAME),
                    () -> assertToken(tokens.get(3), 1, 4, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Teste para validar aceitação de palavras reservas e identificadores tao em maiusculo quanto minusculo")
    void testWhenUseCapsWorlds() {
        try (final InputStream resource = getResource("program10.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(7, tokens.size());
            assertAll(
                    () -> assertToken(tokens.get(0), 1, 1, "IF", KeyWorld.IF),
                    () -> assertToken(tokens.get(1), 1, 4, "(", Symbols.SMB_OPA),
                    () -> assertToken(tokens.get(2), 1, 5, "A", Constants.IDENTIFIER),
                    () -> assertToken(tokens.get(3), 1, 7, ">", Operators.OP_GT),
                    () -> assertToken(tokens.get(4), 1, 9, "a", Constants.IDENTIFIER),
                    () -> assertToken(tokens.get(5), 1, 10, ")", Symbols.SMB_CPA),
                    () -> assertToken(tokens.get(6), 1, 11, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Testando operador and dentro de uma condicional")
    void testWhenUseAndWithIfOperator() {
        try (final InputStream resource = getResource("program11.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(15, tokens.size());
            assertAll(
                    () -> assertToken(tokens.get(0), 1, 1, "if", KeyWorld.IF),
                    () -> assertToken(tokens.get(1), 1, 3, "(", Symbols.SMB_OPA),
                    () -> assertToken(tokens.get(2), 1, 4, "a", Constants.IDENTIFIER),
                    () -> assertToken(tokens.get(3), 1, 5, ">", Operators.OP_GT),
                    () -> assertToken(tokens.get(4), 1, 6, "1", Constants.NUM_CONST),
                    () -> assertToken(tokens.get(5), 1, 8, "and", KeyWorld.AND),
                    () -> assertToken(tokens.get(6), 1, 12, "b", Constants.IDENTIFIER),
                    () -> assertToken(tokens.get(7), 1, 13, "<", Operators.OP_LT),
                    () -> assertToken(tokens.get(8), 1, 14, "2", Constants.NUM_CONST),
                    () -> assertToken(tokens.get(9), 1, 15, ")", Symbols.SMB_CPA),
                    () -> assertToken(tokens.get(10), 1, 16, "{", Symbols.SMB_OBC),

                    () -> assertToken(tokens.get(11), 2, 4, "write", KeyWorld.WRITE),
                    () -> assertToken(tokens.get(12), 2, 10, "a", Constants.IDENTIFIER),

                    () -> assertToken(tokens.get(13), 3, 1, "}", Symbols.SMB_CBC),

                    () -> assertToken(tokens.get(14), 3, 2, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );
        }
    }

    @SneakyThrows
    @Test
    @DisplayName("Testando atribuição de número com ponto flutuante")
    void testWhenAttrSomeFloatVariable() {
        try (final InputStream resource = getResource("program12.pasc")) {
            assertDoesNotThrow(() -> processText.process(resource));
            final List<Token> tokens = assertDoesNotThrow(() -> processText.getTokens());
            assertNotNull(tokens);
            assertFalse(tokens.isEmpty());
            assertEquals(5, tokens.size());

            assertAll(
                    () -> assertToken(tokens.get(0), 1, 1, "num", KeyWorld.NUM),
                    () -> assertToken(tokens.get(1), 1, 5, "a", Constants.IDENTIFIER),
                    () -> assertToken(tokens.get(2), 1, 7, "=", Operators.OP_ATRIB),
                    () -> assertToken(tokens.get(3), 1, 9, "125.21", Constants.NUM_CONST),
                    () -> assertToken(tokens.get(4), 1, 15, FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME)
            );

        }
    }
}


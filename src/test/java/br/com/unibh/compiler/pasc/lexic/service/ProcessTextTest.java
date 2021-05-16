package br.com.unibh.compiler.pasc.lexic.service;

import br.com.unibh.compiler.pasc.lexic.configuration.EOFConfig;
import br.com.unibh.compiler.pasc.lexic.configuration.PanicModeConfig;
import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.model.TokeError;
import br.com.unibh.compiler.pasc.lexic.model.Token;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
            final List<Token> tokens = processText.tokens;
            assertEquals(4, tokens.size());
            final Token first = tokens.get(0);
            final Token second = tokens.get(1);
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
            final List<Token> tokens = processText.tokens;
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
    void testWhenProgramFails() {
        try (final InputStream resource = getResource("program3.pasc")) {
            processText.process(resource);
            final List<Token> tokens = processText.tokens;
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
                    () -> assertToken(tokens.get(7), 3, 2, "write", KeyWorld.WRITE.getTokenName()),
                    () -> assertToken(tokens.get(8), 3, 8, "var1", Constants.IDENTIFIER.getTokenName()),
                    () -> assertToken(tokens.get(9), 4, 1, "}", Symbols.SMB_CBC.getTokenName()),
                    () -> assertToken(tokens.get(10), 4, 2, EOFConfig.EOF_TOKEN_NAME, EOFConfig.EOF_TOKEN_NAME)
            );
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

    private void assertTokenError(final Token assertToken, int line, int column, String tokenName) {
        assertAll(
                () -> assertNotNull(assertToken),
                () -> assertEquals(line, assertToken.getLine()),
                () -> assertEquals(column, assertToken.getColumn()),
                () -> assertEquals(tokenName, assertToken.getName()),
                () -> assertTrue(assertToken instanceof TokeError)
        );
    }

    private InputStream getResource(String name) throws URISyntaxException, IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(name);
        Path path = Path.of(resource.toURI());
        return Files.newInputStream(path);
    }

}
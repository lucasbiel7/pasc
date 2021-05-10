package br.com.unibh.compiler.pasc.lexic.service;

import br.com.unibh.compiler.pasc.lexic.model.Constants;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Processing language resources")
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
            assertNotNull(first);
            assertEquals("123", first.getValue());
            assertEquals(Constants.NUM_CONST.getTokenName(), first.getName());
            assertEquals(1, first.getLine());
            assertEquals(1, first.getColumn());
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


    private InputStream getResource(String name) throws URISyntaxException, IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(name);
        Path path = Path.of(resource.toURI());
        return Files.newInputStream(path);
    }

}
package br.com.unibh.compiler.pasc.service;

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
        try (final InputStream resource = getResource("program.pasc")) {
            processText.process(resource);
            
        }
    }


    private InputStream getResource(String name) throws URISyntaxException, IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource("program.pasc");
        Path path = Path.of(resource.toURI());
        return Files.newInputStream(path);
    }

}
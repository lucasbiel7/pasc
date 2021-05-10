package br.com.unibh.compiler.pasc;

import br.com.unibh.compiler.pasc.service.ProcessText;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Log
public class App {

    @SneakyThrows
    public static void main(String[] args) {
        final Path file = Path.of(args[0]);
        try (final InputStream inputStream = Files.newInputStream(file)) {
            ProcessText processText = new ProcessText();
            processText.process(inputStream);
        }
    }
}

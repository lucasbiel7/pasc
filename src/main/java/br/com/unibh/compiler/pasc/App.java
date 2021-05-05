package br.com.unibh.compiler.pasc;

import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;

@Log
public class App {

    public static void main(String[] args) {
        //TODO colocar para compilar o args que é o código fonte
        try {
            final Path file = Path.of(args[0]);
            final List<String> lines = Files.readAllLines(file);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }
}

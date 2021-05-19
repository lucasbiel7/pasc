package br.com.unibh.compiler.pasc.lexic;

import br.com.unibh.compiler.pasc.lexic.model.TokeError;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.service.LanguageLexer;
import br.com.unibh.compiler.pasc.lexic.service.ProcessText;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.logging.LogManager;

@Log
public class App {

    static {
        InputStream stream = App.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public static final String MARKED_STRING = " *************** ";

    @SneakyThrows
    public static void main(String[] args) {
        LanguageLexer.setDefaultLocale(Locale.forLanguageTag("pt-BR"));
        if (args.length > 0) {
            final Path file = Path.of(args[0]);
            try (final InputStream inputStream = Files.newInputStream(file)) {
                ProcessText processText = new ProcessText();
                processText.process(inputStream);
                log.warning(MARKED_STRING + LanguageLexer.getInstance().message("MSG008") + MARKED_STRING);
                processText.getTokens().stream().forEach(App::showToken);
                log.warning(MARKED_STRING + LanguageLexer.getInstance().message("MSG009") + MARKED_STRING);
                processText.getSymbolTable()
                        .stream()
                        .forEach(o -> log.info(String.format("%s -> %s", o.getLeft(), o.getRight())));
            }
        } else {
            log.severe(LanguageLexer.getInstance().message("MSG004"));
        }
    }

    private static void showToken(Token token) {
        if (token instanceof TokeError) {
            log.severe(String.valueOf(token));
        } else {
            log.info(String.valueOf(token));
        }
    }
}

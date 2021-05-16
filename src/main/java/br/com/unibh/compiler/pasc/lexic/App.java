package br.com.unibh.compiler.pasc.lexic;

import br.com.unibh.compiler.pasc.lexic.model.TokeError;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.service.LanguageLexer;
import br.com.unibh.compiler.pasc.lexic.service.ProcessText;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@Log
public class App {

    @SneakyThrows
    public static void main(String[] args) {
        LanguageLexer.setDefaultLocale(Locale.forLanguageTag("pt-BR"));
        if (args.length > 0) {
            final Path file = Path.of(args[0]);
            try (final InputStream inputStream = Files.newInputStream(file)) {
                ProcessText processText = new ProcessText();
                processText.process(inputStream);
                processText.getTokens().stream().forEach(App::showToken);
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

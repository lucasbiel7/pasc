package br.com.unibh.compiler.pasc;

import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.model.TokenError;
import br.com.unibh.compiler.pasc.lexic.service.LanguageLexer;
import br.com.unibh.compiler.pasc.lexic.service.LexicalService;
import br.com.unibh.compiler.pasc.simple.syntactic.RecursiveSyntactic;
import br.com.unibh.compiler.pasc.syntactic.service.SyntacticService;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.function.Consumer;
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
            LexicalService lexicalService = new LexicalService();
            SyntacticService syntacticService = new SyntacticService();
            Queue<Token> tokens = new LinkedList<>();
            try (final InputStream inputStream = Files.newInputStream(file)) {
                log.warning(MARKED_STRING + LanguageLexer.getInstance().message("MSG008") + MARKED_STRING);
                final Consumer<Token> showToken = App::showToken;
//                syntacticService.andThen(App::showToken)
                lexicalService.process(inputStream, tokens::add);
                RecursiveSyntactic recursiveSyntactic = new RecursiveSyntactic(tokens, lexicalService.getSymbolTable(), App::showToken);
                recursiveSyntactic.analyse();
            } finally {
                log.warning(MARKED_STRING + LanguageLexer.getInstance().message("MSG009") + MARKED_STRING);
                lexicalService.getSymbolTable()
                        .stream()
                        .forEach(o -> log.info(String.format("%s -> %s", o.getLeft(), o.getRight())));
            }
        } else {
            log.severe(LanguageLexer.getInstance().message("MSG004"));
        }
    }

    private static void showToken(Token token) {
        if (token instanceof TokenError) {
            log.severe(String.valueOf(token));
        } else {
            log.info(String.valueOf(token));
        }
    }
}

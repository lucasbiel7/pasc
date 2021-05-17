package br.com.unibh.compiler.pasc.lexic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

/**
 * Palavras reservadas da linguagem
 *
 * @author Lucas Gabriel
 * @since 09 de maio de 2021
 */
@Getter
@AllArgsConstructor
public enum KeyWorld implements TokenName {


    PROGRAM(Constants.KW + "_PROGRAM", "program"),

    IF(Constants.KW + "_IF", "if"),

    ELSE(Constants.KW + "_ELSE", "else"),

    WHILE(Constants.KW + "_WHILE", "while"),

    WRITE(Constants.KW + "_WRITE", "write"),

    READ(Constants.KW + "_READ", "read"),

    NUM(Constants.KW + "_NUM", "num"),

    CHAR(Constants.KW + "_CHAR", "char"),

    NOT(Constants.KW + "_NOT", "not"),

    OR(Constants.KW + "_OR", "or"),

    AND(Constants.KW + "_AND", "and");

    private final String tokenName;
    private final String value;

    private static final class Constants {
        private static final String KW = "KW";
    }

    private static final class CacheKeyWorld {
        private static Map<String, TokenName> tokens;

        static {
            tokens = Arrays.stream(KeyWorld.values()).collect(Collectors.toMap(KeyWorld::getValue, identity()));
        }
    }

    public static boolean isKeyWord(String value) {
        return CacheKeyWorld.tokens.containsKey(value);
    }

    public static Optional<TokenName> getKeyWorld(String value) {
        return Optional.ofNullable(CacheKeyWorld.tokens.get(value));
    }

}

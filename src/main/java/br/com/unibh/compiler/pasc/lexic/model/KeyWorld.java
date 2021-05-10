package br.com.unibh.compiler.pasc.lexic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Palavras reservadas da linguagem
 *
 * @author Lucas Gabriel
 * @since 09 de maio de 2021
 */
@Getter
@AllArgsConstructor
public enum KeyWorld implements TokenName {


    PROGRAM(Constants.KW, "program"),

    IF(Constants.KW, "if"),

    ELSE(Constants.KW, "else"),

    WHILE(Constants.KW, "while"),

    WRITE(Constants.KW, "write"),

    READ(Constants.KW, "read"),

    NUM(Constants.KW, "num"),

    CHAR(Constants.KW, "char"),

    NOT(Constants.KW, "not"),

    OR(Constants.KW, "or"),

    AND(Constants.KW, "and");

    private final String tokenName;
    private final String value;

    private static final class Constants {
        private static final String KW = "KW";
    }

}

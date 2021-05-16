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

}

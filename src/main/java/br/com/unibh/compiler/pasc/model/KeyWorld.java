package br.com.unibh.compiler.pasc.model;

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
public enum KeyWorld {

    PROGRAM("program"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    WRITE("write"),
    READ("read"),
    NUM("num"),
    CHAR("char"),
    NOT("not"),
    OR("or"),
    AND("and");

    private final String command;

}

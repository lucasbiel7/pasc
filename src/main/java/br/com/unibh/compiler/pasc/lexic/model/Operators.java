package br.com.unibh.compiler.pasc.lexic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Operadores da linguagem
 *
 * @author Lucas Dutra
 * @since 09 de maio 2021
 */
@AllArgsConstructor
@Getter
public enum Operators implements TokenName {

    OP_EQ("OP_EQ", "=="),
    OP_NE("OP_NE", "!="),
    OP_GT("OP_GT", ">"),
    OP_LT("OP_LT", "<"),
    OP_GE("OP_GE", ">="),
    OP_LE("OP_LE", "<="),
    OP_AD("OP_AD", "+"),
    OP_MIN("OP_MIN", "-"),
    OP_MUL("OP_MUL", "*"),
    OP_DIV("OP_DIV", "/"),
    OP_ATRIB("OP_ATRIB", "=");

    private final String tokenName;
    private final String value;

}

package br.com.unibh.compiler.pasc.lexic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private String name;
    private String value;
    private int line;
    private int column;
    private String type;

    public Token(String name, String value, int line, int column) {
        this.name = name;
        this.value = value;
        this.line = line;
        this.column = column;
    }
}

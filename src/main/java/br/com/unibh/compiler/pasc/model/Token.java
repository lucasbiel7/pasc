package br.com.unibh.compiler.pasc.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {

    private String name;
    private String value;
    private int line;
    private int column;


    @Override
    public String toString() {
        return "Token{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}

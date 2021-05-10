package br.com.unibh.compiler.pasc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Identifier implements TokenName {

    private String value;

    @Override
    public String getTokenName() {
        return "ID";
    }
}

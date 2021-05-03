package br.com.unibh.compiler.pasc.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {

    private String nome;
    private String valor;
    private int linha;
    private int coluna;

}

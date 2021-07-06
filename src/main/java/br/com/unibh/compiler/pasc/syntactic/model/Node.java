package br.com.unibh.compiler.pasc.syntactic.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private SyntacticType type;


    public Node() {
        type = SyntacticType.VOID;
    }

    public Node(SyntacticType type) {
        this.type = type;
    }
}

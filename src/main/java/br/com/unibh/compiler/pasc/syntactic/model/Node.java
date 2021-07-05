package br.com.unibh.compiler.pasc.syntactic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Node {

    private SyntacticType type;
    private Node filho;

    public Node(SyntacticType type) {
        this.type = type;
    }
}

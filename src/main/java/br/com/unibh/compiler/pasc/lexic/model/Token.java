package br.com.unibh.compiler.pasc.lexic.model;

import br.com.unibh.compiler.pasc.syntactic.model.SyntacticType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private String name;
    private String value;
    private int line;
    private int column;
    private SyntacticType type;

    public Token(String name, String value, int line, int column) {
        this.name = name;
        this.value = value;
        this.line = line;
        this.column = column;
    }
}

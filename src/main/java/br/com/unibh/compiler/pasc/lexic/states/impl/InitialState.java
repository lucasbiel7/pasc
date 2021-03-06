package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.UnexpectedSymbolException;
import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Estado inicial para validar os caracteres
 *
 * @author Lucas Dutra
 * @since 04 maio 2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InitialState implements State {

    private static InitialState instance;


    public static InitialState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new InitialState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        if (Character.isDigit(value)) return new NumberState(value);
        if (Character.isLetter(value)) return new IdentifierState(value);
        if (Symbols.isSymbol(value)) return new SymbolState(value);
        return switch (value) {
            case ' ', '\n', '\r', '\t' -> InitialState.getInstance();
            case '/' -> DivisionState.getInstance();
            case '=' -> AttributionState.getInstance();
            case '+', '-', '*' -> new OperatorState(value);
            case '"' -> new StringState(value);
            case '<' -> LessThanState.getInstance();
            case '>' -> GreaterThanState.getInstance();
            case '!' -> NotOperatorState.getInstance();
            default -> throw new UnexpectedSymbolException("MSG001", value);
        };
    }
}

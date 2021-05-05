package br.com.unibh.compiler.pasc.states;

/**
 * @author Lucas Dutra
 */
public class InitialState implements State {

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '/' -> new BarState();
            case '=' -> new EqualState();
            case '+', '-', '*' -> new OperatorState(value);
            default -> throw new UnsupportedOperationException("Caracter não é valido");
        };
    }
}

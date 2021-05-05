package br.com.unibh.compiler.pasc.states;

public class BarState implements State {
    @Override
    public State nextState(char value) {
        return switch (value) {
            case '/' -> new CommentState();
            default -> new OperatorState(value);
        };
    }
}

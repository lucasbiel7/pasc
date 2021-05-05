package br.com.unibh.compiler.pasc.states;


public class CommentState implements State {

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '\n' -> new InitialState();
            default -> this;
        };
    }
}

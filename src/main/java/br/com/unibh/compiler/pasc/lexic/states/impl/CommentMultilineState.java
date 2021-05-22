package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.service.LanguageLexer;
import br.com.unibh.compiler.pasc.lexic.states.State;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMultilineState implements State {

    private static CommentMultilineState instance;

    public static CommentMultilineState getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CommentMultilineState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        return switch (value) {
            case '*' -> PreCloseMultilineCommendState.getInstance();
            default -> this;
        };
    }
    @Override
    public String messageError() {
        return LanguageLexer.getInstance().message("MSG012");
    }
}
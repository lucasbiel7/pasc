package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.states.State;

import java.util.Objects;

/**
 * Representando um estado vazio, será utilizado quando uma expressão precisar ler outro simbolo
 * qualquer para gerar o token com o lexema
 *
 * @author Lucas Gabriel
 * @since 09 de maio de 2021
 */
public class EmptyState implements State {

    private static EmptyState instance;

    public static State getInstance() {
        if (Objects.isNull(instance)) {
            instance = new EmptyState();
        }
        return instance;
    }

    @Override
    public State nextState(char value) {
        throw new UnsupportedOperationException("Do nothing");
    }
}

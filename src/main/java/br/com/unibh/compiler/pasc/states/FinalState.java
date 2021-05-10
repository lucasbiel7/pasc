package br.com.unibh.compiler.pasc.states;


import br.com.unibh.compiler.pasc.states.impl.EmptyState;

/**
 * Interface de marcação para falar que o estado é final
 *
 * @author Lucas Gabriel
 */
public interface FinalState extends State {

    @Override
    default State nextState(char value) {
        return EmptyState.getInstance();
    }

    String name();

    String value();

}

package br.com.unibh.compiler.pasc.lexic.states;

/**
 * Classe para definir comportamento padrão de estados
 *
 * @author Lucas Dutra
 */
@FunctionalInterface
public interface State {

    State nextState(char value);

}

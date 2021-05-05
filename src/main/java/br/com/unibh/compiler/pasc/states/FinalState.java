package br.com.unibh.compiler.pasc.states;

/**
 * Interface de marcação para falar que o estado é final
 *
 * @author Lucas Gabriel
 */
public interface FinalState extends State {

    @Override
    default State nextState(char value) {
        throw new UnsupportedOperationException("Estado final não pode ler outra variável");
    }
}

package br.com.unibh.compiler.pasc.lexic.states;


import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.states.impl.EmptyState;

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

    /**
     * Utilizado para abstrair o nome de um token gerado por ser um estado final
     *
     * @return tokeName
     * @see Token#getName()
     */
    String name();

    /**
     * Valor do estado naquele momento, quando o estado e interado
     *
     * @return O valor do estado atual
     * @see Token#getValue()
     */
    String value();

}

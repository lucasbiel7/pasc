package br.com.unibh.compiler.pasc.lexic.states;

import br.com.unibh.compiler.pasc.lexic.service.LanguageLexer;

/**
 * Classe para definir comportamento padrão de estados
 *
 * @author Lucas Dutra
 */
@FunctionalInterface
public interface State {

    State nextState(char value);

    default String messageError() {
        return LanguageLexer.getInstance().message("MSG006");
    }
}

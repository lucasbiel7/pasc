package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.exceptions.UnexpectedSymbolException;
import br.com.unibh.compiler.pasc.lexic.service.LanguageLexer;
import br.com.unibh.compiler.pasc.lexic.states.State;

/**
 * Classe para armazenar o valor digitado entre aspas ("aaa"=aaa)
 * para conseguir gerar o token na leitura do código
 *
 * @author Cleiton
 * @since 12 de maio 2021
 */
public class StringState implements State {

    private StringBuilder value;
    private char delimiter;

    public StringState(char delimiter) {
        this.delimiter = delimiter;
        this.value = new StringBuilder();
    }

    @Override
    public State nextState(char value) {
        if (value == delimiter) {
            if (this.value.isEmpty()) {
                throw new UnexpectedSymbolException("MSG011");
            }
            return new ClosedStringState(this.value.toString());
        }
        if (value == '\n') {
            throw new UnexpectedSymbolException("MSG002");
        }
        if (isAscii(value)) {
            this.value.append(value);
            return this;
        }
        throw new UnexpectedSymbolException("MSG003", value);
    }

    /**
     * Verifica se os caracteres são ASCII
     *
     * @param ch
     * @return retorna verdadeiro caso o caracter seja ascii
     * @see java.util.regex.ASCII#isAscii(int)
     */
    static boolean isAscii(int ch) {
        return ((ch & 0xFFFFFF80) == 0);
    }

    @Override
    public String messageError() {
        return LanguageLexer.getInstance().message("MSG007", delimiter);
    }
}

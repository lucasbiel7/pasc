package br.com.unibh.compiler.pasc.lexic.states.impl;

import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;

/**
 * Estado para simbolos comuns
 *
 * @author Lucas Dutra
 * @since 10 de maio de 2021
 */
public class SymbolState implements FinalState {

    final Symbols symbol;

    public SymbolState(char value) {
        symbol = Symbols.getSymbol(value);
    }

    @Override
    public String name() {
        return symbol.getTokenName();
    }

    @Override
    public String value() {
        return symbol.getValue();
    }
}
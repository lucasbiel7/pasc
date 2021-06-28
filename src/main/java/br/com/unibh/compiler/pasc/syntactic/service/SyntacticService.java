package br.com.unibh.compiler.pasc.syntactic.service;

import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.syntactic.grammar.BaseRule;
import br.com.unibh.compiler.pasc.syntactic.grammar.ProgramRule;
import lombok.extern.java.Log;

import java.util.function.Consumer;

@Log
public class SyntacticService implements Consumer<Token> {
    /**
     * Controla a quantidade de erros sintáticos
     */
    private int erros;

    BaseRule current;

    public SyntacticService() {
        this.current = new ProgramRule();
    }

    // Regra -> conjunto de regras Tanto consumir um token ou uma nova regra
    // program id body ... EOF
    @Override
    public void accept(Token token) {
        //processar o que a gente acha dele?
        log.warning("Lexico fala token: " + token.getName());
        current.process(token);
    }


}

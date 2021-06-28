package br.com.unibh.compiler.pasc.syntactic.grammar;

import br.com.unibh.compiler.pasc.lexic.model.Token;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class BaseRule {

    protected Queue<BaseRule> rules;

    BaseRule(BaseRule... rules) {
        this.rules = new LinkedList<>(List.of(rules));
    }

    public abstract void process(Token token);

}

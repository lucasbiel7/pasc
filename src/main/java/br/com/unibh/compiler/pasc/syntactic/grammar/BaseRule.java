package br.com.unibh.compiler.pasc.syntactic.grammar;

import br.com.unibh.compiler.pasc.lexic.model.SpecialTokens;
import br.com.unibh.compiler.pasc.lexic.model.Token;

import java.util.Queue;

public abstract class BaseRule {

    protected Queue<BaseRule> rules;

    protected BaseRule previous;

    public BaseRule(BaseRule previous) {
        this.previous = previous;
        initRules();
    }

    public abstract void initRules();

    public BaseRule process(Token token) {
        //Verificar se não tem mais regra e o previous e null (Quer dizer que leu com sucesso!)
        if (rules.isEmpty()) {
            if (previous == null) {
                //Tenho PROCESSAR UM EOF
                return new ConsumerTokenRule(null, SpecialTokens.EOF).process(token);
            }
            return previous.process(token);
        }
        return rules.poll().process(token);
    }

}

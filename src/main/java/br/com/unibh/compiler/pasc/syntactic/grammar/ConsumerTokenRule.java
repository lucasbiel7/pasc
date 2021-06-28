package br.com.unibh.compiler.pasc.syntactic.grammar;

import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.model.TokenName;

public class ConsumerTokenRule extends BaseRule {

    private final TokenName tokenName;

    public ConsumerTokenRule(BaseRule previous, TokenName tokenName) {
        super(previous);
        this.tokenName = tokenName;
    }

    //TODO verificar uma hierarquia sem regras
    @Override
    public void initRules() {
    }

    @Override
    public BaseRule process(Token token) {
        if (!token.getName().equals(tokenName.getTokenName())) {
            throw new RuntimeException(String.format("Token esperado Name: %s Value: %s ", tokenName.getTokenName(), tokenName.getValue()));
        }
        return previous;
    }
}

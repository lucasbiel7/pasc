package br.com.unibh.compiler.pasc.syntactic.grammar;

import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.model.TokenName;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsumerTokenRule extends BaseRule {

    private final TokenName tokenName;

    @Override
    public void process(Token token) {
        if (!token.getName().equals(tokenName.getTokenName())) {
            throw new RuntimeException("Erro");
        }
    }
}

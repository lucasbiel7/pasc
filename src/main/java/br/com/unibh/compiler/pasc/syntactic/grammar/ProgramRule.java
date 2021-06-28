package br.com.unibh.compiler.pasc.syntactic.grammar;

import br.com.unibh.compiler.pasc.lexic.model.Identifier;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.model.Token;

public class ProgramRule extends BaseRule {

    public ProgramRule() {
        super(
                new ConsumerTokenRule(KeyWorld.PROGRAM),
                new ConsumerTokenRule(new Identifier("")),
                new BodyRule()
        );
    }

    @Override
    public void process(Token token) {
        rules.poll().process(token);
    }
}

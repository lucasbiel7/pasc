package br.com.unibh.compiler.pasc.syntactic.grammar;

import br.com.unibh.compiler.pasc.lexic.model.Identifier;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;

import java.util.Arrays;
import java.util.LinkedList;

public class ProgramRule extends BaseRule {

    public ProgramRule(BaseRule previous) {
        super(previous);
    }

    @Override
    public void initRules() {
        rules = new LinkedList<>(
                Arrays.asList(
                        new ConsumerTokenRule(this, KeyWorld.PROGRAM),
                        new ConsumerTokenRule(this, new Identifier("")),
                        new BodyRule(this)
                )
        );
    }

}

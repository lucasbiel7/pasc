package br.com.unibh.compiler.pasc.syntactic.grammar;

import br.com.unibh.compiler.pasc.lexic.model.Symbols;

import java.util.LinkedList;
import java.util.List;

public class BodyRule extends BaseRule {


    public BodyRule(BaseRule previous) {
        super(previous);
    }

    @Override
    public void initRules() {
        rules = new LinkedList<>(
                List.of(
                        //regra de declList
                        new ConsumerTokenRule(this, Symbols.SMB_OBC),
                        //regra de stmt-list
                        new ConsumerTokenRule(this, Symbols.SMB_CBC)
                )
        );
    }

}

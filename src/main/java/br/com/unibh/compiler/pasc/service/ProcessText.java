package br.com.unibh.compiler.pasc.service;

import br.com.unibh.compiler.pasc.model.Token;
import br.com.unibh.compiler.pasc.states.FinalState;
import br.com.unibh.compiler.pasc.states.State;
import br.com.unibh.compiler.pasc.states.impl.EmptyState;
import br.com.unibh.compiler.pasc.states.impl.InitialState;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ProcessText {

    List<Token> tokens;

    public ProcessText() {
        tokens = new ArrayList<>();
    }


    public void process(InputStream data) throws IOException {
        int value;
        State actualState = InitialState.getInstance();
        FinalState lastFinalState = null;
        int line = 1;
        int column = 1;
        while ((value = data.read()) != -1) {
            char valueCasted = (char) value;
            actualState = actualState.nextState(valueCasted);
            if (actualState instanceof FinalState finalState) {
                lastFinalState = finalState;
            }
            if (actualState instanceof EmptyState) {
                tokens.add(Token.builder()
                        .column(column - lastFinalState.value().length())
                        .line(line)
                        .value(lastFinalState.value())
                        .name(lastFinalState.name())
                        .build());
                lastFinalState = null;
                actualState = InitialState.getInstance().nextState(valueCasted);
            }

            column++;
            if (valueCasted == '\n') {
                line++;
                column = 1;
            }
        }
        if (lastFinalState != null) {
            tokens.add(Token.builder()
                    .column(column - lastFinalState.value().length())
                    .line(line)
                    .value(lastFinalState.value())
                    .name(lastFinalState.name())
                    .build());
        }
        tokens.add(Token.builder()
                .column(column)
                .line(line)
                .value("EOF")
                .name("EOF")
                .build());
    }

}

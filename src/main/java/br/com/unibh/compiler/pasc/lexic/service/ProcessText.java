package br.com.unibh.compiler.pasc.lexic.service;

import br.com.unibh.compiler.pasc.lexic.configuration.EOFConfig;
import br.com.unibh.compiler.pasc.lexic.configuration.PanicModeConfig;
import br.com.unibh.compiler.pasc.lexic.exceptions.UnexpectedSymbolException;
import br.com.unibh.compiler.pasc.lexic.model.TokeError;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.impl.EmptyState;
import br.com.unibh.compiler.pasc.lexic.states.impl.InitialState;

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
        int erros = 0;
        while ((value = data.read()) != -1) {
            char valueCasted = (char) value;
            try {
                actualState = actualState.nextState(valueCasted);
                if (actualState instanceof FinalState finalState) {
                    lastFinalState = finalState;
                } else if (actualState instanceof EmptyState) {
                    tokens.add(Token.builder()
                            .column(column - lastFinalState.value().length())
                            .line(line)
                            .value(lastFinalState.value())
                            .name(lastFinalState.name())
                            .build());
                    lastFinalState = null;
                    actualState = InitialState.getInstance();
                    actualState = actualState.nextState(valueCasted);
                } else {
                    lastFinalState = null;
                }
                // TODO validar se esse erro pode fica aqui
                erros = 0;
            } catch (UnexpectedSymbolException e) {
                erros++;
                addingErrorToken(line, column, e.getMessage());
                validateStopProgram(erros);
            } finally {
                column++;
                if (valueCasted == '\n') {
                    line++;
                    column = 1;
                }
            }
        }
        // TODO validar erros aqui
        if (lastFinalState != null) {
            tokens.add(Token.builder()
                    .column(column - lastFinalState.value().length())
                    .line(line)
                    .value(lastFinalState.value())
                    .name(lastFinalState.name())
                    .build());
        }
        eofToken(line, column);
    }

    private void addingErrorToken(int line, int column, String message) {
        tokens.add(new TokeError(PanicModeConfig.TOKEN_ERROR_NAME, message, line, column));
    }

    private void eofToken(int line, int column) {
        tokens.add(Token.builder()
                .column(column)
                .line(line)
                .value(EOFConfig.EOF_TOKEN_NAME)
                .name(EOFConfig.EOF_TOKEN_NAME)
                .build());
    }

    private void validateStopProgram(int erros) {
        if (erros >= PanicModeConfig.RETRY) {
            throw new RuntimeException("O programa não pode continuar, número máximos de erros seguidos obtido!");
        }
    }

}

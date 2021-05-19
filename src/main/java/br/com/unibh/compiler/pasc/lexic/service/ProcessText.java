package br.com.unibh.compiler.pasc.lexic.service;

import br.com.unibh.compiler.pasc.lexic.configuration.FileConfig;
import br.com.unibh.compiler.pasc.lexic.configuration.PanicModeConfig;
import br.com.unibh.compiler.pasc.lexic.exceptions.UnexpectedSymbolException;
import br.com.unibh.compiler.pasc.lexic.model.SymbolTable;
import br.com.unibh.compiler.pasc.lexic.model.TokeError;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.states.FinalState;
import br.com.unibh.compiler.pasc.lexic.states.State;
import br.com.unibh.compiler.pasc.lexic.states.impl.CommentLineState;
import br.com.unibh.compiler.pasc.lexic.states.impl.EmptyState;
import br.com.unibh.compiler.pasc.lexic.states.impl.IdentifierState;
import br.com.unibh.compiler.pasc.lexic.states.impl.InitialState;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Getter
public class ProcessText {

    private List<Token> tokens;
    private SymbolTable symbolTable;

    public ProcessText() {
        tokens = new ArrayList<>();
        symbolTable = new SymbolTable();
    }

    public void process(InputStream data) throws IOException {
        int value;
        State actualState = InitialState.getInstance();
        FinalState lastFinalState = null;
        int actualLine = 1;
        int actualColumn = 1;
        int erros = 0;
        int startLine = 1;
        int startColumn = 1;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data, StandardCharsets.UTF_8))) {
            while ((value = bufferedReader.read()) != -1) {
                char valueCasted = (char) value;
                try {
                    if (actualState instanceof InitialState) {
                        startColumn = actualColumn;
                        startLine = actualLine;
                    }
                    actualState = actualState.nextState(valueCasted);

                    if (actualState instanceof FinalState finalState) {
                        lastFinalState = finalState;
                    } else if (actualState instanceof EmptyState) {
                        generateTokenAndValidateIdentifier(lastFinalState, startLine, startColumn);
                        lastFinalState = null;
                        erros = 0;
                        actualState = InitialState.getInstance();
                        startColumn = actualColumn;
                        actualState = actualState.nextState(valueCasted);
                        if (actualState instanceof FinalState finalState) {
                            lastFinalState = finalState;
                        }
                    } else {
                        lastFinalState = null;
                    }
                } catch (UnexpectedSymbolException e) {
                    erros++;
                    addingErrorToken(actualLine, actualColumn, e.getMessage());
                    validateStopProgram(erros);
                } finally {
                    actualColumn += valueCasted == '\t' ? FileConfig.TAB_VALUE_COLUMN : 1;
                    if (valueCasted == '\n') {
                        actualLine++;
                        actualColumn = 1;
                    }
                }
            }
            if (lastFinalState != null) {
                generateTokenAndValidateIdentifier(lastFinalState, startLine, startColumn);
            } else {
                if (!(actualState instanceof InitialState || actualState instanceof CommentLineState)) {
                    //ERRO de estado não finalizado
                    addingErrorToken(actualLine, actualColumn, actualState.messageError());
                }
            }
            eofToken(actualLine, actualColumn);
        }
    }

    private void generateTokenAndValidateIdentifier(FinalState lastFinalState, int startLine, int startColumn) {
        final Token token = generateToken(startLine, startColumn, lastFinalState.value(), lastFinalState.name());
        this.tokens.add(token);
        verifyToTableSymbol(lastFinalState, token);
    }

    private void verifyToTableSymbol(FinalState lastFinalState, Token token) {
        if (lastFinalState instanceof IdentifierState identifierState) {
            symbolTable.add(identifierState.value(), token);
        }
    }

    private Token generateToken(int line, int column, String value, String name) {
        return Token.builder()
                .column(column)
                .line(line)
                .value(value)
                .name(name)
                .build();
    }

    private void addingErrorToken(int line, int column, String message) {
        tokens.add(new TokeError(PanicModeConfig.TOKEN_ERROR_NAME, message, line, column));
    }

    private void eofToken(int line, int column) {
        tokens.add(Token.builder()
                .column(column)
                .line(line)
                .value(FileConfig.EOF_TOKEN_NAME)
                .name(FileConfig.EOF_TOKEN_NAME)
                .build());
    }

    private void validateStopProgram(int erros) {
        if (erros >= PanicModeConfig.RETRY) {
            throw new RuntimeException("O programa não pode continuar, número máximos de erros seguidos obtido!");
        }
    }
}

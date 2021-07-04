package br.com.unibh.compiler.pasc.simple.syntactic;

import br.com.unibh.compiler.pasc.lexic.model.Identifier;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.model.SpecialTokens;
import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.model.TokenName;

import java.text.MessageFormat;
import java.util.Queue;

public class Sintatico {

    public static final Identifier DEFAULT_IDENTIFIER = new Identifier("");
    Queue<Token> tokens;

    private Token tokenAtual;


    public Sintatico(Queue<Token> tokens) {
        this.tokens = tokens;
    }

    public void analisar() {
        //carrega o primeiro token
        proximoToken();
        //primeira regra aqui
        prog();
        consumir(SpecialTokens.EOF);
    }

    private void prog() {
        consumir(KeyWorld.PROGRAM);
        consumir(DEFAULT_IDENTIFIER);
        body();
    }

    private void body() {
        declList();
        consumir(Symbols.SMB_OBC);
        stmtList();
        consumir(Symbols.SMB_CBC);
    }

    private void stmtList() {

    }

    private void declList() {
        if (ehToken(KeyWorld.NUM) || ehToken(KeyWorld.CHAR)) {
            decl();
            consumir(Symbols.SMB_SEM);
            declList();
        }
        // pode passar batido, porque ele aceita vazio
    }

    private void decl() {
        type();
        idList();
    }

    private void idList() {
        consumir(DEFAULT_IDENTIFIER);
        idListLinha();
    }

    private void idListLinha() {
        if (ehToken(Symbols.SMB_COM)) {
            consumir(Symbols.SMB_COM);
            idList();
        }
        // Segue o baile, transição em vazio
    }

    private void type() {
        if (ehToken(KeyWorld.NUM)) {
            consumir(KeyWorld.NUM);
        } else if (ehToken(KeyWorld.CHAR)) {
            consumir(KeyWorld.CHAR);
        } else {
            throw new RuntimeException("Erro sintático: Falto o tipo da váriavel");
        }
    }

    private void consumir(TokenName tokenName) {
        if (!ehToken(tokenName)) {
            throw new RuntimeException(MessageFormat.format("[{0},{1}] Erro analise sintatica, token esperado {2} = {3}",
                    tokenAtual.getLine(),
                    tokenAtual.getColumn(),
                    tokenName.getTokenName(),
                    tokenName.getValue()
            ));
        }
        proximoToken();
    }

    private boolean ehToken(TokenName tokenName) {
        return tokenAtual.getName().equals(tokenName.getTokenName());
    }

    private void proximoToken() {
        tokenAtual = tokens.poll();
    }

}



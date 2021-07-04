package br.com.unibh.compiler.pasc.simple.syntactic;

import br.com.unibh.compiler.pasc.lexic.model.Identifier;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.model.SpecialTokens;
import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.model.TokenName;

import java.text.MessageFormat;
import java.util.Arrays;
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
        //Validar os first de stmt
        if (ehToken(DEFAULT_IDENTIFIER, KeyWorld.IF, KeyWorld.WHILE, KeyWorld.READ, KeyWorld.WRITE)) {
            stmt();
            consumir(Symbols.SMB_SEM);
            stmtList();
        }
        // segue o baile, transição em vazio
    }

    private void stmt() {
        if (ehToken(DEFAULT_IDENTIFIER)) {
            assignStmt();
        } else if (ehToken(KeyWorld.IF)) {
            ifStmt();
        } else if (ehToken(KeyWorld.WHILE)) {
            whileStmt();
        } else if (ehToken(KeyWorld.READ)) {
            readStmt();
        } else if (ehToken(KeyWorld.WRITE)) {
            writeStmt();
        } else {
            enviaErroSintatico("O Comando é inválido!");
        }
    }

    private void writeStmt() {

    }

    private void readStmt() {

    }

    private void whileStmt() {
    }

    private void ifStmt() {
        consumir(KeyWorld.IF);
        consumir(Symbols.SMB_OPA);
        expression();
        consumir(Symbols.SMB_CPA);
        consumir(Symbols.SMB_OBC);
        stmtList();
        consumir(Symbols.SMB_CBC);
        ifStmtLinha();
    }

    private void ifStmtLinha() {
        if (ehToken(KeyWorld.ELSE)) {
            consumir(KeyWorld.ELSE);
            consumir(Symbols.SMB_OBC);
            stmtList();
            consumir(Symbols.SMB_CBC);
        }
        //segue o baile, transicao em vazio
    }

    private void expression() {
    }

    private void assignStmt() {
        consumir(DEFAULT_IDENTIFIER);
        consumir(Operators.OP_ATRIB);
        simpleExpr();
    }

    private void simpleExpr() {
    }

    private void declList() {
        if (ehToken(KeyWorld.NUM, KeyWorld.CHAR)) {
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
            enviaErroSintatico(MessageFormat.format("O token {0} não é um tipo válido", tokenAtual.getValue()));
        }
    }

    //TODO melhorar tratamento de exceção
    private void enviaErroSintatico(String mensagem) {
        throw new RuntimeException(MessageFormat.format("[{0},{1}] Erro analise sintática: {2}",
                tokenAtual.getLine(), tokenAtual.getColumn(), mensagem)
        );
    }

    private void consumir(TokenName tokenName) {
        if (!ehToken(tokenName)) {
            enviaErroSintatico(MessageFormat.format(
                    "token esperado {0} = {1}",
                    tokenName.getTokenName(),
                    tokenName.getValue()));
        }
        proximoToken();
    }

    private boolean ehToken(TokenName tokenName) {
        return tokenAtual.getName().equals(tokenName.getTokenName());
    }

    @SafeVarargs
    private boolean ehToken(TokenName... tokenName) {
        return Arrays.stream(tokenName).anyMatch(this::ehToken);
    }

    private void proximoToken() {
        tokenAtual = tokens.poll();
    }

}



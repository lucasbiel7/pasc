package br.com.unibh.compiler.pasc.simple.syntactic;

import br.com.unibh.compiler.pasc.lexic.model.Constants;
import br.com.unibh.compiler.pasc.lexic.model.KeyWorld;
import br.com.unibh.compiler.pasc.lexic.model.Operators;
import br.com.unibh.compiler.pasc.lexic.model.SpecialTokens;
import br.com.unibh.compiler.pasc.lexic.model.SymbolTable;
import br.com.unibh.compiler.pasc.lexic.model.Symbols;
import br.com.unibh.compiler.pasc.lexic.model.Token;
import br.com.unibh.compiler.pasc.lexic.model.TokenError;
import br.com.unibh.compiler.pasc.lexic.model.TokenName;
import br.com.unibh.compiler.pasc.syntactic.model.Node;
import br.com.unibh.compiler.pasc.syntactic.model.SyntacticType;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

public class Sintatico {

    private Queue<Token> tokens;

    private Token tokenAtual;

    private SymbolTable symbolTable;
    /**
     * Nós para controlar as avaliações semanticas
     */
    private Node idList;
    private Node simpleExpression;
    private Node expression;


    public Sintatico(Queue<Token> tokens, SymbolTable symbolTable) {
        this.tokens = tokens;
        this.symbolTable = symbolTable;
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
        consumirAlterarTipo(Constants.IDENTIFIER, SyntacticType.VOID);
        body();
    }

    private void body() {
        declList();
        blockCode();
    }

    private void stmtList() {
        //Validar os first de stmt
        if (ehToken(Constants.IDENTIFIER, KeyWorld.IF, KeyWorld.WHILE, KeyWorld.READ, KeyWorld.WRITE)) {
            stmt();
            consumir(Symbols.SMB_SEM);
            stmtList();
        }
        // segue o baile, transição em vazio
    }

    private void stmt() {
        if (ehToken(Constants.IDENTIFIER)) {
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
        consumir(KeyWorld.WRITE);
        simpleExpr();
    }

    private void readStmt() {
        consumir(KeyWorld.READ);
        consumirValidateTS(Constants.IDENTIFIER);
    }

    private void whileStmt() {
        stmtPrefix();
        blockCode();
    }

    private void stmtPrefix() {
        consumir(KeyWorld.WHILE);
        expressionInParenteses();
    }

    private void ifStmt() {
        consumir(KeyWorld.IF);
        expressionInParenteses();
        blockCode();
        ifStmtLinha();
    }

    private void expressionInParenteses() {
        consumir(Symbols.SMB_OPA);
        expression();
        consumir(Symbols.SMB_CPA);
    }

    private void blockCode() {
        consumir(Symbols.SMB_OBC);
        stmtList();
        consumir(Symbols.SMB_CBC);
    }

    private void ifStmtLinha() {
        if (ehToken(KeyWorld.ELSE)) {
            consumir(KeyWorld.ELSE);
            blockCode();
        }
        //segue o baile, transicao em vazio
    }

    private void expression() {
        simpleExpr();
        expressionLine();
    }

    private void expressionLine() {
        if (ehToken(KeyWorld.AND, KeyWorld.OR)) {
            logOp();
            simpleExpr();
            expressionLine();
        }
    }

    private void logOp() {
        consumirOr("Operador inválido para unir expressões, esperado and ou or",
                KeyWorld.AND,
                KeyWorld.OR);
    }

    private void assignStmt() {
        consumirValidateTS(Constants.IDENTIFIER);
        consumir(Operators.OP_ATRIB);
        simpleExpr();
    }


    private void simpleExpr() {
        term();
        simpleExprLine();
    }

    private void simpleExprLine() {
        if (ehToken(Operators.OP_EQ, Operators.OP_GT, Operators.OP_GE, Operators.OP_LT, Operators.OP_LE, Operators.OP_NE)) {
            relop();
            term();
            simpleExprLine();
        }
        // segue o baile, transicao em vazio
    }

    private void relop() {
        consumirOr("Operadores inválidos",
                Operators.OP_EQ,
                Operators.OP_GT,
                Operators.OP_GE,
                Operators.OP_LT,
                Operators.OP_LE,
                Operators.OP_NE
        );
    }


    private void term() {
        factorB();
        termLine();
    }

    private void termLine() {
        if (ehToken(Operators.OP_AD, Operators.OP_MIN)) {
            addOp();
            factorB();
            termLine();
        }
    }

    private void addOp() {
        consumirOr("Operador inválido, utilize + ou - ",
                Operators.OP_AD,
                Operators.OP_MIN
        );
    }

    private void factorB() {
        factorA();
        factorBLine();
    }

    private void factorBLine() {
        if (ehToken(Operators.OP_MUL, Operators.OP_DIV)) {
            mulop();
            factorA();
            factorBLine();
        }
    }

    private void mulop() {
        consumirOr("Operador inválido, utilize * ou / ",
                Operators.OP_MUL,
                Operators.OP_DIV
        );
    }

    private void factorA() {
        if (ehToken(KeyWorld.NOT)) {
            consumir(KeyWorld.NOT);
        }
        factor();
    }

    private void factor() {
        if (ehToken(Constants.IDENTIFIER)) {
            consumir(Constants.IDENTIFIER);
        } else if (ehToken(Symbols.SMB_OPA)) {
            expressionInParenteses();
        } else if (ehToken(Constants.NUM_CONST, Constants.CHAR_CONST)) {
            constant();
        } else {
            enviaErroSintatico("Fator inválido!");
        }
    }

    private void constant() {
        consumirOr("Constante era esperada",
                Constants.NUM_CONST,
                Constants.CHAR_CONST
        );
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
        consumirAlterarTipo(Constants.IDENTIFIER, idList.getType());
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
            idList = new Node(SyntacticType.NUM);
        } else if (ehToken(KeyWorld.CHAR)) {
            consumir(KeyWorld.CHAR);
            idList = new Node(SyntacticType.CHAR);
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

    private void enviaErroSemantico(String mensagem) {
        throw new RuntimeException(MessageFormat.format("[{0},{1}] Erro analise semântica: {2}",
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

    private SyntacticType consumirValidateTS(TokenName tokeName) {
        Token temp = tokenAtual;
        final Token token = symbolTable.get(temp.getValue());
        if (Objects.isNull(token.getType())) {
            enviaErroSemantico(MessageFormat.format(
                    "O identificador {0} não foi declarado",
                    token.getValue()
            ));
        }
        consumir(tokeName);
        return token.getType();
    }

    private void consumirAlterarTipo(TokenName tokenName, SyntacticType syntacticType) {
        var tempToken = tokenAtual;
        consumir(tokenName);
        symbolTable.get(tempToken.getValue()).setType(syntacticType);
    }


    void consumirOr(String msgFail, TokenName... tokens) {
        final Optional<TokenName> token = Arrays.stream(tokens)
                .filter(this::ehToken)
                .findFirst();
        token.ifPresentOrElse(this::consumir, () -> enviaErroSintatico(msgFail));
    }

    private boolean ehToken(TokenName tokenName) {
        return tokenAtual.getName().equals(tokenName.getTokenName());
    }

    private boolean ehToken(TokenName... tokenName) {
        return Arrays.stream(tokenName).anyMatch(this::ehToken);
    }

    private void proximoToken() {
        tokenAtual = tokens.poll();
        //skip tokens de erro lexico
        if (tokenAtual instanceof TokenError) {
            proximoToken();
        }
    }

}



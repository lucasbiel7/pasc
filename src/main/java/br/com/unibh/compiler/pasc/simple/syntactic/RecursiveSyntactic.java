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

public class RecursiveSyntactic {

    private Queue<Token> tokens;

    private Token tokenAtual;

    private SymbolTable symbolTable;
    /**
     * Nós para controlar as avaliações semanticas
     */
    private Node idList;
    private Node simpleExpression;
    private Node expression;


    public RecursiveSyntactic(Queue<Token> tokens, SymbolTable symbolTable) {
        this.tokens = tokens;
        this.symbolTable = symbolTable;
    }

    public void analyse() {
        //carrega o primeiro token
        nextToken();
        //primeira regra aqui
        prog();
        consume(SpecialTokens.EOF);
    }

    private void prog() {
        consume(KeyWorld.PROGRAM);
        consume(Constants.IDENTIFIER, SyntacticType.VOID);
        body();
    }

    private void body() {
        declList();
        blockCode();
    }

    private void stmtList() {
        //Validar os first de stmt
        if (isToken(Constants.IDENTIFIER, KeyWorld.IF, KeyWorld.WHILE, KeyWorld.READ, KeyWorld.WRITE)) {
            stmt();
            consume(Symbols.SMB_SEM);
            stmtList();
        }
        // segue o baile, transição em vazio
    }

    private void stmt() {
        if (isToken(Constants.IDENTIFIER)) {
            assignStmt();
        } else if (isToken(KeyWorld.IF)) {
            ifStmt();
        } else if (isToken(KeyWorld.WHILE)) {
            whileStmt();
        } else if (isToken(KeyWorld.READ)) {
            readStmt();
        } else if (isToken(KeyWorld.WRITE)) {
            writeStmt();
        } else {
            erroSyntactical("O Comando é inválido!");
        }
    }

    private void writeStmt() {
        consume(KeyWorld.WRITE);
        simpleExpr();
    }

    private void readStmt() {
        consume(KeyWorld.READ);
        consumeWithValidateTableSymbol(Constants.IDENTIFIER);
    }

    private void whileStmt() {
        stmtPrefix();
        blockCode();
    }

    private void stmtPrefix() {
        consume(KeyWorld.WHILE);
        expressionInParenteses();
    }

    private void ifStmt() {
        consume(KeyWorld.IF);
        expressionInParenteses();
        blockCode();
        ifStmtLinha();
    }

    private void expressionInParenteses() {
        consume(Symbols.SMB_OPA);
        expression();
        consume(Symbols.SMB_CPA);
    }

    private void blockCode() {
        consume(Symbols.SMB_OBC);
        stmtList();
        consume(Symbols.SMB_CBC);
    }

    private void ifStmtLinha() {
        if (isToken(KeyWorld.ELSE)) {
            consume(KeyWorld.ELSE);
            blockCode();
        }
        //segue o baile, transicao em vazio
    }

    private void expression() {
        simpleExpr();
        expressionLine();
    }

    private void expressionLine() {
        if (isToken(KeyWorld.AND, KeyWorld.OR)) {
            logOp();
            simpleExpr();
            expressionLine();
        }
    }

    private void logOp() {
        consume("Operador inválido para unir expressões, esperado and ou or",
                KeyWorld.AND,
                KeyWorld.OR);
    }

    private void assignStmt() {
        final SyntacticType typeIdentifier = consumeWithValidateTableSymbol(Constants.IDENTIFIER);
        consume(Operators.OP_ATRIB);
        simpleExpr();
        if (typeIdentifier != simpleExpression.getType()) {
            erroSemantic("Atribuição incompatível");
        }
    }


    //TODO precisa retorna o tipo.
    private void simpleExpr() {
        term();
        simpleExprLine();
    }

    private void simpleExprLine() {
        if (isToken(Operators.OP_EQ, Operators.OP_GT, Operators.OP_GE, Operators.OP_LT, Operators.OP_LE, Operators.OP_NE)) {
            relop();
            term();
            simpleExprLine();
        }
        // segue o baile, transicao em vazio
    }

    private void relop() {
        consume("Operadores inválidos",
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
        if (isToken(Operators.OP_AD, Operators.OP_MIN)) {
            addOp();
            factorB();
            termLine();
        }
    }

    private void addOp() {
        consume("Operador inválido, utilize + ou - ",
                Operators.OP_AD,
                Operators.OP_MIN
        );
    }

    private void factorB() {
        factorA();
        factorBLine();
    }

    private void factorBLine() {
        if (isToken(Operators.OP_MUL, Operators.OP_DIV)) {
            mulop();
            factorA();
            factorBLine();
        }
    }

    private void mulop() {
        consume("Operador inválido, utilize * ou / ",
                Operators.OP_MUL,
                Operators.OP_DIV
        );
    }

    private void factorA() {
        if (isToken(KeyWorld.NOT)) {
            consume(KeyWorld.NOT);
        }
        factor();
    }

    private void factor() {
        if (isToken(Constants.IDENTIFIER)) {
            consume(Constants.IDENTIFIER);
        } else if (isToken(Symbols.SMB_OPA)) {
            expressionInParenteses();
        } else if (isToken(Constants.NUM_CONST, Constants.CHAR_CONST)) {
            constant();
        } else {
            erroSyntactical("Fator inválido!");
        }
    }

    private void constant() {
        consume("Constante era esperada",
                Constants.NUM_CONST,
                Constants.CHAR_CONST
        );
    }

    private void declList() {
        if (isToken(KeyWorld.NUM, KeyWorld.CHAR)) {
            decl();
            consume(Symbols.SMB_SEM);
            declList();
        }
        // pode passar batido, porque ele aceita vazio
    }

    private void decl() {
        type();
        idList();
    }

    private void idList() {
        consume(Constants.IDENTIFIER, idList.getType());
        idListLinha();
    }

    private void idListLinha() {
        if (isToken(Symbols.SMB_COM)) {
            consume(Symbols.SMB_COM);
            idList();
        }
        // Segue o baile, transição em vazio
    }

    private void type() {
        if (isToken(KeyWorld.NUM)) {
            consume(KeyWorld.NUM);
            idList = new Node(SyntacticType.NUM);
        } else if (isToken(KeyWorld.CHAR)) {
            consume(KeyWorld.CHAR);
            idList = new Node(SyntacticType.CHAR);
        } else {
            erroSyntactical(MessageFormat.format("O token {0} não é um tipo válido", tokenAtual.getValue()));
        }
    }

    //TODO melhorar tratamento de exceção
    private void erroSyntactical(String mensagem) {
        throw new RuntimeException(MessageFormat.format("[{0},{1}] Erro análise sintática: {2}",
                tokenAtual.getLine(), tokenAtual.getColumn(), mensagem)
        );
    }

    private void erroSemantic(String mensagem) {
        throw new RuntimeException(MessageFormat.format("[{0},{1}] Erro análise semântica: {2}",
                tokenAtual.getLine(), tokenAtual.getColumn(), mensagem)
        );
    }

    private void consume(TokenName tokenName) {
        if (!isToken(tokenName)) {
            erroSyntactical(MessageFormat.format(
                    "token esperado {0} = {1}",
                    tokenName.getTokenName(),
                    tokenName.getValue()));
        }
        nextToken();
    }

    private SyntacticType consumeWithValidateTableSymbol(TokenName tokeName) {
        Token temp = tokenAtual;
        final Token token = symbolTable.get(temp.getValue());
        if (Objects.isNull(token.getType())) {
            erroSemantic(MessageFormat.format(
                    "O identificador {0} não foi declarado",
                    token.getValue()
            ));
        }
        consume(tokeName);
        return token.getType();
    }

    private void consume(TokenName tokenName, SyntacticType syntacticType) {
        var tempToken = tokenAtual;
        consume(tokenName);
        symbolTable.get(tempToken.getValue()).setType(syntacticType);
    }


    void consume(String msgFail, TokenName... tokens) {
        final Optional<TokenName> token = Arrays.stream(tokens)
                .filter(this::isToken)
                .findFirst();
        token.ifPresentOrElse(this::consume, () -> erroSyntactical(msgFail));
    }

    private boolean isToken(TokenName tokenName) {
        return tokenAtual.getName().equals(tokenName.getTokenName());
    }

    private boolean isToken(TokenName... tokenName) {
        return Arrays.stream(tokenName).anyMatch(this::isToken);
    }

    private void nextToken() {
        tokenAtual = tokens.poll();
        //skip tokens de erro lexico
        if (tokenAtual instanceof TokenError) {
            nextToken();
        }
    }

}



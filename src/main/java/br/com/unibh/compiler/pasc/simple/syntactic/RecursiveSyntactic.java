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
import lombok.extern.java.Log;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;

@Log
public class RecursiveSyntactic {


    public static final int MAX_ERRO_SINTATICO = 5;
    public static final int MAX_ERRO_SEMANTICO = 5;

    private Queue<Token> tokens;

    int erroSintatico = 0;
    int erroSemantico = 0;

    private Token tokenAtual;

    private SymbolTable symbolTable;

    private Consumer<Token> message;


    public RecursiveSyntactic(Queue<Token> tokens, SymbolTable symbolTable, Consumer<Token> message) {
        this.tokens = tokens;
        this.symbolTable = symbolTable;
        this.message = message;
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
        final Node simpleExpr = simpleExpr();
        if (simpleExpr.getType() != SyntacticType.CHAR && simpleExpr.getType() != SyntacticType.NUM) {
            erroSemantic("Incompatibilidade para impressão de valores, deve ser num ou char");
        }
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
        final Node expression = expressionInParenteses();
        validateExpressionLogic(expression);
    }

    private void ifStmt() {
        consume(KeyWorld.IF);
        final Node expression = expressionInParenteses();
        validateExpressionLogic(expression);
        blockCode();
        ifStmtLinha();
    }

    private void validateExpressionLogic(Node expression) {
        if (expression.getType() != SyntacticType.BOOL) {
            erroSemantic("Expressão lógica mal formada");
        }
    }

    private Node expressionInParenteses() {
        consume(Symbols.SMB_OPA);
        final Node expression = expression();
        consume(Symbols.SMB_CPA);
        return expression;
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

    private Node expression() {
        Node expression = new Node();
        final Node simpleExpr = simpleExpr();
        final Node expressionLine = expressionLine();
        if (expressionLine.getType() == SyntacticType.VOID) {
            expressionLine.setType(simpleExpr.getType());
        } else if (expressionLine.getType() == simpleExpr.getType() && simpleExpr.getType() == SyntacticType.BOOL) {
            expression.setType(SyntacticType.BOOL);
        } else {
            expression.setType(SyntacticType.ERROR);
        }
        return expression;
    }

    private Node expressionLine() {
        Node expressionLine = new Node(SyntacticType.VOID);
        if (isToken(KeyWorld.AND, KeyWorld.OR)) {
            logOp();
            final Node simpleExpr = simpleExpr();
            final Node expressionLineFilho = expressionLine();
            if (expressionLineFilho.getType() == SyntacticType.VOID && simpleExpr.getType() == SyntacticType.BOOL) {
                expressionLine.setType(SyntacticType.BOOL);
            } else if (expressionLineFilho.getType() == simpleExpr.getType() && simpleExpr.getType() == SyntacticType.BOOL) {
                expressionLine.setType(SyntacticType.BOOL);
            } else {
                expressionLine.setType(SyntacticType.ERROR);
            }
        }
        return expressionLine;
    }

    private void logOp() {
        consume("Operador inválido para unir expressões, esperado and ou or",
                KeyWorld.AND,
                KeyWorld.OR);
    }

    private void assignStmt() {
        final SyntacticType typeIdentifier = consumeWithValidateTableSymbol(Constants.IDENTIFIER);
        consume(Operators.OP_ATRIB);
        final Node simpleExpr = simpleExpr();
        if (typeIdentifier != simpleExpr.getType()) {
            erroSemantic(MessageFormat.format("Atribuição incompatível tipo do identificador {0}, expressão {1}",
                    typeIdentifier,
                    simpleExpr.getType()
            ));
        }
    }

    private Node simpleExpr() {
        Node simpleExpr = new Node();
        final Node term = term();
        final Node simpleExprLine = simpleExprLine();
        if (simpleExprLine.getType() == SyntacticType.VOID) {
            simpleExpr.setType(term.getType());
        } else if (simpleExprLine.getType() == term.getType() && simpleExprLine.getType() == SyntacticType.NUM) {
            simpleExpr.setType(SyntacticType.BOOL);
        } else {
            simpleExpr.setType(SyntacticType.ERROR);
        }
        return simpleExpr;
    }

    private Node simpleExprLine() {
        Node simpleExprLine = new Node(SyntacticType.VOID);
        if (isToken(Operators.OP_EQ, Operators.OP_GT, Operators.OP_GE, Operators.OP_LT, Operators.OP_LE, Operators.OP_NE)) {
            relop();
            final Node term = term();
            final Node simpleExprLineFilho = simpleExprLine();
            evaluateTypeExpressionWithRecursive(simpleExprLine, term, simpleExprLineFilho);
        }
        return simpleExprLine;
        // segue o baile, transicao em vazio
    }

    public void evaluateTypeExpressionWithRecursive(Node actualNode, Node primaryTerm, Node recursiveNode) {
        if (recursiveNode.getType() == SyntacticType.VOID && primaryTerm.getType() == SyntacticType.NUM) {
            actualNode.setType(SyntacticType.NUM);
        } else if (recursiveNode.getType() == primaryTerm.getType() && primaryTerm.getType() == SyntacticType.NUM) {
            actualNode.setType(SyntacticType.NUM);
        } else {
            actualNode.setType(SyntacticType.ERROR);
        }
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


    private Node term() {
        Node term = new Node();
        final Node factorB = factorB();
        final Node termLine = termLine();
        if (termLine.getType() == SyntacticType.VOID) {
            term.setType(factorB.getType());
        } else if (termLine.getType() == factorB.getType() && termLine.getType() == SyntacticType.NUM) {
            term.setType(SyntacticType.NUM);
        } else {
            term.setType(SyntacticType.ERROR);
        }
        return term;
    }

    private Node termLine() {
        Node termLine = new Node(SyntacticType.VOID);
        if (isToken(Operators.OP_AD, Operators.OP_MIN)) {
            addOp();
            final Node factorB = factorB();
            final Node termLineFilho = termLine();
            evaluateTypeExpressionWithRecursive(termLine, factorB, termLineFilho);
        }
        return termLine;
    }

    private void addOp() {
        consume("Operador inválido, utilize + ou - ",
                Operators.OP_AD,
                Operators.OP_MIN
        );
    }

    private Node factorB() {
        Node factorB = new Node();
        final Node factorA = factorA();
        final Node factorBLine = factorBLine();
        if (factorBLine.getType() == SyntacticType.VOID) {
            factorB.setType(factorA.getType());
        } else if (factorBLine.getType() == factorA.getType() && factorBLine.getType() == SyntacticType.NUM) {
            factorB.setType(SyntacticType.NUM);
        } else {
            factorB.setType(SyntacticType.ERROR);
        }
        return factorB;
    }

    private Node factorBLine() {
        Node factorBLine = new Node(SyntacticType.VOID);
        if (isToken(Operators.OP_MUL, Operators.OP_DIV)) {
            mulop();
            final Node factorA = factorA();
            final Node factorBLineFilho = factorBLine();
            evaluateTypeExpressionWithRecursive(factorBLine, factorA, factorBLineFilho);
        }
        return factorBLine;
    }

    private void mulop() {
        consume("Operador inválido, utilize * ou / ",
                Operators.OP_MUL,
                Operators.OP_DIV
        );
    }

    private Node factorA() {
        Node factorA = new Node();
        if (isToken(KeyWorld.NOT)) {
            consume(KeyWorld.NOT);
            final Node factor = factor();
            if (factor.getType() != SyntacticType.BOOL) {
                factorA.setType(SyntacticType.ERROR);
            } else {
                factorA.setType(SyntacticType.BOOL);
            }
        } else {
            final Node factor = factor();
            factorA.setType(factor.getType());
        }
        return factorA;
    }

    private Node factor() {
        Node factor = new Node();
        if (isToken(Constants.IDENTIFIER)) {
            final SyntacticType syntacticType = consumeWithValidateTableSymbol(Constants.IDENTIFIER);
            factor.setType(syntacticType);
        } else if (isToken(Symbols.SMB_OPA)) {
            final Node expression = expressionInParenteses();
            factor.setType(expression.getType());
        } else if (isToken(Constants.NUM_CONST, Constants.CHAR_CONST)) {
            factor.setType(constant());
        } else {
            erroSyntactical("Fator inválido!");
        }
        return factor;
    }

    private SyntacticType constant() {
        SyntacticType type = isToken(Constants.NUM_CONST) ? SyntacticType.NUM : SyntacticType.CHAR;
        consume("Constante era esperada",
                Constants.NUM_CONST,
                Constants.CHAR_CONST
        );
        return type;
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
        idList(type());
    }

    private void idList(Node type) {
        consume(Constants.IDENTIFIER, type.getType());
        idListLinha(type);
    }

    private void idListLinha(Node type) {
        if (isToken(Symbols.SMB_COM)) {
            consume(Symbols.SMB_COM);
            idList(type);
        }
        // Segue o baile, transição em vazio
    }

    private Node type() {
        Node typeNode = new Node();
        if (isToken(KeyWorld.NUM)) {
            typeNode.setType(SyntacticType.NUM);
            consume(KeyWorld.NUM);
        } else if (isToken(KeyWorld.CHAR)) {
            typeNode.setType(SyntacticType.CHAR);
            consume(KeyWorld.CHAR);
        } else {
            erroSyntactical(MessageFormat.format("O token {0} não é um tipo válido", tokenAtual.getValue()));
        }
        return typeNode;
    }

    private void erroSyntactical(String mensagem) {
        erroSintatico++;
        log.severe(MessageFormat.format("[{0},{1}] Erro análise sintática: {2}",
                tokenAtual.getLine(), tokenAtual.getColumn(), mensagem)
        );
        if (erroSintatico > MAX_ERRO_SINTATICO) {
            throw new RuntimeException("A analise foi interrompida devido ao número máximo de erros sintáticos");
        }
    }

    private void erroSemantic(String mensagem) {
        erroSemantico++;
        log.severe(MessageFormat.format("[{0},{1}] Erro análise semântica: {2}",
                tokenAtual.getLine(), tokenAtual.getColumn(), mensagem)
        );
        if (erroSemantico > MAX_ERRO_SEMANTICO) {
            throw new RuntimeException("A analise foi interrompida devido ao número máximo de erros semânticos");
        }
    }

    private void consume(TokenName tokenName) {
        if (!isToken(tokenName)) {
            String defaultValue;
            try {
                defaultValue = tokenName.getValue();
            } catch (Exception e) {
                defaultValue = "";
            }
            erroSyntactical(MessageFormat.format(
                    "Token esperado: {0} token lido: {1}",
                    tokenName.getTokenName(),
                    tokenAtual.getName()
            ));
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
        message.accept(tokenAtual);
        //skip tokens de erro lexico
        if (tokenAtual instanceof TokenError) {
            nextToken();
        }
    }

}



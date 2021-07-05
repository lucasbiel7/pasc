package br.com.unibh.compiler.pasc.lexic.model;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SymbolTable {

    private Map<String, Token> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
        loadKeyWorld();
    }

    public Stream<Pair<String, Token>> stream() {
        return this.symbols.keySet().stream().map(s -> Pair.of(s, symbols.get(s)));
    }

    public int size() {
        return symbols.size();
    }

    /**
     * Carrega as palavras reservadas na tabela de simbolos
     */
    private void loadKeyWorld() {
        Arrays.stream(KeyWorld.values()).forEach(keyWorld -> add(keyWorld.getValue().toLowerCase(),
                Token.builder()
                        .value(keyWorld.getValue())
                        .name(keyWorld.getTokenName())
                        .build()));
    }

    public void add(String value, Token token) {
        if (symbols.containsKey(value.toLowerCase())) {
            final Token pastToken = symbols.get(value.toLowerCase());
            // Ajuste para apenas atualizar linha e coluna do token na tabela de simbolo, assim evitar apagando o tipo dele
            pastToken.setColumn(token.getColumn());
            pastToken.setLine(token.getLine());
        } else {
            symbols.put(value.toLowerCase(), Token.builder()
                    .name(token.getName())
                    .value(token.getValue())
                    .line(token.getLine())
                    .column(token.getColumn())
                    .build());
        }
    }

    public boolean hasKey(String key) {
        return this.symbols.containsKey(key.toLowerCase());
    }

    public Token get(String value) {
        return symbols.get(value.toLowerCase());
    }
}

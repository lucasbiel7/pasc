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
        Arrays.stream(KeyWorld.values()).forEach(keyWorld -> add(keyWorld.getValue().toLowerCase(), Token.builder().build()));
    }

    //TODO verificar o que será avaliado no value
    public void add(String value, Token token) {
        symbols.put(value.toLowerCase(), token);
    }

    public boolean hasKey(String key) {
        return this.symbols.containsKey(key.toLowerCase());
    }
}

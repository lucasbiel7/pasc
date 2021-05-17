package br.com.unibh.compiler.pasc.lexic.model;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public class SymbolTable {

    private Map<String, String> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
        loadKeyWorld();
    }

    public Stream<Pair<String, String>> stream() {
        return this.symbols.keySet().stream().map(s -> Pair.of(s, symbols.get(s)));
    }

    /**
     * Carrega as palavras reservadas na tabela de simbolos
     */
    private void loadKeyWorld() {
        Arrays.stream(KeyWorld.values()).forEach(keyWorld -> getSymbols().putIfAbsent(keyWorld.getValue(), keyWorld.getTokenName()));
    }

    //TODO verificar o que será avaliado no value
    public void add(String value) {
        symbols.putIfAbsent(value, Constants.IDENTIFIER.getTokenName());
    }
}

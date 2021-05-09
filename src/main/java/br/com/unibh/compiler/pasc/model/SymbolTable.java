package br.com.unibh.compiler.pasc.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class SymbolTable {

    private Map<String, String> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
        loadKeyWorld();
    }

    private void loadKeyWorld() {
        Arrays.stream(KeyWorld.values()).forEach(keyWorld -> getSymbols().putIfAbsent(keyWorld.getValue(), ""));
    }


}

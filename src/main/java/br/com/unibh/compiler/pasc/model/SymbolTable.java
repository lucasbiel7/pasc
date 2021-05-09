package br.com.unibh.compiler.pasc.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SymbolTable {

    private Map<String, String> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }
    
}

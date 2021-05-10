package br.com.unibh.compiler.pasc.lexic.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@RequiredArgsConstructor
@Getter
public enum Symbols implements TokenName {

    SMB_OBC("SMB_OBC", "{"),
    SMB_CBC("SMB_CBC", "}"),
    SMB_OPA("SMB_OPA", "("),
    SMB_CPA("SMB_CPA", ")"),
    SMB_COM("SMB_COM", ","),
    SMB_SEM("SMB_SEM", ";");

    private final String tokenName;
    private final String value;


    private static final class CacheSymbols {

        private static final Map<Character, Symbols> symbolsCache;

        static {
            symbolsCache = Arrays.stream(Symbols.values()).collect(Collectors.toMap(CacheSymbols::symbol, identity()));
        }

        private static char symbol(Symbols symbols) {
            return symbols.getValue().charAt(0);
        }

    }

    public static boolean isSymbol(char value) {
        return CacheSymbols.symbolsCache.containsKey(value);
    }

    public static Symbols getSymbol(char value) {
        return CacheSymbols.symbolsCache.get(value);
    }

}

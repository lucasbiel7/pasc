package br.com.unibh.compiler.pasc.lexic.model;

import br.com.unibh.compiler.pasc.lexic.configuration.FileConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SpecialTokens implements TokenName {

    EOF(FileConfig.EOF_TOKEN_NAME, FileConfig.EOF_TOKEN_NAME);

    private final String tokenName;
    private final String value;
}

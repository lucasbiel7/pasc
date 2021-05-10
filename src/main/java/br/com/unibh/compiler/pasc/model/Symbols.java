package br.com.unibh.compiler.pasc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

}

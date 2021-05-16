package br.com.unibh.compiler.pasc.lexic.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LanguageLexer {

    private static LanguageLexer instance;

    @Getter
    @Setter
    private static Locale defaultLocale = Locale.getDefault();


    public static LanguageLexer getInstance() {
        if (instance == null) {
            instance = new LanguageLexer();
        }
        return instance;
    }

    private ResourceBundle resourceBundle;


    private ResourceBundle getMessages(Locale locale) {
        return ResourceBundle.getBundle("messages", locale);
    }

    @SafeVarargs
    public final String message(String messageCode, Object... args) {
        return MessageFormat.format(getMessages(defaultLocale).getString(messageCode), args);
    }

}

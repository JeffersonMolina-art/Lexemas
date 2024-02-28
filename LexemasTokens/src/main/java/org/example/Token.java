package org.example;

public class Token {
    private final String lexema;
    private final String token;

    public Token(String lexema, String token){
        this.lexema = lexema;
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public String getToken() {
        return token;
    }
}

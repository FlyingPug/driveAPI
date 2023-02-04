package ru.cft.shift.intensive.cool_drive.dto;

import javax.validation.constraints.NotEmpty;

/**
 * класс для взаимодействия с токеном
 */
public class Token {
    @NotEmpty(message = "token should not be empty")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Token(String token) {
        this.token = token;
    }
}

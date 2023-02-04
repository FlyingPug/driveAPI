package ru.cft.shift.intensive.cool_drive.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * данные для ауентификации
 */
public class AuthRequest {
    @NotEmpty(message = "{account.username.not-empty}")
    @Size(min = 6, max = 30)
    private String username;
    @NotEmpty(message = "{account.password.not-empty}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

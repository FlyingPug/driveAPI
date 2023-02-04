package ru.cft.shift.intensive.cool_drive.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Данные пользователя
 */
public class Account implements Serializable {
    @NotEmpty(message = "{account.username.not-empty}")
    @Size(min = 6, max = 30)
    private String username;
    @NotEmpty(message = "{account.password.not-empty}")
    private String password;
    @NotEmpty(message = "{account.password.not-empty}")
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}

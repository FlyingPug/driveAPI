package ru.cft.shift.intensive.template.dto;

/**
 * Данные пользователя для аутентификации
 */
public interface AccountDetails {
    String getUsername();

    AccountDetails clearPassword();
}

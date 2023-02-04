package ru.cft.shift.intensive.cool_drive.dto;

/**
 * Данные пользователя для аутентификации
 */
public interface AccountDetails {
    String getUsername();

    String getPassword();

    AccountDetails clearPassword();
}

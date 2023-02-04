package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Пользователь не авторизован
 */
public class UserNotAuthorized extends RuntimeException {
    public UserNotAuthorized(String message) {
        super(message);
    }
}

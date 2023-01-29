package ru.cft.shift.intensive.template.exception;

/**
 * Исключение, выбрасываемое при отсутвии пользователя в системе
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

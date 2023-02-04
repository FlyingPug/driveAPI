package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Исключение, выбрасываемое при отсутвии пользователя в системе
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

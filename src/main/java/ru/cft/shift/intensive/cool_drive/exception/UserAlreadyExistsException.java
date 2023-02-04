package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Исключение, выбрасываемое при добавлении уже существующего пользователя
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}

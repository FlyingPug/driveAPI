package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Исключение, выбрасываемое при несоответствии паролей
 */
public class PasswordNotConfirmedException extends RuntimeException {
    public PasswordNotConfirmedException(String msg) {
        super(msg);
    }
}

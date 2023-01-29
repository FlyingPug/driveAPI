package ru.cft.shift.intensive.template.exception;

/**
 * Исключение, выбрасываемое при несоответствии паролей
 */
public class PasswordNotConfirmedException extends RuntimeException {
    public PasswordNotConfirmedException(String msg) {
        super(msg);
    }
}

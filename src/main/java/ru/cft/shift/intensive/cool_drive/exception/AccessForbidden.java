package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Исключение, если нет доступа к файлу
 */
public class AccessForbidden extends RuntimeException
{
    public AccessForbidden(String message) {
        super(message);
    }
}

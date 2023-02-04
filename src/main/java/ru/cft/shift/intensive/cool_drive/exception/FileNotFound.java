package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Файл не найден
 */
public class FileNotFound extends RuntimeException {
    public FileNotFound(String message) {
        super(message);
    }
}

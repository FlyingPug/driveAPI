package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Файл уже существует
 */
public class FileAlreadyExists extends RuntimeException
{
    public FileAlreadyExists(String message) {
        super(message);
    }
}

package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Папка не найдена
 */
public class DirectoryNotFound extends RuntimeException
{
    public DirectoryNotFound(String message) { super(message); }
}

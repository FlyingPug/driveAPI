package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Исключение, если создаваемая директория уже существует
 */
public class DirectoryAlreadyExists extends RuntimeException
{
    public DirectoryAlreadyExists(String message) {
        super(message);
    }
}

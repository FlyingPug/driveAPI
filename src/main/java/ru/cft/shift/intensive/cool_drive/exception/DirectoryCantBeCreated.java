package ru.cft.shift.intensive.cool_drive.exception;

/**
 * Директория (папка) не может быть создана
 */
public class DirectoryCantBeCreated extends RuntimeException{
    public DirectoryCantBeCreated(String message) {
        super(message);
    }
}

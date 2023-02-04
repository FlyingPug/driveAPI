package ru.cft.shift.intensive.cool_drive.service;

import org.springframework.web.multipart.MultipartFile;
import ru.cft.shift.intensive.cool_drive.dto.FileDto;
import ru.cft.shift.intensive.cool_drive.repository.entity.Account;
import ru.cft.shift.intensive.cool_drive.repository.entity.File;

import java.util.List;

/**
 * сервис для работы с файлами
 */
public interface FileService {
    /**
     * получить файл по id
     */
    File getFileById(Long fileId);

    /**
     * получить полный путь файла
     */
    java.io.File getFilePath(File file);

    /**
     * Основное предназначение метода - смена имени файла, однако также файл можно заменить, изменить стостояние его приватности
     */
    void changeFileName(FileDto fileDto);

    /**
     * удаление файла
     */
    void removeFile(Long fileId);

    /**
     * загрузка файла
     */
    File loadFile(Account owner, Long dirId, MultipartFile fileContent);

    /**
     * поиск файла по имени TODO имплеминтировать этот метод в контроллер
     */
    List<File> searchFileByName(String name);
}

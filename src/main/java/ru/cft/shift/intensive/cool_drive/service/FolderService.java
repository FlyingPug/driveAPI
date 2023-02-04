package ru.cft.shift.intensive.cool_drive.service;

import ru.cft.shift.intensive.cool_drive.dto.FoldersAndFiles;
import ru.cft.shift.intensive.cool_drive.repository.entity.Account;
import ru.cft.shift.intensive.cool_drive.repository.entity.Directory;

/**
 * Интерфейс для работы с директориями(папками)
 * Да, Folder и Directory в коде это одно и тоже
 */
public interface FolderService {
    /**
     * Получить папку по id :/
     */
    Directory getDirectoryById(Long dirId);

    /**
     * создание папки
     */
    Directory createFolder(Account owner, Long parentId, String name);

    /**
     * удаление папки
     */
    void deleteFolder(Long dirId);

    /**
     * получить списки файлов и папок директории по сортировке 0 - сортировка файлов по имени, 1 - тоже самое, но убывание
     */
    FoldersAndFiles getContent(Long dirId, Long sortType);

    /**
     * переименовать папку
     */
    void renameDirectory(Long dirId, String name);
    /**
     * получить путь к директории. Собирает его из родительских директорий
     */
    String getPathToDir(Directory dir);
    /**
     * Получение полного пути папки. К обычному пути добавляется root папка, имя пользователя и имя самой папки
     */
    String getFullPath(String ownerName,String directoryPath,String directoryName);
}

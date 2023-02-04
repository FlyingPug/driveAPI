package ru.cft.shift.intensive.cool_drive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.shift.intensive.cool_drive.repository.entity.File;

import java.util.List;

/**
 * интерфейс для работы с файлами в бд (нет, в бд хранится только описание файлов, а не сами файлы)
 */
public interface FileRepository extends JpaRepository<File, Long> {
    /**
     * поиск в бд файла по имени
     */
    List<File> findAllByName(String name);
}

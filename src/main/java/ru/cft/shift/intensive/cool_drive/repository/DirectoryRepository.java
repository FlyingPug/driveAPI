package ru.cft.shift.intensive.cool_drive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.shift.intensive.cool_drive.repository.entity.Directory;

/**
 * интерфейс для работы с папками
 */
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
}

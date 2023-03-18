package ru.cft.shift.intensive.cool_drive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.cool_drive.exception.AccessForbidden;
import ru.cft.shift.intensive.cool_drive.repository.entity.File;
import ru.cft.shift.intensive.cool_drive.service.FileService;

import javax.validation.Valid;

/**
 * api для доступа к публичным файлам
 */
@RestController
@RequestMapping(value = "driveAPI/public", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicAccessController {
    private final FileService fileService;

    @Autowired
    public PublicAccessController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "files/{fileId}",produces = MediaType.ALL_VALUE)
    public FileSystemResource load(@Valid @PathVariable Long fileId) {
        File file = fileService.getFileById(fileId);
        if (file.isPublic() == 1)
            return new FileSystemResource(fileService.getFilePath(file));
        throw new AccessForbidden("file doesn't exist or not public");
    }
}

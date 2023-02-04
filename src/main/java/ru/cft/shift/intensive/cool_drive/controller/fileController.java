package ru.cft.shift.intensive.cool_drive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;
import ru.cft.shift.intensive.cool_drive.dto.FileDto;
import ru.cft.shift.intensive.cool_drive.dto.FileIdDto;
import ru.cft.shift.intensive.cool_drive.exception.UserNotAuthorized;
import ru.cft.shift.intensive.cool_drive.repository.entity.Account;
import ru.cft.shift.intensive.cool_drive.repository.entity.File;
import ru.cft.shift.intensive.cool_drive.service.FileService;
import ru.cft.shift.intensive.cool_drive.service.UserDetailsService;

import java.util.Objects;

/**
 * api для взаимодействия с файлами
 */
@RestController
@RequestMapping(value = "driveAPI/users/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class fileController {
    private final FileService fileService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public fileController(FileService fileService, UserDetailsService userDetailsService) {
        this.fileService = fileService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileIdDto> upload(long directoryID,MultipartFile file, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        File dir = fileService.loadFile((Account) owner, directoryID, file);
        return ResponseEntity.ok(new FileIdDto(dir.getId()));
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // я не знаю почему, но через fileDto не получается передать файл (его значение там всегда = null)
    public ResponseEntity<Void> change(Long fileId, String name, int isPublic, MultipartFile fileData, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        File file = fileService.getFileById(fileId);
        if (!(Objects.equals(file.getAccount().getUsername(), owner.getUsername())))
            throw new UserNotAuthorized("wrong token");

        fileService.changeFileName(new FileDto(fileId,name,isPublic,fileData));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "{fileId}")
    public ResponseEntity<Void> delete(@PathVariable Long fileId, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        File file = fileService.getFileById(fileId);
        if (!(Objects.equals(file.getAccount().getUsername(), owner.getUsername())))
            throw new UserNotAuthorized("wrong token");

        fileService.removeFile(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "{fileId}",produces = MediaType.ALL_VALUE)
    public FileSystemResource load(@PathVariable Long fileId, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        File file = fileService.getFileById(fileId);
        if (!(Objects.equals(file.getAccount().getUsername(), owner.getUsername())))
            throw new UserNotAuthorized("wrong token");
        return new FileSystemResource(fileService.getFilePath(file));
    }
}

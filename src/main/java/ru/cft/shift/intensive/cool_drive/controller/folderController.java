package ru.cft.shift.intensive.cool_drive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;
import ru.cft.shift.intensive.cool_drive.dto.FoldersAndFiles;
import ru.cft.shift.intensive.cool_drive.dto.FolderDto;
import ru.cft.shift.intensive.cool_drive.dto.FolderIdDto;
import ru.cft.shift.intensive.cool_drive.exception.AccessForbidden;
import ru.cft.shift.intensive.cool_drive.exception.UserNotAuthorized;
import ru.cft.shift.intensive.cool_drive.repository.entity.Account;
import ru.cft.shift.intensive.cool_drive.repository.entity.Directory;
import ru.cft.shift.intensive.cool_drive.service.FolderService;
import ru.cft.shift.intensive.cool_drive.service.UserDetailsService;

import java.util.Objects;

/**
 * api для взаимодействия с папками
 */
@RestController
@RequestMapping(value = "driveAPI/users/folders", produces = MediaType.APPLICATION_JSON_VALUE)
public class folderController {
    private final FolderService folderService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public folderController(FolderService folderService, UserDetailsService userDetailsService) {
        this.folderService = folderService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FolderIdDto> create(@RequestBody FolderDto folder, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        Directory parentDir = folderService.getDirectoryById(folder.getId());
        if (parentDir.getId() != 1 && !(Objects.equals( parentDir.getAccount().getUsername(), owner.getUsername())))
            throw new AccessForbidden("wrong token");
        Directory dir = folderService.createFolder((Account) owner, folder.getId(), folder.getName());
        FolderIdDto folderIdDto = new FolderIdDto(dir.getId());
        return ResponseEntity.ok(folderIdDto);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> change(@RequestBody FolderDto folder, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        Directory dir = folderService.getDirectoryById(folder.getId());
        if (!(Objects.equals(dir.getAccount().getUsername(), owner.getUsername())))
            throw new UserNotAuthorized("wrong token");
        folderService.renameDirectory(folder.getId(), folder.getName());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FoldersAndFiles> getContent(@RequestParam Long directoryId, @RequestParam Long sortType, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        Directory dir = folderService.getDirectoryById(directoryId);
        if (!(Objects.equals(dir.getAccount().getUsername(), owner.getUsername())))
            throw new UserNotAuthorized("wrong token");
        FoldersAndFiles foldersAndFiles = folderService.getContent(directoryId, sortType);
        return ResponseEntity.ok(foldersAndFiles);
    }

    @DeleteMapping(value = "{directoryId}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long directoryId, @RequestHeader("Authorization") String token) {
        AccountDetails owner = userDetailsService.getByToken(token);
        Directory dir = folderService.getDirectoryById(directoryId);
        if (!(Objects.equals(dir.getAccount().getUsername(), owner.getUsername())))
            throw new UserNotAuthorized("wrong token");
        folderService.deleteFolder(directoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

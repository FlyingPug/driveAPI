package ru.cft.shift.intensive.cool_drive.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.shift.intensive.cool_drive.dto.FileNameAndId;
import ru.cft.shift.intensive.cool_drive.dto.FolderDto;
import ru.cft.shift.intensive.cool_drive.dto.FoldersAndFiles;
import ru.cft.shift.intensive.cool_drive.exception.DirectoryAlreadyExists;
import ru.cft.shift.intensive.cool_drive.exception.DirectoryCantBeCreated;
import ru.cft.shift.intensive.cool_drive.exception.DirectoryNotFound;
import ru.cft.shift.intensive.cool_drive.repository.DirectoryRepository;
import ru.cft.shift.intensive.cool_drive.repository.entity.Account;
import ru.cft.shift.intensive.cool_drive.repository.entity.Directory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FolderServiceImpl implements FolderService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final DirectoryRepository directoryRepository;


    @Value("${storage.rootDirectory}")
    private String rootDirectoryName; // Название папки, в которой хранится все

    @Autowired
    public FolderServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Directory getDirectoryById(Long dirId) {
        Optional<Directory> directory = directoryRepository.findById(dirId);
        if (directory.isEmpty()) {
            log.warn("directory not found");
            throw new DirectoryNotFound("directory not found");
        }
        return directory.get();
    }

    // собирает путь к определенной папке (выводит в формате /dir/another_dir)
    @Transactional
    public String getPathToDir(Directory dir) {
        StringBuilder path = new StringBuilder();
        while (dir.getId() > 1) {
            path.insert(0, "/" + dir.getName());
            dir = dir.getParentDirectory();
        }
        return path.toString();
    }

    public String getFullPath(String ownerName, String directoryPath, String directoryName) {
        return "/" + rootDirectoryName + "/" + ownerName + directoryPath + "/" + directoryName;
    }

    @Override
    @Transactional
    public Directory createFolder(Account owner, Long parentId, String name) {
        if(parentId < 1L) parentId = 1L;
        String directoryPath = getPathToDir(directoryRepository.findById(parentId).get());
        File root = new File(getFullPath(owner.getUsername(), directoryPath, name));
        if(!root.exists())
        {
            if(!root.mkdirs()) {
                log.error("some error in creating of a folder");
                throw new DirectoryCantBeCreated("directory cant be created");
            }
        }
        else {
            log.warn("directory already exists");
            throw new DirectoryAlreadyExists("Directory already exists");
        }
        Directory dir = new Directory();
        dir.setName(name);
        dir.setAccount(owner);
        dir.setParentDirectory(directoryRepository.findById(parentId).get());
        return directoryRepository.save(dir);
    }

    @Override
    @Transactional
    public void deleteFolder(Long dirId) {
        Optional<Directory> dir = directoryRepository.findById(dirId);
        if (dir.isEmpty()) throw new DirectoryNotFound("directory not found");

        try {
            FileUtils.deleteDirectory(new File(getFullPath(
                    dir.get().getAccount().getUsername(),
                    getPathToDir(dir.get().getParentDirectory()),
                    dir.get().getName())));
        } catch (Exception e) {
            throw new DirectoryNotFound(e.getMessage());
        }
        directoryRepository.deleteById(dirId);
    }

    @Override //0 - сортировка по возрастания 1 - сортировка по убыванию
    @Transactional(readOnly = true)
    public FoldersAndFiles getContent(Long dirId, Long sortType) {
        Optional<Directory> dir = directoryRepository.findById(dirId);
        if (dir.isEmpty()) throw new DirectoryNotFound("directory not found");
        FoldersAndFiles foldersAndFiles = new FoldersAndFiles();
        List<FileNameAndId> filesInfo = new ArrayList<>();
        List<FolderDto> directoriesInfo = new ArrayList<>();
        for (ru.cft.shift.intensive.cool_drive.repository.entity.File file : List.copyOf(dir.get().getFiles())) {
            filesInfo.add(new FileNameAndId(file.getId(), file.getName()));
        }

        for (Directory childDir : List.copyOf(dir.get().getChildDirectories())) {
            directoriesInfo.add(new FolderDto(childDir.getId(), childDir.getName()));
        }
        Comparator<FileNameAndId> comp;
        comp = Comparator.comparing(FileNameAndId::getName);
        if(sortType == 1) comp = comp.reversed();
        Collections.sort(filesInfo, comp);

        foldersAndFiles.setFiles(filesInfo);
        foldersAndFiles.setFolders(directoriesInfo);
        return foldersAndFiles;
    }

    @Override
    @Transactional
    public void renameDirectory(Long dirId, String name) {
        Optional<Directory> dir = directoryRepository.findById(dirId);
        if (dir.isEmpty()) throw new DirectoryNotFound("directory not found");
        try {
            Files.move(Paths.get(getFullPath(dir.get().getAccount().getUsername(),
                            getPathToDir(dir.get().getParentDirectory()), dir.get().getName())),
                    Paths.get(getFullPath(dir.get().getAccount().getUsername(),
                            getPathToDir(dir.get().getParentDirectory()), name)));
        } catch (Exception e) {
            throw new DirectoryAlreadyExists(e.getMessage());
        }
        dir.get().setName(name);
        directoryRepository.save(dir.get());
    }
}

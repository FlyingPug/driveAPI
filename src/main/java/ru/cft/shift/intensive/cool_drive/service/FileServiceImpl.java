package ru.cft.shift.intensive.cool_drive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shift.intensive.cool_drive.dto.FileDto;
import ru.cft.shift.intensive.cool_drive.exception.FileAlreadyExists;
import ru.cft.shift.intensive.cool_drive.exception.FileNotFound;
import ru.cft.shift.intensive.cool_drive.repository.FileRepository;
import ru.cft.shift.intensive.cool_drive.repository.entity.Account;
import ru.cft.shift.intensive.cool_drive.repository.entity.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final FileRepository fileRepository;
    private final FolderService folderService;


    public FileServiceImpl(FileRepository fileRepository, FolderService folderService) {
        this.fileRepository = fileRepository;
        this.folderService = folderService;
    }

    @Override
    @Transactional(readOnly = true)
    public java.io.File getFilePath(File file) {
        java.io.File filePath = new java.io.File(
                folderService.getFullPath(file.getAccount().getUsername(),
                        folderService.getPathToDir(file.getDirectory()), file.getName()));
        if (!filePath.exists()) throw new FileNotFound("file not found");
        return filePath;
    }

    @Override
    @Transactional(readOnly = true)
    public File getFileById(Long fileId) {
        Optional<File> file = fileRepository.findById(fileId);
        if (file.isEmpty()) {
            log.warn("file not found");
            throw new FileNotFound("file not found");
        }
        return file.get();
    }

    @Override
    @Transactional
    public void changeFileName(FileDto fileDto) {
        Optional<File> file = fileRepository.findById(fileDto.getFileId());
        if (file.isEmpty()) {
            log.warn("file not found");
            throw new FileNotFound("file not found");
        }
        java.io.File fileOld = new java.io.File(folderService.getFullPath(file.get().getAccount().getUsername(),
                folderService.getPathToDir(file.get().getDirectory()), file.get().getName()));
        java.io.File fileNew = new java.io.File(folderService.getFullPath(file.get().getAccount().getUsername(),
                folderService.getPathToDir(file.get().getDirectory()), fileDto.getName()));

        file.get().setName(fileDto.getName());
        file.get().setPublic(fileDto.isPublic());

        // выбрасываем исключение толко когда идет смена имени, а не просто содержимого
        if(fileNew.exists() && !Objects.equals(fileDto.getName(), file.get().getName())) throw new FileAlreadyExists("file with that name already exists");

        //мы можем и не обновлять файл
        if(fileDto.getFileData() != null) {
            if (!fileOld.delete()) throw new FileNotFound("file not found, that's strange");

            try (OutputStream os = new FileOutputStream(fileNew)) {
                os.write(fileDto.getFileData().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        file.get().setName(fileDto.getName());
        file.get().setPublic(fileDto.isPublic());
        fileRepository.save(file.get());
    }

    @Override
    @Transactional
    public void removeFile(Long fileId) {
        Optional<File> file = fileRepository.findById(fileId);
        if (file.isEmpty()) {
            log.warn("file not found");
            throw new FileNotFound("file not found");
        }
        java.io.File diskFile = new java.io.File(folderService.getFullPath(file.get().getAccount().getUsername(),
                folderService.getPathToDir(file.get().getDirectory()), file.get().getName()));
        if (!diskFile.delete()) {
            throw new FileNotFound("file to delete is not found");
        }

        fileRepository.deleteById(fileId);
    }

    @Override
    @Transactional
    public File loadFile(Account owner, Long dirId, MultipartFile fileContent) {
        File file = new File();
        file.setAccount(owner);
        file.setDirectory(folderService.getDirectoryById(dirId));
        file.setName(fileContent.getOriginalFilename());
        file.setPublic(0);
        java.io.File pathToLoad = new java.io.File(folderService.getFullPath(owner.getUsername(),
                folderService.getPathToDir(file.getDirectory()),
                file.getName()));
        log.warn(pathToLoad.getAbsolutePath());
        if(pathToLoad.exists()) throw new FileAlreadyExists("file already exists");

        try (OutputStream os = new FileOutputStream(pathToLoad)) {
            os.write(fileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileRepository.save(file);
    }

    @Override
    @Transactional(readOnly = true)
    public List<File> searchFileByName(String name) {
        return fileRepository.findAllByName(name);
    }
}

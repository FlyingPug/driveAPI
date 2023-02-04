package ru.cft.shift.intensive.cool_drive.dto;

import java.util.List;

/**
 * класс в котором содержатся файлы и папки какой-то директории
 */
public class FoldersAndFiles {
    private List<FileNameAndId> files;
    private List<FolderDto> folders;

    public List<FileNameAndId> getFiles() {
        return files;
    }

    public void setFiles(List<FileNameAndId> files) {
        this.files = files;
    }

    public List<FolderDto> getFolders() {
        return folders;
    }

    public void setFolders(List<FolderDto> folders) {
        this.folders = folders;
    }
}

package ru.cft.shift.intensive.cool_drive.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

/**
 * данные для изменения состояния файла
 */
public class FileDto {
    @NotEmpty(message = "file should not be empty")
    private Long fileId;
    @NotEmpty(message = "name should not be empty")
    private String name;
    private int isPublic;
    private MultipartFile fileData;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isPublic() {
        return isPublic;
    }

    public void setPublic(int aPublic) {
        isPublic = aPublic;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

    public FileDto(Long fileId, String name, int isPublic, MultipartFile fileData) {
        this.fileId = fileId;
        this.name = name;
        this.isPublic = isPublic;
        this.fileData = fileData;
    }
}

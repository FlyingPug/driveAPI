package ru.cft.shift.intensive.cool_drive.dto;

/**
 * просто id файла
 */
public class FileIdDto {
    private Long Id;

    public Long getId() {
        return Id;
    }

    public FileIdDto(Long id) {
        Id = id;
    }

    public void setId(Long id) {
        Id = id;
    }
}

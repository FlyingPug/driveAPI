package ru.cft.shift.intensive.cool_drive.dto;

import io.micrometer.core.lang.Nullable;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotEmpty;

/**
 * данные для идентификации папки
 */
public class FolderDto {

    @NotEmpty(message = "folder id should not be empty")
    private Long Id;
    @NotEmpty(message = "folder name should not be empty")
    private String name;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FolderDto(Long id, String name) {
        Id = id;
        this.name = name;
    }
    public FolderDto() {
    }
    @Override
    public String toString() {
        return "FolderDto{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}

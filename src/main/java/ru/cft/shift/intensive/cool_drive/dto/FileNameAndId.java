package ru.cft.shift.intensive.cool_drive.dto;

import javax.validation.constraints.NotEmpty;

public class FileNameAndId {
    @NotEmpty(message = "file id should not be empty")
    public Long id;
    @NotEmpty(message = "name id should not be empty")
    public String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileNameAndId(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

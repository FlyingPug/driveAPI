package ru.cft.shift.intensive.cool_drive.repository.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * таблица директорий (папок)
 */
@Entity
@Table(name = "directory")
public class Directory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Directory parentDirectory;
    @OneToMany(mappedBy = "directory",cascade=CascadeType.ALL)
    private Set<File> files;

    @OneToMany(mappedBy = "parentDirectory",cascade=CascadeType.ALL)
    private Set<Directory> childDirectories;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account owner) {
        this.account = owner;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Account account;

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

    public Directory getParentDirectory() {
        return parentDirectory;
    }

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public Set<Directory> getChildDirectories() {
        return childDirectories;
    }

    public void setChildDirectories(Set<Directory> childDirectories) {
        this.childDirectories = childDirectories;
    }
}

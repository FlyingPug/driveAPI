package ru.cft.shift.intensive.cool_drive.repository.entity;

import javax.persistence.*;
/**
 * таблица файлов (их описаний)
 */
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "is_public", columnDefinition="BIT")
    private int isPublic;
    @ManyToOne
    @JoinColumn(name = "dir_id")
    private Directory directory;

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

    public int isPublic() {
        return isPublic;
    }

    public void setPublic(int aPublic) {
        isPublic = aPublic;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account owner) {
        this.account = owner;
    }
}

package ru.cft.shift.intensive.cool_drive.repository.entity;

import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Таблица пользователей
 */
@Entity
@Table(name = "account")
public class Account implements AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<File> files;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Directory> directories;

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getPassword(String password) {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public AccountDetails clearPassword() {
        setPassword(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}

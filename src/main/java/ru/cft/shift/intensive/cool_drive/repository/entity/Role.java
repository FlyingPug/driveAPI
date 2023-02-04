package ru.cft.shift.intensive.cool_drive.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

/**
 * Таблица ролей
 */
@Entity
@Table(name = "role")
public class Role {
    private static final String ROLE_PREFIX = "ROLE_";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    @Transient
    public static final Role ADMIN_ROLE = new Role(1L, ROLE_PREFIX + ADMIN);
    @Transient
    public static final Role USER_ROLE = new Role(2L, ROLE_PREFIX + USER);

    @Id
    private Long id;
    private String name;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id.equals(role.id);
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}

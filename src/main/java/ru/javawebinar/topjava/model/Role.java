package ru.javawebinar.topjava.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends AbstractBaseEntity {

    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(String role) {
        this.role = role;
    }

    protected Role() { }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
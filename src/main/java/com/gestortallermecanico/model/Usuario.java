package com.gestortallermecanico.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String lastname;
    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
    )
    private Collection<Rol> roles;

    public Usuario() {
        super();
    }

    public Usuario(Long id, String username, String lastname, String password, String email, Collection<Rol> roles) {
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public Usuario(String username, String lastname, String password, String email, Collection<Rol> roles) {
        this.username = username;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Collection<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Rol> roles) {
        this.roles = roles;
    }
}

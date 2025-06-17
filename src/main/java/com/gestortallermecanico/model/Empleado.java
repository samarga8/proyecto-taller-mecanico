package com.gestortallermecanico.model;

import jakarta.persistence.*;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String apellidos;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String especialidad;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String pwd;

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEspecialidad() { return especialidad; }

    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

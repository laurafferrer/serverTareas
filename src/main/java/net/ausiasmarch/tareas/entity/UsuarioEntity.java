package net.ausiasmarch.tareas.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @NotBlank
    @Size(min=3, max=255)
    private String nombre;
    @NotNull
    @NotBlank
    @Size(min=6, max=15)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric")
    private String username; 
    @Size(max=255)
    private String apellidos;
    private Boolean puesto = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @NotBlank
    @Size(min=64, max=64)
    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "Password must be hexadecimal")
    private String password = "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e";        

    @OneToMany(mappedBy = "usuario", fetch = jakarta.persistence.FetchType.LAZY)
    private List<TareaEntity> tareas;

    public UsuarioEntity() {
        tareas = new ArrayList<>();
    }

    public UsuarioEntity(Long id, String username, String nombre, String apellidos, Boolean puesto,
            String password) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puesto = puesto;
        this.password = password;
    }

    public UsuarioEntity(String username, String nombre, String apellidos, Boolean puesto, String password) {
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.puesto = puesto;
    }

    public UsuarioEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return username;
    }

    public void setCodigo(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getPuesto() {
        return puesto;
    }

    public void setPuesto(Boolean puesto) {
        this.puesto = puesto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTareas() {
        return tareas.size();
    }

}

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
import jakarta.validation.constraints.Email;
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

    @Column(unique = true) // Hace que el campo "codigo" sea único
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long codigo;
    
    @NotNull
    @NotBlank
    @Size(min=3, max=255)
    private String nombre;  
    @Size(max=255)
    private String apellidos;
    private Boolean puesto = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @NotBlank
    @Size(min=64, max=64)
    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "Password must be hexadecimal")
    private String password = "123";        

    @OneToMany(mappedBy = "usuario", fetch = jakarta.persistence.FetchType.LAZY)
    private List<TareaEntity> tareas;

    public UsuarioEntity() {
        tareas = new ArrayList<>();
    }

    public UsuarioEntity(Long id, Long codigo, String nombre, String apellidos, Boolean puesto,
            String password) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puesto = puesto;
        this.password = password;
    }

    public UsuarioEntity(Long codigo, String nombre, String apellidos, Boolean puesto, String password) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.puesto = puesto;
    }

    public UsuarioEntity(Long codigo, String password) {
        this.codigo = codigo;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

package net.ausiasmarch.tareas.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "proyecto")
public class ProyectoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 2048)
    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fecha_inicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime fecha_fin;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @OneToMany(mappedBy = "proyecto", fetch = jakarta.persistence.FetchType.LAZY)
    private List<TareaEntity> tareas;

    public ProyectoEntity() {
        tareas = new java.util.ArrayList<>();
    }

     public ProyectoEntity(String nombre) {
        this.nombre = nombre;
        this.fecha_inicio = LocalDateTime.now();
        this.fecha_fin = LocalDateTime.now();
    }

    public ProyectoEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_inicio = LocalDateTime.now();
        this.fecha_fin = LocalDateTime.now();
    }

    public ProyectoEntity(String nombre, UsuarioEntity usuario) {
        this.nombre = nombre;
        this.fecha_inicio = LocalDateTime.now();
        this.fecha_fin = LocalDateTime.now();
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public int getTareas() {
        return tareas.size();
    }

}
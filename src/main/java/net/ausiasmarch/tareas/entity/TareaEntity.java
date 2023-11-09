package net.ausiasmarch.tareas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tarea")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 2048)
    String nombre;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private ProyectoEntity proyecto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public TareaEntity() {
    }

    public TareaEntity(String nombre) {
        this.nombre = nombre;
    }

    public TareaEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TareaEntity(String nombre, UsuarioEntity usuario, ProyectoEntity proyecto) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.proyecto = proyecto;
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

    public ProyectoEntity getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }

}

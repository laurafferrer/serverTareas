package net.ausiasmarch.tareas.entity;

import java.util.List;

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

    @OneToMany(mappedBy = "proyecto", fetch = jakarta.persistence.FetchType.LAZY)
    private List<TareaEntity> tareas;

    @NotNull
    private Date fecha_inicio;
    
    @NotNull
    private Date fecha_fin;

    public ProyectoEntity() {
        tareas = new java.util.ArrayList<>();
    }

    public ProyectoEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ProyectoEntity(String nombre) {
        this.nombre = nombre;
    }

    public ProyectoEntity(String nombre, Date fecha_inicio, Date fecha_fin) {
        this.nombre = nombre;
        this.fecha_inicio = fecha_inicio;
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

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getTareas() {
        return tareas.size();
    }

}

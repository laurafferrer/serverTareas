package net.ausiasmarch.tareas.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private TareaEntity tarea;

    public ProyectoEntity() {
    }

    public ProyectoEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_inicio = LocalDateTime.now();
    }

    public ProyectoEntity(String nombre, LocalDateTime fecha_inicio, TareaEntity tarea) {
        this.nombre = nombre;
        this.fecha_inicio = LocalDateTime.now();
        this.tarea = tarea;
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

    public LocalDateTime getFechaInicio() {
        return fecha_inicio;
    }

    public void setFechaInicio(LocalDateTime fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public TareaEntity getTarea() {
        return tarea;
    }

    public void setTarea(TareaEntity tarea) {
        this.tarea = tarea;
    }

}
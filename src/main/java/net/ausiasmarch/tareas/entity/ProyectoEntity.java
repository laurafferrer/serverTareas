package net.ausiasmarch.tareas.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Period;
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

    @OneToMany(mappedBy = "proyecto", fetch = jakarta.persistence.FetchType.LAZY)
    private List<TareaEntity> tareas;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
private LocalDateTime fecha_inicio;

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
private LocalDateTime fecha_fin;

    private String speech;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public ProyectoEntity(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_inicio = LocalDateTime.now();
        this.fecha_fin = LocalDateTime.now().plus(Period.ofDays(7));
    }

    public ProyectoEntity(String nombre, Date fecha_inicio, Date fecha_fin) {
        this.nombre = nombre;
        this.fecha_inicio = LocalDateTime.now();
        this.fecha_fin = LocalDateTime.now().plus(Period.ofDays(7));
    }

    public ProyectoEntity(String speech, UsuarioEntity oneRandom) {
        this.speech = speech;
    this.usuario = oneRandom;
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

}

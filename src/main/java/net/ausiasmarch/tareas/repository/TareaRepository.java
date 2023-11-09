package net.ausiasmarch.tareas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import net.ausiasmarch.tareas.entity.TareaEntity;

public interface TareaRepository extends JpaRepository<TareaEntity, Long> {
    Page<TareaEntity> findByUsuarioId(Long id, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE tarea AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    Page<TareaEntity> findByProyectoId(Long proyectoId, Pageable oPageable);
}

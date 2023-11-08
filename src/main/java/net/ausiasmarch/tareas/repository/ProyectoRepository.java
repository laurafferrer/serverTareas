package net.ausiasmarch.tareas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import net.ausiasmarch.tareas.entity.ProyectoEntity;

public interface ProyectoRepository extends JpaRepository<ProyectoEntity, Long> {
    Page<ProyectoEntity> findByUsuarioId(Long id, Pageable pageable);

    Page<ProyectoEntity> findByTareaId(Long id, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE proyecto AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}

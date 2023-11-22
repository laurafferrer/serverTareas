package net.ausiasmarch.tareas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import net.ausiasmarch.tareas.entity.TareaEntity;

public interface TareaRepository extends JpaRepository<TareaEntity, Long> {

    @Modifying
    @Query(value = "ALTER TABLE tarea AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}

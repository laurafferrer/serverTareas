package net.ausiasmarch.tareas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.tareas.entity.TareaEntity;

public interface TareaRepository extends JpaRepository<TareaEntity, Long> {

   /*  Page<TareaEntity> findByUserId(Long id, Pageable pageable);*/

    @Query(value = "SELECT t.*,count(pr.id) FROM tarea t, proyecto pr WHERE t.id = pr.id_tarea GROUP BY t.id ORDER BY COUNT(pr.id) desc", nativeQuery = true)
    Page<TareaEntity> findTareasByProyectosNumberDesc(Pageable pageable);

    @Query(value = "SELECT t.*,count(pr.id) FROM tarea t, proyecto pr WHERE t.id = pr.id_tarea and t.id_usuario=$1 GROUP BY t.id ORDER BY COUNT(pr.id) desc", nativeQuery = true)
    Page<TareaEntity> findTareasByProyectosNumberDescFilterByUserId(Long usuarioId, Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE pedido AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}

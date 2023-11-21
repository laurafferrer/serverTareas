package net.ausiasmarch.tareas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import net.ausiasmarch.tareas.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByUsername(String username);

    Optional<UsuarioEntity> findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT u.*,count(t.id) FROM usuario u, tarea t WHERE u.id = t.usuario_id GROUP BY u.id ORDER BY COUNT(u.id) desc", nativeQuery = true)
    Page<UsuarioEntity> findUsuariosByTareasNumberDescFilter(Pageable pageable);

    @Modifying
    @Query(value = "ALTER TABLE user AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}

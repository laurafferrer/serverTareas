package net.ausiasmarch.tareas.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.tareas.entity.ProyectoEntity;
import net.ausiasmarch.tareas.entity.TareaEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.repository.ProyectoRepository;
import net.ausiasmarch.tareas.repository.TareaRepository;

@Service
public class ProyectoService {
    @Autowired
    ProyectoRepository oProyectoRepository;

    @Autowired
    TareaRepository oTareaRepository;

    @Autowired
    SessionService oSessionService;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    UsuarioService oUsuarioService;

    public ProyectoEntity get(Long id) {
        return oProyectoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proyecto not found"));
    }

    public Long create(ProyectoEntity oProyectoEntity) {
        oSessionService.onlyAdmins();
        oProyectoEntity.setId(null);
        return oProyectoRepository.save(oProyectoEntity).getId();
    }

    public ProyectoEntity update(ProyectoEntity oProyectoEntity) {
        oSessionService.onlyAdmins();
        if (oProyectoEntity.getId() == null) {
            throw new ResourceNotFoundException("Proyecto id cannot be null for an update");
        }
        return oProyectoRepository.save(oProyectoEntity);
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        ProyectoEntity oProyectoEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oProyectoEntityFromDatabase.getTarea().getId());
        oProyectoRepository.deleteById(id);
        return id;
    }

    public Page<ProyectoEntity> getPage(Pageable oPageable, Long usuario_id) {
        return oProyectoRepository.findAll(oPageable);
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        TareaEntity proyectoPorDefecto = oTareaRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un proyecto por defecto con ID 1"));
        for (int i = 0; i < amount; i++) {
            ProyectoEntity proyecto = new ProyectoEntity();
            proyecto.setTarea(proyectoPorDefecto);
            proyecto.setNombre("Proyecto inicial");
            proyecto.setFechaInicio(LocalDateTime.now());

            oProyectoRepository.save(proyecto);
        }
        return amount.longValue();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oProyectoRepository.deleteAll();
        oProyectoRepository.resetAutoIncrement();
        oProyectoRepository.flush();
        return oProyectoRepository.count();
    }

}
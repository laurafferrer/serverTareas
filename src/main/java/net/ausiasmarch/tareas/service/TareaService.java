package net.ausiasmarch.tareas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.tareas.entity.TareaEntity;
import net.ausiasmarch.tareas.entity.UsuarioEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.repository.TareaRepository;
import net.ausiasmarch.tareas.repository.UsuarioRepository;

@Service
public class TareaService {

    @Autowired
    TareaRepository oTareaRepository;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SessionService oSessionService;

    public TareaEntity get(Long id) {
        return oTareaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));
    }

    public Long create(TareaEntity oTareaEntity) {
        oSessionService.onlyAdmins();
        oTareaEntity.setId(null);

        if (oSessionService.isUsuario()) {
            oTareaEntity.setUsuario(oSessionService.getSessionUsuario());
        }

        return oTareaRepository.save(oTareaEntity).getId();
    }

    public TareaEntity update(TareaEntity tareaEntity) {

        TareaEntity oTareaEntityFromDatabase = this.get(tareaEntity.getId());
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oTareaEntityFromDatabase.getUsuario().getId());
        if (oSessionService.isUsuario()) {
            tareaEntity.setUsuario(oSessionService.getSessionUsuario());
            return oTareaRepository.save(tareaEntity);
        } else {
            return oTareaRepository.save(tareaEntity);
        }
    }

    public Long delete(Long id) {
        TareaEntity oTareaEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oTareaEntityFromDatabase.getUsuario().getId());
        oTareaRepository.deleteById(id);
        return id;
    }

    public Page<TareaEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oTareaRepository.findAll(oPageable);
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        UsuarioEntity usuarioPorDefecto = oUsuarioRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un cliente por defecto con ID 1"));

        for (int i = 0; i < amount; i++) {
            TareaEntity tarea = new TareaEntity();

            tarea.setUsuario(usuarioPorDefecto);
            tarea.setNombre("tarea inicial");
            oTareaRepository.save(tarea);
        }

        return amount.longValue();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oTareaRepository.deleteAll();
        oTareaRepository.resetAutoIncrement();
        oTareaRepository.flush();
        return oTareaRepository.count();
    }

}
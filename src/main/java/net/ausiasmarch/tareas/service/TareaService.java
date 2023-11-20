package net.ausiasmarch.tareas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.tareas.entity.TareaEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.helper.DataGenerationHelper;
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
    ProyectoService oProyectoService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    SessionService oSessionService;

    public TareaEntity get(Long id) {
        return oTareaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));
    }

    public Page<TareaEntity> getPage(Pageable oPageable, Long usuarioId, Long proyectoId) {
        if (usuarioId == 0) {
            if (proyectoId == 0) {
                return oTareaRepository.findAll(oPageable);
            } else {
                return oTareaRepository.findByProyectoId(proyectoId, oPageable);
            }
        } else {
            return oTareaRepository.findByUsuarioId(usuarioId, oPageable);
        }
    }

    public Long create(TareaEntity oTareaEntity) {
        oSessionService.onlyAdminsOrUsuarios();
        oTareaEntity.setId(null);        
        if (oSessionService.isUsuario()) {
            oTareaEntity.setUsuario(oSessionService.getSessionUsuario());
            return oTareaRepository.save(oTareaEntity).getId();
        } else {
            if (oTareaEntity.getUsuario().getId() == null || oTareaEntity.getUsuario().getId() == 0){
                oTareaEntity.setUsuario(oSessionService.getSessionUsuario());
            }
            return oTareaRepository.save(oTareaEntity).getId();
        }
    }

    public TareaEntity update(TareaEntity oTareaEntityToSet) {
        TareaEntity oTareaEntityFromDatabase = this.get(oTareaEntityToSet.getId());
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oTareaEntityFromDatabase.getUsuario().getId());
        if (oSessionService.isUsuario()) {
            oTareaEntityToSet.setUsuario(oSessionService.getSessionUsuario());
            return oTareaRepository.save(oTareaEntityToSet);
        } else {
            return oTareaRepository.save(oTareaEntityToSet);
        }
    }

    public Long delete(Long id) {
        TareaEntity oTareaEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oTareaEntityFromDatabase.getUsuario().getId());
        oTareaRepository.deleteById(id);
        return id;
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            TareaEntity tareaEntity = new TareaEntity(DataGenerationHelper.getRadomNombre(),
                    oUsuarioService.getOneRandom(), oProyectoService.getOneRandom());
            oTareaRepository.save(tareaEntity);
        }
        return oTareaRepository.count();
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

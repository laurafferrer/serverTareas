package net.ausiasmarch.tareas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.tareas.entity.ProyectoEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.helper.DataGenerationHelper;
import net.ausiasmarch.tareas.repository.ProyectoRepository;
import net.ausiasmarch.tareas.repository.UsuarioRepository;

@Service
public class ProyectoService {
    @Autowired
    ProyectoRepository oProyectoRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    SessionService oSessionService;

    public ProyectoEntity get(Long id) {
        return oProyectoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proyecto not found"));
    }

    public Page<ProyectoEntity> getPage(Pageable oPageable, Long usuarioId) {
        if (usuarioId == 0) {
            return oProyectoRepository.findAll(oPageable);
        } else {
            return oProyectoRepository.findByUsuarioId(usuarioId, oPageable);
        }
    }

    public Page<ProyectoEntity> getPageByTareasNumberDesc(Pageable oPageable) {
        if (usuarioId == 0) {
            return oProyectoRepository.findProyectosByTareasNumberDesc(oPageable);
        } else {
            return oProyectoRepository.findProyectosByTareasNumberDescFilterByUsuarioId(usuarioId, oPageable);
        }
        }

    public Long create(ProyectoEntity oProyectoEntity) {
        oProyectoEntity.setId(null);
        oSessionService.onlyAdminsOrUsuarios();
        if (oSessionService.isUsuario()) {
            oProyectoEntity.setUsuario(oSessionService.getSessionUsuario());
            return oProyectoRepository.save(oProyectoEntity).getId();
        } else {
            return oProyectoRepository.save(oProyectoEntity).getId();
        }
    }

    public ProyectoEntity update(ProyectoEntity oProyectoEntityToSet) {
        ProyectoEntity oProyectoEntityFromDatabase = this.get(oProyectoEntityToSet.getId());
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oProyectoEntityFromDatabase.getUsuario().getId());
        if (oSessionService.isUsuario()) {
            if (oProyectoEntityToSet.getUsuario().getId().equals(oSessionService.getSessionUsuario().getId())) {
                return oProyectoRepository.save(oProyectoEntityToSet);
            } else {
                throw new ResourceNotFoundException("Unauthorized");
            }
        } else {
            return oProyectoRepository.save(oProyectoEntityToSet);
        }
    }

    public Long delete(Long id) {
        ProyectoEntity oProyectoEntityFromDatabase = this.get(id);
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oProyectoEntityFromDatabase.getUsuario().getId());
        oProyectoRepository.deleteById(id);
        return id;
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            oProyectoRepository
                    .save(new ProyectoEntity(DataGenerationHelper.getSpeech(1), oUsuarioService.getOneRandom()));
        }
        return oProyectoRepository.count();
    }

    public ProyectoEntity getOneRandom() {
        oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oProyectoRepository.count()), 1);
        return oProyectoRepository.findAll(oPageable).getContent().get(0);
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

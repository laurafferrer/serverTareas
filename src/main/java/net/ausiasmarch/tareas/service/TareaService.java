package net.ausiasmarch.tareas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.tareas.entity.TareaEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.helper.DataGenerationHelper;
import net.ausiasmarch.tareas.repository.TareaRepository;

@Service
public class TareaService {

     @Autowired
    TareaRepository oTareaRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SessionService oSessionService;

    public TareaEntity get(Long id) {
        return oTareaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarea not found"));
    }

    public Page<TareaEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oTareaRepository.findAll(oPageable);
    }

    public Long create(TareaEntity oTareaEntity) {
        oSessionService.onlyAdmins();
        oTareaEntity.setId(null);
        return oTareaRepository.save(oTareaEntity).getId();
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        oTareaRepository.deleteById(id);
        return id;
    }

    public TareaEntity getOneRandom() {
        oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oTareaRepository.count()), 1);
        return oTareaRepository.findAll(oPageable).getContent().get(0);
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            String nombre = DataGenerationHelper.generateNombreTarea().substring(0, 3);
            oTareaRepository.save(new TareaEntity(nombre));
        }
        return oTareaRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oTareaRepository.deleteAll();
        oTareaRepository.resetAutoIncrement();
        TareaEntity oTareaEntity1 = new TareaEntity(1L, "Tarea prueba");
        oTareaRepository.save(oTareaEntity1);
        oTareaEntity1 = new TareaEntity(2L, "Tarea prueba 2");
        oTareaRepository.save(oTareaEntity1);
        return oTareaRepository.count();
    }

}
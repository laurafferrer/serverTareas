package net.ausiasmarch.tareas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.tareas.entity.UsuarioEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.helper.DataGenerationHelper;
import net.ausiasmarch.tareas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final String usuarioPASSWORD = "e2cac5c5f7e52ab03441bb70e89726ddbd1f6e5b683dde05fb65e0720290179e";

     @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SessionService oSessionService;

    public UsuarioEntity get(Long id) {
        return oUsuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
    }

    public UsuarioEntity getByUsername(String username) {
        return oUsuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found by username"));
    }

    public Page<UsuarioEntity> getPage(Pageable oPageable) {
        oSessionService.onlyAdmins();
        return oUsuarioRepository.findAll(oPageable);
    }

    public Long create(UsuarioEntity oUsuarioEntity) {
        oSessionService.onlyAdmins();
        oUsuarioEntity.setId(null);
        oUsuarioEntity.setPassword(usuarioPASSWORD);
        return oUsuarioRepository.save(oUsuarioEntity).getId();
    }

    public UsuarioEntity update(UsuarioEntity oUsuarioEntityToSet) {
        UsuarioEntity oUsuarioEntityFromDatabase = this.get(oUsuarioEntityToSet.getId());
        oSessionService.onlyAdminsOrUsuariosWithIisOwnData(oUsuarioEntityFromDatabase.getId());
        if (oSessionService.isUsuario()) {            
            oUsuarioEntityToSet.setPuesto(oUsuarioEntityFromDatabase.getPuesto());
            oUsuarioEntityToSet.setPassword(usuarioPASSWORD);
            return oUsuarioRepository.save(oUsuarioEntityToSet);
        } else {            
            oUsuarioEntityToSet.setPassword(usuarioPASSWORD);
            return oUsuarioRepository.save(oUsuarioEntityToSet);
        }
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        oUsuarioRepository.deleteById(id);
        return id;
    }

    public UsuarioEntity getOneRandom() {
        oSessionService.onlyAdmins();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oUsuarioRepository.count()), 1);
        return oUsuarioRepository.findAll(oPageable).getContent().get(0);
    }

    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            String nombre = DataGenerationHelper.getRandomNombre();
            String apellidos = DataGenerationHelper.getRandomApellido();
            String username = DataGenerationHelper
                    .doNormalizeString(
                            nombre.substring(0, 3) + apellidos.substring(1, 3));
            oUsuarioRepository.save(new UsuarioEntity(nombre, apellidos, username, usuarioPASSWORD , true));
        }
        return oUsuarioRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oUsuarioRepository.deleteAll();
        oUsuarioRepository.resetAutoIncrement();
        UsuarioEntity oUsuarioEntity1 = new UsuarioEntity(1L, "Pedro", "Picapiedra", "pedropicapiedra", usuarioPASSWORD, false);
        oUsuarioRepository.save(oUsuarioEntity1);
        oUsuarioEntity1 = new UsuarioEntity(2L, "Pablo", "Mármol", "pablomarmol", usuarioPASSWORD, true);
        oUsuarioRepository.save(oUsuarioEntity1);
        return oUsuarioRepository.count();
    }

}
package net.ausiasmarch.tareas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.tareas.entity.UsuarioEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.exception.UnauthorizedException;
import net.ausiasmarch.tareas.helper.DataGenerationHelper;
import net.ausiasmarch.tareas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final String usuarioPASSWORD = "123";

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    SessionService oSessionService;

    public UsuarioEntity get(Long id) {
        return oUsuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
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
        oSessionService.onlySupervisoresOrUsuariosWithIisOwnData(oUsuarioEntityFromDatabase.getId());
        if (oSessionService.isUsuario()) {
            oUsuarioEntityToSet.setId(null);
            oUsuarioEntityToSet.setPuesto(oUsuarioEntityFromDatabase.getPuesto());
            oUsuarioEntityToSet.setPassword(usuarioPASSWORD);
            return oUsuarioRepository.save(oUsuarioEntityToSet);
        } else {
            oUsuarioEntityToSet.setId(null);
            oUsuarioEntityToSet.setPassword(usuarioPASSWORD);
            return oUsuarioRepository.save(oUsuarioEntityToSet);
        }
    }

    public Long delete(Long id) {
        oSessionService.onlySupervisor();
        oUsuarioRepository.deleteById(id);
        return id;
    }

    public UsuarioEntity getOneRandom() {
        oSessionService.onlySupervisor();
        Pageable oPageable = PageRequest.of((int) (Math.random() * oUsuarioRepository.count()), 1);
        return oUsuarioRepository.findAll(oPageable).getContent().get(0);
    }
/*
    public Long populate(Integer amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i < amount; i++) {
            String name = DataGenerationHelper.getRadomName();
            String surname = DataGenerationHelper.getRadomSurname();
            String lastname = DataGenerationHelper.getRadomSurname();
            String email = name.substring(0, 3) + surname.substring(0, 3) + lastname.substring(0, 2) + i
                    + "@ausiasmarch.net";
            String username = DataGenerationHelper
                    .doNormalizeString(
                            name.substring(0, 3) + surname.substring(1, 3) + lastname.substring(1, 2) + i);
            oUsuarioRepository.save(new UsuarioEntity(name, surname, lastname, email, username,
                    "123", true));
        }
        return oUsuarioRepository.count();
    }
*//*
    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oUsuarioRepository.deleteAll();
        oUsuarioRepository.resetAutoIncrement();
        UsuarioEntity oUsuarioEntity1 = new UsuarioEntity(1L, "Pedro", "Picapiedra", "Roca",
                "pedropicapiedra@ausiasmarch.net", "pedropicapiedra", usuarioPASSWORD, false);
        oUsuarioRepository.save(oUsuarioEntity1);
        oUsuarioEntity1 = new UsuarioEntity(2L, "Pablo", "Mármol", "Granito", "pablomarmol@ausiasmarch.net",
                "pablomarmol", usuarioPASSWORD, true);
        oUsuarioRepository.save(oUsuarioEntity1);
        return oUsuarioRepository.count();
    }
*/
    public Object populate(Integer amount) {
        return null;
    }

    public Object empty() {
        return null;
    }

}

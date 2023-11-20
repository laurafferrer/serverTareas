package net.ausiasmarch.tareas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.tareas.bean.UsuarioBean;
import net.ausiasmarch.tareas.entity.UsuarioEntity;
import net.ausiasmarch.tareas.exception.ResourceNotFoundException;
import net.ausiasmarch.tareas.exception.UnauthorizedException;
import net.ausiasmarch.tareas.helper.JWTHelper;
import net.ausiasmarch.tareas.repository.UsuarioRepository;

@Service
public class SessionService {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public String login(UsuarioBean oUsuarioBean) {
        oUsuarioRepository.findByUsernameAndPassword(oUsuarioBean.getUsername(), oUsuarioBean.getPassword())
                .orElseThrow(() -> new ResourceNotFoundException("Wrong Usuario or password"));
        return JWTHelper.generateJWT(oUsuarioBean.getUsername());
    }

    public String getSessionUsername() {        
        if (oHttpServletRequest.getAttribute("username") instanceof String) {
            return oHttpServletRequest.getAttribute("username").toString();
        } else {
            return null;
        }
    }

    public UsuarioEntity getSessionUsuario() {
        if (this.getSessionUsername() != null) {
            return oUsuarioRepository.findByUsername(this.getSessionUsername()).orElse(null);    
        } else {
            return null;
        }
    }

    public Boolean isSessionActive() {
        if (this.getSessionUsername() != null) {
            return oUsuarioRepository.findByUsername(this.getSessionUsername()).isPresent();
        } else {
            return false;
        }
    }

    public Boolean isAdmin() {
        if (this.getSessionUsername() != null) {
            UsuarioEntity oUsuarioEntityInSession = oUsuarioRepository.findByUsername(this.getSessionUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
            return Boolean.FALSE.equals(oUsuarioEntityInSession.getPuesto());
        } else {
            return false;
        }
    }

    public Boolean isUsuario() {
        if (this.getSessionUsername() != null) {
            UsuarioEntity oUsuarioEntityInSession = oUsuarioRepository.findByUsername(this.getSessionUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
            return Boolean.TRUE.equals(oUsuarioEntityInSession.getPuesto());
        } else {
            return false;
        }
    }

    public void onlyAdmins() {
        if (!this.isAdmin()) {
            throw new UnauthorizedException("Only supervisor can do this");
        }
    }

    public void onlyUsuarios() {
        if (!this.isUsuario()) {
            throw new UnauthorizedException("Only Usuarios can do this");
        }
    }

    public void onlyAdminsOrUsuarios() {
        if (!this.isSessionActive()) {
            throw new UnauthorizedException("Only supervisores or usuarios can do this");
        }
    }

    public void onlyUsuariosWithIisOwnData(Long usuario_id) {
        if (!this.isUsuario()) {
            throw new UnauthorizedException("Only usuarios can do this");
        }
        if (!this.getSessionUsuario().getId().equals(usuario_id)) {
            throw new UnauthorizedException("Only Usuarios can do this");
        }
    }

    public void onlyAdminsOrUsuariosWithIisOwnData(Long usuario_id) {
        if (this.isSessionActive()) {
            if (!this.isAdmin()) {
                if (!this.isUsuario()) {
                    throw new UnauthorizedException("Only supervisores or usuarios can do this");
                } else {
                    if (!this.getSessionUsuario().getId().equals(usuario_id)) {
                        throw new UnauthorizedException("Only supervisores or usuarios with its own data can do this");
                    }
                }
            }
        } else {
            throw new UnauthorizedException("Only supervisores or usuarios can do this");
        }
    }

}

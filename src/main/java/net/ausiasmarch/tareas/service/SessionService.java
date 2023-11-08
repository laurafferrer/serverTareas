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
        oUsuarioRepository.findByCodigoAndPassword(oUsuarioBean.getCodigo(), oUsuarioBean.getPassword())
                .orElseThrow(() -> new ResourceNotFoundException("Wrong Usuario or password"));
        return JWTHelper.generateJWT(oUsuarioBean.getCodigo());
    }

    public String getSessionCodigo() {        
        if (oHttpServletRequest.getAttribute("codigo") instanceof String) {
            return oHttpServletRequest.getAttribute("codigo").toString();
        } else {
            return null;
        }
    }

    public UsuarioEntity getSessionUsuario() {
        if (this.getSessionCodigo() != null) {
            return oUsuarioRepository.findByCodigo(this.getSessionCodigo()).orElse(null);    
        } else {
            return null;
        }
    }

    public Boolean isSessionActive() {
        if (this.getSessionCodigo() != null) {
            return oUsuarioRepository.findByCodigo(this.getSessionCodigo()).isPresent();
        } else {
            return false;
        }
    }

    public Boolean isSupervisor() {
        if (this.getSessionCodigo() != null) {
            UsuarioEntity oUsuarioEntityInSession = oUsuarioRepository.findByCodigo(this.getSessionCodigo())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
            return Boolean.FALSE.equals(oUsuarioEntityInSession.getPuesto());
        } else {
            return false;
        }
    }

    public Boolean isUsuario() {
        if (this.getSessionCodigo() != null) {
            UsuarioEntity oUsuarioEntityInSession = oUsuarioRepository.findByCodigo(this.getSessionCodigo())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario not found"));
            return Boolean.TRUE.equals(oUsuarioEntityInSession.getPuesto());
        } else {
            return false;
        }
    }

    public void onlySupervisor() {
        if (!this.isSueprvisor()) {
            throw new UnauthorizedException("Only supervisor can do this");
        }
    }

    public void onlyUsuarios() {
        if (!this.isUsuario()) {
            throw new UnauthorizedException("Only Usuarios can do this");
        }
    }

    public void onlySupervisoresOrUsuarios() {
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

    public void onlySupervisoresOrUsuariosWithIisOwnData(Long usuario_id) {
        if (this.isSessionActive()) {
            if (!this.isSupervisor()) {
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

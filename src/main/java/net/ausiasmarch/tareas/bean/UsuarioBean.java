package net.ausiasmarch.taread.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioBean {

    private Long codigo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

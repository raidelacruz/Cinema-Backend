package com.app.cinemabackend.dto.request;

public class LoginRequestDTO {
    private String correoElectronico;
    private String password;

    public LoginRequestDTO() {}

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
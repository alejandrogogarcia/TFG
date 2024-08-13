package es.udc.tfg.app.rest.common;

public class JwtInfo {

    private Long userId;
    private String dni;
    private String role;

    public JwtInfo(String role, String dni, Long userId) {
        this.role = role;
        this.dni = dni;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


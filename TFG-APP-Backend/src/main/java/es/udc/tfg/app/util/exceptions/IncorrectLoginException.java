package es.udc.tfg.app.util.exceptions;

public class IncorrectLoginException extends Exception{

    private String dni;
    private String password;

    public IncorrectLoginException(String dni, String password) {

        this.dni = dni;
        this.password = password;

    }

    public String getDni() {
        return dni;
    }

    public String getPassword() {
        return password;
    }
}

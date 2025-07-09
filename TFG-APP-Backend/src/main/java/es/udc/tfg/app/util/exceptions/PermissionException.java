package es.udc.tfg.app.util.exceptions;

@SuppressWarnings("serial")
public class PermissionException extends Exception {

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException() {
        super("El usuario no tiene permisos para realizar la operacion");
    }
}
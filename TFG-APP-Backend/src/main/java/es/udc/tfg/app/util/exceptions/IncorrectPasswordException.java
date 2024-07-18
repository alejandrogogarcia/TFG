package es.udc.tfg.app.util.exceptions;

public class IncorrectPasswordException extends Exception{

    private String username;

    public IncorrectPasswordException(String username) {
        super("Incorrect Password (username = '" + username + "')");
        this.username = username;
    }

    public String getLoginName() {
        return username;
    }

    @Override
    public String toString() {
        return "IncorrectPasswordException [username = '" + username + "']";
    }
}

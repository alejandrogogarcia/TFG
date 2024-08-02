package es.udc.tfg.app.util.exceptions;

public class DisabledUserException extends Exception {

	private Object userId;
	private String className;

	public DisabledUserException(Object userId) {
		super("The user is disabled (userId = '" + userId +"')");
		this.userId = userId;
		this.className = className;
	}

	public Object getUserId() {
		return userId;
	}

	public String getClassName() {
		return className;
	}


}

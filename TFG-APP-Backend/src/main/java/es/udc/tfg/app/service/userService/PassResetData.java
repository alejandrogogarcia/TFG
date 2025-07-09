package es.udc.tfg.app.service.userService;

public class PassResetData {

	private String dni;

	private String token;

	private String password;

	public PassResetData() {
	}

	public PassResetData(String dni, String token, String password) {
		this.dni = dni;
		this.token = token;
		this.password = password;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

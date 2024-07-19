package es.udc.tfg.app.util.encrypt;

public class PasswordEncrypter {
	
	public static String crypt(String password) {
		return password;
	}

	public static boolean isCorrectPassword(String clearPassword, String encryptedPassword) {

		return clearPassword.equals(encryptedPassword);
	}

}

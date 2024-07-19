package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.InputValidationException;

public class RoleConversor {

	public static String roleToString(UserRole role) throws InputValidationException {

		if (role == null) {
			throw new InputValidationException("role", "It must not be null");
		}

		return role.toString();

	}

	public static UserRole stringToRole(String strRole) throws InputValidationException {

		UserRole role = null;
		try {
			role = UserRole.valueOf(strRole);
		} catch (IllegalArgumentException e) {
			throw new InputValidationException("strRole", "It must be: EMPLOYEE, CLERK OR ADMIN");
		}

		return role;

	}

}

package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.util.exceptions.InputValidationException;

public class BooleanConversor {

	public static String booleanToString(boolean value) throws InputValidationException {
		return Boolean.toString(value);
	}

	public static boolean stringToBoolean(String value) {
		if (value.trim().equalsIgnoreCase("true")) {
			return true;
		} else if (value.trim().equalsIgnoreCase("false")) {
			return false;
		} else {
			throw new IllegalArgumentException("Invalid boolean string: " + value);
		}
	}

}

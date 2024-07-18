package es.udc.tfg.app.util.validator;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.udc.tfg.app.util.exceptions.InputValidationException;

public class ValidatorProperties {

	public static void validateDni(String dni) throws InputValidationException {

		String stringPattern = "^[0-9]{8}[a-zA-Z]{1}$";
		Pattern regexPattern = Pattern.compile(stringPattern);
		Matcher regexMatcher = regexPattern.matcher(dni);

		if (dni == null || !regexMatcher.matches()) {
			throw new InputValidationException(dni, "It must be '8' numbers and '1' letter");
		}

	}

	public static void validateEmail(String email) throws InputValidationException {

		String stringPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern regexPattern = Pattern.compile(stringPattern);
		Matcher regexMatcher = regexPattern.matcher(email.toLowerCase());

		if (email.length() > 80 || !regexMatcher.matches() || email == null ){
			throw new InputValidationException(email,
					"It must be less than 80 char and it must have valid email characters");
		}

	}

	public static void validatePassword(String pwd) throws InputValidationException {

		if (pwd == null || pwd.length() < 6 || pwd.length() > 30) {
			throw new InputValidationException(pwd, "It cannot be null and must be between '6' and '30' char");
		}

	}

	public static void validateString(String string) throws InputValidationException {

		if (string == null || string.length() < 1) {
			throw new InputValidationException(string, "It cannot be null or empty");
		}

	}

	public static void validateCalendarPastDate(Calendar calendar) throws InputValidationException {

		Calendar now = Calendar.getInstance();

		if (calendar.after(now) || calendar == null) {
			throw new InputValidationException("Calendar", "Should be a past date");
		}

	}

	public static void validatePostCode(Long postCode) throws InputValidationException {

		if (postCode == null || postCode.toString().length() != 5) {
			throw new InputValidationException("Post Code", "It cannot be null or whith size other than 5");
		}

	}

	public static void validatePhoneNumbre(Long phoneNumber) throws InputValidationException {

		if (phoneNumber.toString().length() != 9 || phoneNumber == null) {
			throw new InputValidationException("Phone Number", "It cannot be null or whith size other than 9");
		}

	}

	public static void validatePositiveInteger(Integer num) throws InputValidationException {

		if (num < 0) {
			throw new InputValidationException("Integer number value", " It cannot be null or less than 0");
		}

	}
	
	public static void validatePositiveFloat(Float num) throws InputValidationException {

		if (num < 0) {
			throw new InputValidationException("Long number value", " It cannot be null or less than 0");
		}

	}

}

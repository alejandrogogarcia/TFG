package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.util.enums.Languages;
import es.udc.tfg.app.util.exceptions.InputValidationException;

public class LanguageConversor {

	public static String languageToString(Languages language) throws InputValidationException {

		if (language == null) {
			throw new InputValidationException("language", "It must not be null");
		}

		return language.toString();
	}

	public static Languages stringToLanguage(String language) throws InputValidationException {

		Languages lang = null;
		try {
			lang = Languages.valueOf(language);
		} catch (IllegalArgumentException e) {
			throw new InputValidationException("language", "It must be: ESP, GAL, ENG");
		}

		return lang;
	}

}

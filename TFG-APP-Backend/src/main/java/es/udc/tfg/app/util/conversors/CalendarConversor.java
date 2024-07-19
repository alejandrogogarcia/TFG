package es.udc.tfg.app.util.conversors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.udc.tfg.app.util.exceptions.InputValidationException;


public class CalendarConversor {

	public static String calendarToString(Calendar calendar) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setCalendar(calendar);

		return dateFormat.format(calendar.getTime());
	}

	public static Calendar stringToCalendar(String stringDate) throws InputValidationException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar;

		try {
			Date date = dateFormat.parse(stringDate);
			calendar = Calendar.getInstance();
			calendar.setTime(date);
		} catch (Exception e) {
			throw new InputValidationException("stringDate", "Error parsing 'stringDate' = [" + stringDate + "] "
					+ "to 'Calendar'. Error Message: " + e.getMessage());
		}

		return calendar;

	}

}

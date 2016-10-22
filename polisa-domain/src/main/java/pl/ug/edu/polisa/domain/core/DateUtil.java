package pl.ug.edu.polisa.domain.core;

import org.joda.time.LocalDate;

/**
 * Util do operacji na datach
 *
 */
public class DateUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * Konwersja LocalDate do java.sql.Date
	 * 
	 * @param localDate
	 * @return
	 */
	public static java.sql.Date localDateToSqlDate(LocalDate localDate) {
		if (localDate == null) {
			return null;
		}
		return java.sql.Date.valueOf(localDate.toString(DateUtil.DEFAULT_DATE_FORMAT));
	}

	/**
	 * Konwersja java.sql.Date na LocalDate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocalDate(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		return new LocalDate(date);
	}

}

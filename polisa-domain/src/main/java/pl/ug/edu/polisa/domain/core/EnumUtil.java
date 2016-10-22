package pl.ug.edu.polisa.domain.core;

/**
 * Util Dla enumów
 */
public class EnumUtil {

	/**
	 * Metoda byCode dla enumów z kodem typu String
	 * 
	 * @param <T>
	 *            typ obsługiwanego enuma
	 * @param code
	 *            kod poszukiwanej wartości enuma
	 * @param enums
	 *            tablica wartości enuma
	 * @return poszukiwana wartość enuma
	 */
	public static <T extends EnumByCodeString<String>> T byCode(String code, T... enums) {
		for (T t : enums) {
			if (t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}

}

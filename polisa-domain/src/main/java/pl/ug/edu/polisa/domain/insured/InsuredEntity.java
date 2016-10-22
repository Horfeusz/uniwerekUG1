package pl.ug.edu.polisa.domain.insured;

import pl.ug.edu.polisa.domain.core.Entity;
import pl.ug.edu.polisa.domain.policy.APolicyParent;

/**
 * Encja reprezentująca dane ubezpieczonego
 *
 */
@Entity(tableName = "UBEZPIECZONY")
public class InsuredEntity extends APolicyParent {

	/** Imię */
	private String firstName;

	/** Nazwisko */
	private String secondName;

	/** Wiek */
	private Integer age;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the secondName
	 */
	public String getSecondName() {
		return secondName;
	}

	/**
	 * @param secondName
	 *            the secondName to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String displayable() {
		StringBuilder sb = new StringBuilder();
		sb.append(firstName).append(" ").append(secondName);
		return sb.toString();
	}

}

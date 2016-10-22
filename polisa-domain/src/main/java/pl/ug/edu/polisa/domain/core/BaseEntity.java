package pl.ug.edu.polisa.domain.core;

/**
 * Klasa bazowa encji
 * 
 * @author michalh
 *
 */
public abstract class BaseEntity {

	/**
	 * Identyfikator encji
	 */
	private Integer id;

	/**
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Metoda to tekstowej reprezentacji obiektu
	 * 
	 * @return
	 */
	public abstract String displayable();

}

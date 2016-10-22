package pl.ug.edu.polisa.domain.insured;

import pl.ug.edu.polisa.domain.core.Filter;

/**
 * Filtr ubezpieczonych
 *
 */
public class InsuredFiter implements Filter {

	/**
	 * Identyfikator polisy
	 */
	private Integer polisyId;

	// TODO Dodać wyszukiwanie po imię i nazwisko

	/**
	 * @return the polisyId
	 */
	public Integer getPolisyId() {
		return polisyId;
	}

	/**
	 * @param polisyId
	 *            the polisyId to set
	 */
	public void setPolisyId(Integer polisyId) {
		this.polisyId = polisyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InsuredFiter [polisyId=" + polisyId + "]";
	}

}

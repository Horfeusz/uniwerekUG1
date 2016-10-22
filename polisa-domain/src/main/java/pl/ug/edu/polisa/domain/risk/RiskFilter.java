package pl.ug.edu.polisa.domain.risk;

import pl.ug.edu.polisa.domain.core.Filter;

/**
 * Filtr dla ryzyk
 *
 */
public class RiskFilter implements Filter {

	/**
	 * Identyfikator polisy
	 */
	private Integer polisyId;

	/**
	 * Ubezpueczenie
	 */
	private Insurance insurance;

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

	/**
	 * @return the insurance
	 */
	public Insurance getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance
	 *            the insurance to set
	 */
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RiskFilter [polisyId=" + polisyId + ", insurance=" + insurance + "]";
	}

}

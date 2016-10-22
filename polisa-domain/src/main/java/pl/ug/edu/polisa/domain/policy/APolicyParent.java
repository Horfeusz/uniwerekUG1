package pl.ug.edu.polisa.domain.policy;

import pl.ug.edu.polisa.domain.core.BaseEntity;

/**
 * Abstrakcyjna klasa bazowa dla encji które wskazują na polisę
 *
 */
public abstract class APolicyParent extends BaseEntity {

	/**
	 * Identyfikator polisy
	 */
	private Integer policyId;

	/**
	 * @return the policyId
	 */
	public Integer getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId
	 *            the policyId to set
	 */
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

}

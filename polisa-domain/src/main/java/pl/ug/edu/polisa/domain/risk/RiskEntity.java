package pl.ug.edu.polisa.domain.risk;

import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.joda.time.LocalDate;

import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.core.Entity;
import pl.ug.edu.polisa.domain.policy.APolicyParent;

/**
 * Encja reprezentująca ryzyko
 *
 */
@Entity(tableName = "RYZYKO")
public class RiskEntity extends APolicyParent {

	/**
	 * Data rozpoczęcia ubezpieczenia
	 */
	private LocalDate protectionOn;

	/**
	 * Data zakończenia ubrzpieczenia
	 */
	private LocalDate protectionOff;

	/**
	 * Ubezpieczenie na ryzyku
	 */
	private Insurance insurance;

	/**
	 * Składka na ryzyku
	 */
	private BigMoney premium = BigMoneyHelper.of(BigDecimal.ZERO);

	/**
	 * @return the protectionOn
	 */
	public LocalDate getProtectionOn() {
		return protectionOn;
	}

	/**
	 * @param protectionOn
	 *            the protectionOn to set
	 */
	public void setProtectionOn(LocalDate protectionOn) {
		this.protectionOn = protectionOn;
	}

	/**
	 * @return the protectionOff
	 */
	public LocalDate getProtectionOff() {
		return protectionOff;
	}

	/**
	 * @param protectionOff
	 *            the protectionOff to set
	 */
	public void setProtectionOff(LocalDate protectionOff) {
		this.protectionOff = protectionOff;
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

	/**
	 * @return the premium
	 */
	public BigMoney getPremium() {
		return premium;
	}

	/**
	 * @param premium
	 *            the premium to set
	 */
	public void setPremium(BigMoney premium) {
		this.premium = premium;
	}

	@Override
	public String displayable() {
		return getId().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RiskEntity [protectionOn=" + protectionOn + ", protectionOff=" + protectionOff + ", policyId="
				+ getPolicyId() + ", insurance=" + insurance + ", premium=" + premium + ", getId()=" + getId() + "]";
	}

}

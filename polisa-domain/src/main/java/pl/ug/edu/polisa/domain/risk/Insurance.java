package pl.ug.edu.polisa.domain.risk;

import java.math.BigDecimal;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.core.EnumByCodeString;
import pl.ug.edu.polisa.domain.core.EnumUtil;
import pl.ug.edu.polisa.domain.policy.InsurancePackage;

/**
 * Słownik ubezpieczeń
 *
 */
public enum Insurance implements EnumByCodeString<String> {

	/** OC Samochodu */
	OC("OC", InsurancePackage.CAR, BigMoneyHelper.of(350)),

	/** AC Samochodu */
	AC("AC", InsurancePackage.CAR, BigMoneyHelper.of(500)),

	/** NNW */
	NNW("NW", InsurancePackage.CAR, BigMoneyHelper.of(15)),

	/** Podłogi Dom */
	FLOOR("PD", InsurancePackage.HOUSE, BigMoneyHelper.of(230)),

	/** Ściany */
	WALL("SC", InsurancePackage.HOUSE, BigMoneyHelper.of(1500)),

	KITCHEN_FERNITURE("KU", InsurancePackage.FURNITURE,
			BigMoney.of(CurrencyUnit.getInstance("PLN"), BigDecimal.valueOf(2000))),

	ROOM_FERNITURE("PO", InsurancePackage.FURNITURE,
			BigMoney.of(CurrencyUnit.getInstance("PLN"), BigDecimal.valueOf(300)))

	;

	/**
	 * Kod
	 */
	private String code;

	/**
	 * Pakiet do którego należy ubezpieczenie
	 */
	private InsurancePackage insurancePackage;

	/**
	 * Składka bazowa dla ubezpieczenia
	 */
	private BigMoney basePremium;

	private Insurance(String code, InsurancePackage insurancePackage, BigMoney basePremium) {
		this.code = code;
		this.insurancePackage = insurancePackage;
		this.basePremium = basePremium;
	}

	public String getCode() {
		return this.code;
	}

	public InsurancePackage getInsurancePackage() {
		return this.insurancePackage;
	}

	public BigMoney getBasePremium() {
		return basePremium;
	}

	/**
	 * Metoda zwraca instancje enuma po kodzie
	 * 
	 * @param code
	 *            kod enuma
	 * @return
	 */
	public static Insurance byCode(String code) {
		return EnumUtil.byCode(code, Insurance.values());
	}

}

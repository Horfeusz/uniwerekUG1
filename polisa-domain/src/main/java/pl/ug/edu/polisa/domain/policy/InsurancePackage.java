package pl.ug.edu.polisa.domain.policy;

import java.math.BigDecimal;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import pl.ug.edu.polisa.domain.core.CalculateProvince;
import pl.ug.edu.polisa.domain.core.EnumByCodeString;
import pl.ug.edu.polisa.domain.core.EnumUtil;

/**
 * Enum z pakietami ubezpieczeniowymi
 */
public enum InsurancePackage implements EnumByCodeString<String> {

	CAR("C", ImmutableMap.of(CalculateProvince.POMORSKIE, new BigDecimal("0.2"), CalculateProvince.MAZOWIECKIE,
			new BigDecimal("0.25"), CalculateProvince.POZOSTAŁE, new BigDecimal("0.18"))),

	// FIXME Uzupełnić dla mazowieckiego i pozostałych
	HOUSE("H", ImmutableMap.of(CalculateProvince.POMORSKIE, new BigDecimal("1.5"))),

	FURNITURE("F", ImmutableMap.of(CalculateProvince.POMORSKIE, new BigDecimal("5.6")))

	;

	private String code;

	//FIXME - Dopisać getera
	private Map<CalculateProvince, BigDecimal> map;

	private InsurancePackage(String code, Map<CalculateProvince, BigDecimal> map) {
		this.code = code;
		this.map = map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.ug.edu.polisa.domain.core.EnumByCodeString#getCode()
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * Metoda zwraca instancje enuma po kodzie
	 * 
	 * @param code
	 *            kod enuma
	 * @return
	 */
	public static InsurancePackage byCode(String code) {
		return EnumUtil.byCode(code, InsurancePackage.values());
	}

}

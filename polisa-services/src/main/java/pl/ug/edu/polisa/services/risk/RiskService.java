package pl.ug.edu.polisa.services.risk;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.joda.money.BigMoney;

import pl.ug.edu.polisa.domain.risk.Insurance;
import pl.ug.edu.polisa.domain.risk.RiskEntity;

public class RiskService {

	public RiskEntity calculate(RiskEntity risk, Integer insuredAge) {

		if(Insurance.OC.equals(risk.getInsurance())) {
			BigMoney premium = Insurance.OC.getBasePremium();
			if (insuredAge.intValue() > 28) {
				premium = premium.multiplyRetainScale(new BigDecimal("0.85"), RoundingMode.HALF_UP); 
			} else if (insuredAge.intValue() < 20) {
				premium = premium.multiplyRetainScale(new BigDecimal("1.25"), RoundingMode.HALF_UP);
			}
			risk.setPremium(premium);
		} else if (Insurance.AC.equals(risk.getInsurance())) {
			BigMoney premium = Insurance.AC.getBasePremium();
			if (insuredAge.intValue() > 28) {
				premium = premium.minus(100); 
			} else if (insuredAge.intValue() < 20) {
				premium = premium.plus(100);
			}
			risk.setPremium(premium);			
		}
		return risk;
	}

}

package pl.ug.edu.polisa.services.policy;

import java.sql.SQLException;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.time.LocalTime;

import com.google.common.base.Strings;

import pl.ug.edu.polisa.dao.insured.InsuredDao;
import pl.ug.edu.polisa.dao.policy.PolicyDao;
import pl.ug.edu.polisa.dao.risk.RiskDao;
import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.insured.InsuredEntity;
import pl.ug.edu.polisa.domain.insured.InsuredFiter;
import pl.ug.edu.polisa.domain.policy.PolicyEntity;
import pl.ug.edu.polisa.domain.policy.PolicyFilter;
import pl.ug.edu.polisa.domain.risk.RiskEntity;
import pl.ug.edu.polisa.domain.risk.RiskFilter;
import pl.ug.edu.polisa.services.risk.RiskService;

/**
 * Serwis do operacji na polisie
 *
 */
public class PolicyService {

	private PolicyDao policyDao = new PolicyDao();

	private RiskDao riskDao = new RiskDao();

	private InsuredDao insuredDao = new InsuredDao();

	private RiskService riskService = new RiskService();

	private String genNumerPolisy(String wyroznik) {
		LocalTime time = new LocalTime();
		String policyNumber = wyroznik + Strings.padStart(String.valueOf(time.getMillisOfDay()), 10, '0');
		return policyNumber;
	}

	/**
	 * Utworzenie polisy z ryzykami i ubezpieczonymi
	 * 
	 * @param policyEntity
	 * @return
	 * @throws SQLException
	 */
	public PolicyEntity create(PolicyEntity policyEntity) throws SQLException {
		if (policyEntity == null) {
			return null;
		}
		String policyNumber = genNumerPolisy(policyEntity.getInsurancePackage().getCode());
		policyEntity.setPolicyNumber(policyNumber);
		policyDao.create(policyEntity);

		PolicyFilter filter = new PolicyFilter();
		filter.setPolicyNumber(policyNumber);
		List<PolicyEntity> policies = policyDao.select(filter);
		PolicyEntity result = policies.iterator().next();

		// Ryzyka
		if (policyEntity.getRisks() != null && policyEntity.getRisks().size() > 0) {
			for (RiskEntity risk : policyEntity.getRisks()) {
				risk.setPolicyId(result.getId());
				riskDao.create(risk);
			}
		}

		// Ubezpieczeni
		if (policyEntity.getInsureds() != null && policyEntity.getInsureds().size() > 0) {
			for (InsuredEntity insured : policyEntity.getInsureds()) {
				insured.setPolicyId(result.getId());
				insuredDao.create(insured);
			}
		}

		policyEntity.setId(result.getId());

		InsuredFiter insuredFilter = new InsuredFiter();
		insuredFilter.setPolisyId(result.getId());
		policyEntity.setInsureds(insuredDao.select(insuredFilter));

		RiskFilter riskFilter = new RiskFilter();
		riskFilter.setPolisyId(result.getId());
		policyEntity.setRisks(riskDao.select(riskFilter));

		return policyEntity;
	}

	/**
	 * Wyliczam składkę i zapisuje na bazie
	 * 
	 * @param policy
	 */
	public PolicyEntity calculate(PolicyEntity policy) {

		if (policy.getRisks() != null && policy.getRisks().size() > 0 && policy.getInsureds() != null
				&& policy.getInsureds().size() > 0) {
			int age = 200;

			// Szukam najmłodszego ubezpieczonego
			for (InsuredEntity insuredd : policy.getInsureds()) {
				if (insuredd.getAge().intValue() < age) {
					age = insuredd.getAge().intValue();
				}
			}

			// Wyliczam sumę składek na polisie
			BigMoney premium = BigMoneyHelper.of(0);
			for (RiskEntity risk : policy.getRisks()) {
				risk = riskService.calculate(risk, Integer.valueOf(age));
				premium = premium.plus(risk.getPremium());
			}
			policy.setPremium(premium);
		}
		return policy;
	}

}

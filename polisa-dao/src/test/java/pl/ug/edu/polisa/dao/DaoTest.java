package pl.ug.edu.polisa.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.base.Strings;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import pl.ug.edu.polisa.dao.insured.InsuredDao;
import pl.ug.edu.polisa.dao.policy.PolicyDao;
import pl.ug.edu.polisa.dao.risk.RiskDao;
import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.insured.InsuredEntity;
import pl.ug.edu.polisa.domain.insured.InsuredFiter;
import pl.ug.edu.polisa.domain.policy.InsurancePackage;
import pl.ug.edu.polisa.domain.policy.PolicyEntity;
import pl.ug.edu.polisa.domain.policy.PolicyFilter;
import pl.ug.edu.polisa.domain.risk.Insurance;
import pl.ug.edu.polisa.domain.risk.RiskEntity;
import pl.ug.edu.polisa.domain.risk.RiskFilter;

/**
 * Klasa testowa DAO RISK
 *
 */
public class DaoTest extends TestCase {

	private PolicyDao policydao;

	private RiskDao riskdao;

	private InsuredDao insuredDao;

	public DaoTest(String testName) throws Exception {
		super(testName);
		policydao = new PolicyDao();
		riskdao = new RiskDao();
		insuredDao = new InsuredDao();
	}

	public static Test suite() {
		return new TestSuite(DaoTest.class);
	}

	private String genNumerPolisy(String wyroznik) {
		LocalTime time = new LocalTime();
		String policyNumber = wyroznik + Strings.padStart(String.valueOf(time.getMillisOfDay()), 10, '0');
		return policyNumber;
	}

	/**
	 * Test utworzenia polisy
	 * 
	 * @throws SQLException
	 */
	public void testPolicy() throws SQLException {

		String policyNumber = genNumerPolisy("CAR");
		PolicyEntity policy = new PolicyEntity();
		policy.setPolicyNumber(policyNumber);
		policy.setPremium(BigMoneyHelper.of("1.13"));
		
		policy.setProtectionOn(LocalDate.now());
		policy.setProtectionOff(LocalDate.now().plusYears(1));
		policy.setInsurancePackage(InsurancePackage.CAR);
		policydao.create(policy);

		PolicyFilter filter = new PolicyFilter();
		filter.setPolicyNumber(policyNumber);
		List<PolicyEntity> result = new ArrayList<PolicyEntity>();
		result = policydao.select(filter);

		assertNotNull(result);
		assertEquals(result.isEmpty(), false);

		policy = result.iterator().next();
		Integer policyId = policy.getId();
		policydao.delete(policy);

		policy = policydao.retrieve(policyId);
		assertEquals(policy == null, true);
	}

	private PolicyEntity createPolicy(LocalDate protectionOn, LocalDate protectionOff) throws SQLException {
		String policyNumber = genNumerPolisy("HOS");
		// Stworze polise
		PolicyEntity policy = new PolicyEntity();
		policy.setPolicyNumber(policyNumber);
		policy.setPremium(BigMoneyHelper.of(150.56));
		policy.setProtectionOn(protectionOn);
		policy.setProtectionOff(protectionOff);
		policy.setInsurancePackage(InsurancePackage.HOUSE);
		policydao.create(policy);

		// Szukam polise
		PolicyFilter policyfilter = new PolicyFilter();
		policyfilter.setPolicyNumber(policyNumber);
		List<PolicyEntity> result = new ArrayList<PolicyEntity>();
		result = policydao.select(policyfilter);
		assertNotNull(result);
		assertEquals(result.isEmpty(), false);
		policy = result.iterator().next();
		return policy;
	}

	public void testRisk() throws SQLException {
		LocalDate protectionOn = LocalDate.now();
		LocalDate protectionOff = LocalDate.now().plusYears(1);
		PolicyEntity policy = createPolicy(protectionOn, protectionOff);
		Integer policyId = policy.getId();

		RiskEntity r1 = new RiskEntity();
		r1.setInsurance(Insurance.FLOOR);
		r1.setPolicyId(policyId);
		r1.setPremium(BigMoneyHelper.of(BigDecimal.valueOf(100.56)));
		r1.setProtectionOn(protectionOn);
		r1.setProtectionOff(protectionOff);
		riskdao.create(r1);
		RiskEntity r2 = new RiskEntity();
		r2.setInsurance(Insurance.WALL);
		r2.setPolicyId(policyId);
		r2.setPremium(BigMoneyHelper.of(BigDecimal.valueOf(50)));
		r2.setProtectionOn(protectionOn);
		r2.setProtectionOff(protectionOff);
		riskdao.create(r2);

		// Szukam ryzyk
		RiskFilter riskFilter = new RiskFilter();
		riskFilter.setPolisyId(policyId);
		List<RiskEntity> risks = riskdao.select(riskFilter);
		assertNotNull(risks);
		assertEquals(risks.isEmpty(), false);
		assertEquals(risks.size(), 2);

		// Sprawdzam retrivy
		for (RiskEntity risk : risks) {
			RiskEntity tempRisk = riskdao.retrieve(risk.getId());
			assertNotNull(tempRisk);
			assertEquals(risk.getId().intValue() == tempRisk.getId().intValue(), true);
			assertEquals(risk.getInsurance().equals(tempRisk.getInsurance()), true);
		}

		// usuwamy
		for (RiskEntity risk : risks) {
			riskdao.delete(risk);
		}
		policydao.delete(policy);
	}

	public void testInsured() throws SQLException {
		PolicyEntity policy = createPolicy(LocalDate.now(), LocalDate.now().plusYears(1));
		Integer policyId = policy.getId();

		InsuredEntity insured = new InsuredEntity();
		insured.setAge(45);
		insured.setFirstName("Jan");
		insured.setSecondName("Kowalski");
		insured.setPolicyId(policyId);
		insuredDao.create(insured);

		InsuredFiter filter = new InsuredFiter();
		filter.setPolisyId(policyId);
		List<InsuredEntity> insureds = insuredDao.select(filter);
		assertNotNull(insureds);
		assertEquals(insureds.isEmpty(), false);
		assertEquals(insureds.size(), 1);

		for (InsuredEntity insuredd : insureds) {
			InsuredEntity testInsured = insuredDao.retrieve(insuredd.getId());
			assertNotNull(testInsured);
			assertEquals(insuredd.getFirstName().equals(testInsured.getFirstName()), true);
			assertEquals(insuredd.getSecondName().equals(testInsured.getSecondName()), true);
			assertEquals(insuredd.getAge().equals(testInsured.getAge()), true);
		}

		// usuwamy
		for (InsuredEntity insuredd : insureds) {
			insuredDao.delete(insuredd);
		}
		policydao.delete(policy);
		
		
	}

}

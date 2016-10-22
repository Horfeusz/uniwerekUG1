package pl.ug.edu.polisa.dao.policy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Strings;

import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.policy.InsurancePackage;
import pl.ug.edu.polisa.domain.policy.PolicyEntity;
import pl.ug.edu.polisa.domain.policy.PolicyFilter;

/**
 * Klasa testowa polisy
 * 
 * @author michalh
 *
 */
public class PolicyTest {

	private static PolicyDao policydao = new PolicyDao();

	private PolicyEntity policy;

	/**
	 * MEtoda uruchamiana na starcie załego testu
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		policydao = new PolicyDao();
	}

	@Before
	public void setUp() {
		String policyNumber = genNumerPolisy("CAR");
		policy = new PolicyEntity();
		policy.setPolicyNumber(policyNumber);
		policy.setPremium(BigMoneyHelper.of("1.13"));
		policy.setProtectionOn(LocalDate.now());
		policy.setProtectionOff(LocalDate.now().plusYears(1));
		policy.setInsurancePackage(InsurancePackage.CAR);		
	}

	/**
	 * Wygenerowanie numeru polisy
	 * 
	 * @param wyroznik
	 * @return
	 */
	private static String genNumerPolisy(String wyroznik) {
		LocalTime time = new LocalTime();
		String policyNumber = wyroznik + Strings.padStart(String.valueOf(time.getMillisOfDay()), 10, '0');
		return policyNumber;
	}

	@Test
	public void createPolicy() throws SQLException {

		policy.setProtectionOn(LocalDate.now());
		policy.setProtectionOff(LocalDate.now().plusYears(1));
		policy.setInsurancePackage(InsurancePackage.CAR);
		policydao.create(policy);

		PolicyFilter filter = new PolicyFilter();
		filter.setPolicyNumber(policy.getPolicyNumber());
		List<PolicyEntity> result = new ArrayList<PolicyEntity>();
		result = policydao.select(filter);

		Assert.assertNotNull(result);
		Assert.assertEquals(result.isEmpty(), false);

		policy = result.iterator().next();
		Integer policyId = policy.getId();
		policydao.delete(policy);

		policy = policydao.retrieve(policyId);
		Assert.assertEquals(policy == null, true);
	}
}

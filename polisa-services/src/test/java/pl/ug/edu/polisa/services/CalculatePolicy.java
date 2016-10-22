package pl.ug.edu.polisa.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.ug.edu.polisa.dao.policy.PolicyDao;
import pl.ug.edu.polisa.domain.policy.PolicyEntity;
import pl.ug.edu.polisa.domain.policy.PolicyFilter;
import pl.ug.edu.polisa.domain.risk.RiskEntity;
import pl.ug.edu.polisa.services.policy.PolicyService;

public class CalculatePolicy {

	static PolicyService policyService = new PolicyService();

	static PolicyDao policyDao = new PolicyDao();

	public static void main(String[] argc) {
		PolicyFilter filter = new PolicyFilter();
		filter.setPolicyNumber("C0039088636");
		List<PolicyEntity> result = new ArrayList<PolicyEntity>();
		try {
			result = policyDao.select(filter);
			PolicyEntity policy = result.iterator().next();

			System.out.println(policy);

			policyService.calculate(policy);

			System.out.println("składka: " + policy.getPremium());
			for (RiskEntity risk : policy.getRisks()) {
				System.out.println("Składka na ryzyku: " + risk.getInsurance().getCode() + "  " + risk.getPremium());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

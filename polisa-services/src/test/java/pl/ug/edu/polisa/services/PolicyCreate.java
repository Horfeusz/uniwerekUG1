package pl.ug.edu.polisa.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.insured.InsuredEntity;
import pl.ug.edu.polisa.domain.policy.InsurancePackage;
import pl.ug.edu.polisa.domain.policy.PolicyEntity;
import pl.ug.edu.polisa.domain.risk.Insurance;
import pl.ug.edu.polisa.domain.risk.RiskEntity;
import pl.ug.edu.polisa.services.policy.PolicyService;

public class PolicyCreate {

	static PolicyService policyService = new PolicyService();

	public static void main(String[] argc) {
		LocalDate protectionOn = LocalDate.now();
		LocalDate protectionOff = LocalDate.now().plusYears(1);

		PolicyEntity policy = new PolicyEntity();
		policy.setInsurancePackage(InsurancePackage.CAR);
		policy.setProtectionOff(protectionOff);
		policy.setProtectionOn(protectionOn);
		policy.setPremium(BigMoneyHelper.of(0));

		List<RiskEntity> risk = new ArrayList<RiskEntity>();
		RiskEntity r1 = new RiskEntity();
		r1.setInsurance(Insurance.OC);
		r1.setProtectionOn(protectionOn);
		r1.setProtectionOff(protectionOff);
		
		RiskEntity r2 = new RiskEntity();
		r2.setInsurance(Insurance.AC);
		r2.setProtectionOn(protectionOn);
		r2.setProtectionOff(protectionOff);
		
		risk.add(r1);
		risk.add(r2);
		policy.setRisks(risk);
		
		List<InsuredEntity> insureds = new ArrayList<InsuredEntity>();
		InsuredEntity insured = new InsuredEntity();
		insured.setFirstName("Tadeusz");
		insured.setSecondName("Kowalski");
		insured.setAge(30);
		insureds.add(insured);
		policy.setInsureds(insureds);
		
		try {
			policy = policyService.create(policy);
			
			System.out.println(policy);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}

}

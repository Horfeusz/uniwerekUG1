package pl.ug.edu.polisa.dao.policy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Strings;

import pl.ug.edu.polisa.dao.core.BaseCRUDDao;
import pl.ug.edu.polisa.dao.core.ConnectionFactory;
import pl.ug.edu.polisa.dao.insured.InsuredDao;
import pl.ug.edu.polisa.dao.risk.RiskDao;
import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.core.DateUtil;
import pl.ug.edu.polisa.domain.insured.InsuredFiter;
import pl.ug.edu.polisa.domain.policy.InsurancePackage;
import pl.ug.edu.polisa.domain.policy.PolicyEntity;
import pl.ug.edu.polisa.domain.policy.PolicyFilter;
import pl.ug.edu.polisa.domain.risk.RiskFilter;

/**
 * DAO dla polisy
 */
public class PolicyDao extends BaseCRUDDao<PolicyEntity, PolicyFilter> {

	Log log = LogFactory.getLog(PolicyDao.class);

	/** DAO Ryzyk */
	private RiskDao riskDao = new RiskDao();
	
	private InsuredDao insuredDao = new InsuredDao();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.ug.edu.polisa.dao.core.BaseCRUDDao#create(pl.ug.edu.polisa.domain.core
	 * .BaseEntity)
	 */
	@Override
	public void create(PolicyEntity policy) throws SQLException {
		validateObject(policy);
		String table = getTableNameAndValidate(policy);
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement(
				"INSERT INTO " + table + "(nr_polisy, skladka, data_od, data_do, pakiet) values (?, ?, ?, ?, ?)");
		ps.setString(1, policy.getPolicyNumber());
		ps.setBigDecimal(2, policy.getPremium().getAmount());
		ps.setDate(3, DateUtil.localDateToSqlDate(policy.getProtectionOn()));
		ps.setDate(4, DateUtil.localDateToSqlDate(policy.getProtectionOff()));
		ps.setString(5, policy.getInsurancePackage() != null ? policy.getInsurancePackage().getCode() : null);
		int row = ps.executeUpdate();
		log.info("Dodano " + row + " rekordów do tabeli " + table);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.ug.edu.polisa.dao.core.BaseCRUDDao#retrieve(java.lang.Long)
	 */
	@Override
	public PolicyEntity retrieve(Integer id) throws SQLException {
		PolicyEntity result = null;
		String query = "SELECT * FROM POLISA WHERE ID = ?";
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result = new PolicyEntity();
			result.setId(rs.getInt("id"));
			result.setPolicyNumber(rs.getString("nr_polisy"));
			result.setPremium(BigMoneyHelper.of(rs.getBigDecimal("skladka")));
			result.setProtectionOn(DateUtil.dateToLocalDate(rs.getDate("data_od")));
			result.setProtectionOff(DateUtil.dateToLocalDate(rs.getDate("data_do")));
			result.setInsurancePackage(InsurancePackage.byCode(rs.getString("pakiet")));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.ug.edu.polisa.dao.core.BaseCRUDDao#update(pl.ug.edu.polisa.domain.core
	 * .BaseEntity)
	 */
	@Override
	public void update(PolicyEntity policy) throws SQLException {
		validateObject(policy);
		String table = getTableNameAndValidate(policy);
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement("UPDATE " + table
				+ " set nr_polisy = ?, skladka = ?, data_od = ?, data_do = ?, pakiet = ? WHERE id = ?");
		ps.setString(1, policy.getPolicyNumber());
		ps.setBigDecimal(2, policy.getPremium().getAmount());
		ps.setDate(3, DateUtil.localDateToSqlDate(policy.getProtectionOn()));
		ps.setDate(4, DateUtil.localDateToSqlDate(policy.getProtectionOff()));
		ps.setString(5, policy.getInsurancePackage() != null ? policy.getInsurancePackage().getCode() : null);
		ps.setLong(6, policy.getId());
		int row = ps.executeUpdate();
		log.info("Zaktualizowano " + row + " rekordów do tabeli " + table);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.ug.edu.polisa.dao.core.BaseCRUDDao#select(pl.ug.edu.polisa.domain.core
	 * .Filter)
	 */
	@Override
	public List<PolicyEntity> select(PolicyFilter filter) throws SQLException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int whereCounter = 0;
		List<PolicyEntity> result = new ArrayList<PolicyEntity>();
		if (filter == null) {
			return result;
		}
		StringBuilder sb = new StringBuilder("SELECT * FROM POLISA WHERE 1=1");
		if (!Strings.isNullOrEmpty(filter.getPolicyNumber())) {
			whereCounter++;
			sb.append(" and nr_polisy = ?");
			map.put("nr_polisy", Integer.valueOf(whereCounter));
		}
		if (filter.getInsurancePackage() != null) {
			whereCounter++;
			sb.append(" and pakiet = ?");
			map.put("pakiet", Integer.valueOf(whereCounter));
		}
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement(sb.toString());
		if (!Strings.isNullOrEmpty(filter.getPolicyNumber())) {
			ps.setString(map.get("nr_polisy").intValue(), filter.getPolicyNumber());
		}
		if (filter.getInsurancePackage() != null) {
			ps.setString(map.get("pakiet").intValue(), filter.getInsurancePackage().getCode());
		}

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			PolicyEntity policy = new PolicyEntity();
			policy.setId(rs.getInt("id"));
			policy.setPolicyNumber(rs.getString("nr_polisy"));
			policy.setPremium(BigMoneyHelper.of(rs.getBigDecimal("skladka")));
			policy.setProtectionOn(DateUtil.dateToLocalDate(rs.getDate("data_od")));
			policy.setProtectionOff(DateUtil.dateToLocalDate(rs.getDate("data_do")));
			policy.setInsurancePackage(InsurancePackage.byCode(rs.getString("pakiet")));
			afterSelect(policy);
			result.add(policy);
		}
		return result;
	}

	/**
	 * Operacje które wykonują się po wczytaniu ensji danych polisy
	 * 
	 * @param policyEntity
	 * @throws SQLException
	 */
	private void afterSelect(PolicyEntity policyEntity) throws SQLException {
		RiskFilter riskFilter = new RiskFilter();
		riskFilter.setPolisyId(policyEntity.getId());
		policyEntity.setRisks(riskDao.select(riskFilter));
		
		InsuredFiter insuredFilter = new InsuredFiter();
		insuredFilter.setPolisyId(policyEntity.getId());
		policyEntity.setInsureds(insuredDao.select(insuredFilter));
	}

}

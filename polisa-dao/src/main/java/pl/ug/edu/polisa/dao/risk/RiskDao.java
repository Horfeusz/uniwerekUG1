package pl.ug.edu.polisa.dao.risk;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.ug.edu.polisa.dao.core.BaseCRUDDao;
import pl.ug.edu.polisa.dao.core.ConnectionFactory;
import pl.ug.edu.polisa.domain.core.BigMoneyHelper;
import pl.ug.edu.polisa.domain.core.DateUtil;
import pl.ug.edu.polisa.domain.risk.Insurance;
import pl.ug.edu.polisa.domain.risk.RiskEntity;
import pl.ug.edu.polisa.domain.risk.RiskFilter;

/**
 * DAO do obsługi Ryzyka
 * 
 * @author Michał Hoffmann
 *
 */
public class RiskDao extends BaseCRUDDao<RiskEntity, RiskFilter> {

	Log log = LogFactory.getLog(RiskDao.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.ug.edu.polisa.dao.core.BaseCRUDDao#create(pl.ug.edu.polisa.domain.core
	 * .BaseEntity)
	 */
	@Override
	public void create(RiskEntity risk) throws SQLException {
		validateObject(risk);
		String table = getTableNameAndValidate(risk);
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement("INSERT INTO " + table
				+ "(data_od, data_do, polisa_id, ubezpieczenie, skladka) values (?, ?, ?, ?, ?)");
		ps.setDate(1, DateUtil.localDateToSqlDate(risk.getProtectionOn()));
		ps.setDate(2, DateUtil.localDateToSqlDate(risk.getProtectionOff()));
		ps.setInt(3, risk.getPolicyId());
		ps.setString(4, risk.getInsurance() != null ? risk.getInsurance().getCode() : null);
		ps.setBigDecimal(5, risk.getPremium().getAmount());
		int row = ps.executeUpdate();
		log.info("Dodano " + row + " rekordów do tabeli " + table);
	}

	/**
	 * Odczytuje obiekt ryzyka z ResultSeta
	 * 
	 * @param rs
	 * @return
	 */
	private RiskEntity getRiskFromRs(ResultSet rs) throws SQLException {
		RiskEntity result = new RiskEntity();
		result.setId(rs.getInt("id"));
		result.setProtectionOn(DateUtil.dateToLocalDate(rs.getDate("data_od")));
		result.setProtectionOff(DateUtil.dateToLocalDate(rs.getDate("data_do")));
		result.setPolicyId(rs.getInt("polisa_id"));
		result.setInsurance(Insurance.byCode(rs.getString("ubezpieczenie")));
		result.setPremium(BigMoneyHelper.of(rs.getBigDecimal("skladka")));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.ug.edu.polisa.dao.core.BaseCRUDDao#retrieve(java.lang.Integer)
	 */
	@Override
	public RiskEntity retrieve(Integer id) throws SQLException {
		RiskEntity result = null;
		String query = "SELECT * FROM RYZYKO WHERE ID = ?";
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result = getRiskFromRs(rs);
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
	public void update(RiskEntity risk) throws SQLException {
		validateObject(risk);
		String table = getTableNameAndValidate(risk);
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement("UPDATE " + table
				+ " set data_od = ?, data_do = ?, polisa_id = ?, ubezpieczenie = ?, skladka = ? WHERE id = ?");
		ps.setDate(1, DateUtil.localDateToSqlDate(risk.getProtectionOn()));
		ps.setDate(2, DateUtil.localDateToSqlDate(risk.getProtectionOff()));
		ps.setInt(3, risk.getPolicyId());
		ps.setString(4, risk.getInsurance() != null ? risk.getInsurance().getCode() : null);
		ps.setBigDecimal(5, risk.getPremium().getAmount());
		ps.setLong(6, risk.getId());
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
	public List<RiskEntity> select(RiskFilter filter) throws SQLException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int whereCounter = 0;
		List<RiskEntity> result = new ArrayList<RiskEntity>();
		if (filter == null) {
			return result;
		}
		StringBuilder sb = new StringBuilder("SELECT * FROM RYZYKO WHERE 1=1");
		if (filter.getPolisyId() != null) {
			sb.append(" and polisa_id = ?");
			map.put("polisa_id", Integer.valueOf(++whereCounter));
		}
		if (filter.getInsurance() != null) {
			sb.append(" and ubezpieczenie = ?");
			map.put("ubezpieczenie", Integer.valueOf(++whereCounter));
		}
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement(sb.toString());
		if (filter.getPolisyId() != null) {
			ps.setInt(map.get("polisa_id").intValue(), filter.getPolisyId());
		}
		if (filter.getInsurance() != null) {
			ps.setString(map.get("ubezpieczenie").intValue(), filter.getInsurance().getCode());
		}
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(getRiskFromRs(rs));
		}
		return result;
	}

}

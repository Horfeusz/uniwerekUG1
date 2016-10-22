package pl.ug.edu.polisa.dao.insured;

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
import pl.ug.edu.polisa.domain.insured.InsuredEntity;
import pl.ug.edu.polisa.domain.insured.InsuredFiter;

public class InsuredDao extends BaseCRUDDao<InsuredEntity, InsuredFiter> {

	Log log = LogFactory.getLog(InsuredDao.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pl.ug.edu.polisa.dao.core.BaseCRUDDao#create(pl.ug.edu.polisa.domain.core
	 * .BaseEntity)
	 */
	@Override
	public void create(InsuredEntity insured) throws SQLException {
		validateObject(insured);
		String table = getTableNameAndValidate(insured);
		PreparedStatement ps = ConnectionFactory.instance().getConnection()
				.prepareStatement("INSERT INTO " + table + "(imie, nazwisko, polisa_id, wiek) values (?, ?, ?, ?)");
		ps.setString(1, insured.getFirstName());
		ps.setString(2, insured.getSecondName());
		ps.setInt(3, insured.getPolicyId());
		ps.setInt(4, insured.getAge());
		int row = ps.executeUpdate();
		log.info("Dodano " + row + " rekordów do tabeli " + table);
	}

	/**
	 * Odczytuje obiekt ubezpieczonego z ResultSeta
	 * 
	 * @param rs
	 * @return
	 */
	private InsuredEntity getInsuredFromRs(ResultSet rs) throws SQLException {
		InsuredEntity result = new InsuredEntity();
		result.setId(rs.getInt("id"));
		result.setFirstName(rs.getString("imie"));
		result.setSecondName(rs.getString("nazwisko"));
		result.setAge(rs.getInt("wiek"));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.ug.edu.polisa.dao.core.BaseCRUDDao#retrieve(java.lang.Integer)
	 */
	@Override
	public InsuredEntity retrieve(Integer id) throws SQLException {
		InsuredEntity result = null;
		String query = "SELECT * FROM UBEZPIECZONY WHERE ID = ?";
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result = getInsuredFromRs(rs);
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
	public void update(InsuredEntity insured) throws SQLException {
		validateObject(insured);
		String table = getTableNameAndValidate(insured);
		PreparedStatement ps = ConnectionFactory.instance().getConnection()
				.prepareStatement("UPDATE " + table + " set imie = ?, nazwisko = ?, wiek = ? WHERE id = ?");
		ps.setString(1, insured.getFirstName());
		ps.setString(2, insured.getSecondName());
		ps.setInt(3, insured.getAge());
		ps.setLong(4, insured.getId());
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
	public List<InsuredEntity> select(InsuredFiter filter) throws SQLException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int whereCounter = 0;
		List<InsuredEntity> result = new ArrayList<InsuredEntity>();
		if (filter == null) {
			return result;
		}
		StringBuilder sb = new StringBuilder("SELECT * FROM UBEZPIECZONY WHERE 1=1");
		if (filter.getPolisyId() != null) {
			sb.append(" and polisa_id = ?");
			map.put("polisa_id", Integer.valueOf(++whereCounter));
		}
		PreparedStatement ps = ConnectionFactory.instance().getConnection().prepareStatement(sb.toString());
		if (filter.getPolisyId() != null) {
			ps.setInt(map.get("polisa_id").intValue(), filter.getPolisyId());
		}
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.add(getInsuredFromRs(rs));
		}
		return result;
	}

}

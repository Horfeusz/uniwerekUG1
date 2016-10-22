package pl.ug.edu.polisa.dao.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import pl.ug.edu.polisa.domain.core.BaseEntity;
import pl.ug.edu.polisa.domain.core.Filter;

/**
 * Klasa bazowa dla CRUD
 * 
 * @author michalh
 *
 */
public abstract class BaseCRUDDao<T extends BaseEntity, Z extends Filter> {

	private Log log = LogFactory.getLog(BaseCRUDDao.class);

	/**
	 * Konstruktor
	 * 
	 * @throws Exception
	 */
	public BaseCRUDDao() {
	}

	/**
	 * Weryfikacja czy obiekt nie równa się null;
	 * 
	 * @param object
	 */
	protected void validateObject(T object) {
		Preconditions.checkNotNull(object, "Obiekt na którym ma być wykonana operacja == NULL");
	}

	/**
	 * Metoda waliduje czy obiekt nie jest pusty oraz czy jest encją. Jeżeli
	 * jest encją zwraca nazwe tabeli
	 * 
	 * @param object
	 *            obiekt encji
	 * @return nazwa tabeli odczytana z encji
	 * @throws SQLException
	 *             wyjątek w przypadku gdy obiekt nie jest encją
	 */
	protected String getTableNameAndValidate(T object) throws SQLException {
		String tableName = TableNameReader.read(object);
		if (Strings.isNullOrEmpty(tableName)) {
			throw new SQLException("Obiekt na którym ma być wykonana operacja nie jest Encją");
		}
		return tableName;
	}

	/**
	 * Metoda tworzy obiekt
	 * 
	 * @param object
	 */
	public abstract void create(T object) throws SQLException;

	/**
	 * Metoda zwraca nam obiekkt po identyfikatorze
	 * 
	 * @param id
	 * @return
	 */
	public abstract T retrieve(Integer id) throws SQLException;
	
	/**
	 * Aktualizacja obiektu
	 * 
	 * @param object
	 */
	public abstract void update(T object) throws SQLException;

	/**
	 * Metoda wyszukująca dane po filtrze
	 * 
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public abstract List<T> select(Z filter) throws SQLException;

	/**
	 * Usunięcie obiektu
	 * 
	 * @param object
	 */
	public void delete(T object) throws SQLException {
		validateObject(object);
		String table = getTableNameAndValidate(object);
		PreparedStatement ps = ConnectionFactory.instance().getConnection()
				.prepareStatement("DELETE FROM " + table + " WHERE id = ?");
		ps.setInt(1, object.getId());
		int row = ps.executeUpdate();
		log.info("Usunięto " + row + " rekordów z tabeli: " + table);
	}

}

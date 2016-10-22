package pl.ug.edu.polisa.dao.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Singleton zwracający połącznie do bazy danych
 */
public class ConnectionFactory {

	private Log log = LogFactory.getLog(ConnectionFactory.class);

	private static ConnectionFactory connectionFactory;

	private Connection conn;

	private ConnectionFactory() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:D:/Uniwerek/baza/mydatabase.db");
			log.info("Udało się nawiązać połaczenie za Bazą danych");
		} catch (Exception e) {
			log.error("Błąd przy próbie nawiązania połączenia z bazą danch");
			throw new SQLException(e);
		}
	}

	/**
	 * Utworzenie instancji konnektora
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ConnectionFactory instance() throws SQLException {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}

	/**
	 * Pobranie połączenia do bazy danych
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return conn;
	}

}

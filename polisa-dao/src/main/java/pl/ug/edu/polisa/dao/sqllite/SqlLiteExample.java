package pl.ug.edu.polisa.dao.sqllite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SqlLiteExample {

	Log log = LogFactory.getLog(getClass());
	
	/**
	 * Pobranie połączenia do bazy danych
	 */
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		return DriverManager.getConnection("jdbc:sqlite:D:/Uniwerek/baza/mydatabase2.db");
	}

	/**
	 * Utworzenie bazy danych
	 */
	public void createDatabase() throws ClassNotFoundException, SQLException {
		Statement statement = getConnection().createStatement();
		statement.executeUpdate("drop table if exists person");
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS person (id integer, name string)");
		statement.executeUpdate("insert into person values(1, 'Stefan')");
		statement.executeUpdate("insert into person values(2, 'Katarzyna')");
		
		ResultSet rs = statement.executeQuery("select * from person");
		while (rs.next()) {
			StringBuilder sb = new StringBuilder();
			sb.append("id = " + rs.getInt("id")).append(", ").append("name = " + rs.getString("name"));
			log.info(sb.toString());
		}
	}
	
	public static void main(String[] argc) {
		SqlLiteExample e = new SqlLiteExample();
		try {
			e.createDatabase();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}

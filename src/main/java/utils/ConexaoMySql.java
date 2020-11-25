package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySql {

	private String url = "jdbc:mysql://localhost:3306/prova2bim?useTimezone=true&serverTimezone=UTC";
	private String root = "usuario";
	private String key = "123123@senha";

	private static ConexaoMySql conexaoMySql;

	public static ConexaoMySql getInstance() {
		if (conexaoMySql == null)
			conexaoMySql = new ConexaoMySql();
		return conexaoMySql;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, root, key);
	}

	public static void main(String[] args) {
		try {
			System.out.println(getInstance().getConnection());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package br.edu.fapi.poo.jdbc.dao.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionProvider {

	public static String url = "jdbc:mysql://localhost:3306/fapi_poo";
	public static String usuario = "root";
	public static String senha = "";
	
	public static Connection abrirConexao() throws SQLException {
		return DriverManager.getConnection(url, usuario, senha);
	}
	
	
}

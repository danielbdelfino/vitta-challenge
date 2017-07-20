package br.com.vitta.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ConnectionFactory {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/webservice";
	private static final String USER = "root";
	private static final String SENHA = "admin";
	
	public Connection criarConexao() {
		Connection conexao = null;
		
		try {
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(URL, USER, SENHA);
		} catch (Exception e) {
			System.out.println("Erro ao criar conexão.");
			e.printStackTrace();
		}
		
		return conexao;
	}
	
	public void fecharConexao(Connection conexao, PreparedStatement pstmt, ResultSet rs) {
		
		try {
			if (conexao != null) {
				conexao.close();
			}
 			
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (rs != null) {
				rs.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao fehcar conexão.");
		}
		
	}
}

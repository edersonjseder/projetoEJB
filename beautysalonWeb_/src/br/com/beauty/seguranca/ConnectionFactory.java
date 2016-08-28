package br.com.beauty.seguranca;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ConnectionFactory {
	
	public static Connection openConnection() throws NamingException, SQLException {

		Context ic = new InitialContext();
		Connection con = null;
		DataSource dataSource = (DataSource) ic.lookup("java:MySqlDS");
		con = dataSource.getConnection();
		
		return con;
	}

}

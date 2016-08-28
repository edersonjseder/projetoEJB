package br.com.beauty.seguranca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.beauty.pojo.User;

public class UserDAO {

	private Connection connection;
	
	public UserDAO (){
		try {
			
			this.connection = ConnectionFactory.openConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean insert(User user) {
		PreparedStatement stmt;
		
		try {
			stmt = connection.prepareStatement("insert into user (login,password) values (?, ?)");
			stmt.setString(1, user.getLogin());
			stmt.setString(2, user.getSenha());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public User retrieve(String sql, String login){
		PreparedStatement stmt;
		User usuario = null;
		
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, login);
			ResultSet rs = stmt.executeQuery();
			
			
			usuario = new User();
			
			while(rs.next()){
				usuario.setSenha(rs.getString("senha"));
			}
			
			rs.close();
			stmt.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}


}

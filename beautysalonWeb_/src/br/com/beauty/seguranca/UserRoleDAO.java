package br.com.beauty.seguranca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.beauty.pojo.UserRole;

public class UserRoleDAO {

	private Connection connection;
	
	public UserRoleDAO (){
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
	
	public boolean insert(UserRole userRole) {
		PreparedStatement stmt;
		
		try {
			stmt = connection.prepareStatement("insert into userrole (login,role) values (?, ?)");
			stmt.setString(1, userRole.getLogin());
			stmt.setString(2, userRole.getRole());
			
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public UserRole retrieveRole(String login){
		PreparedStatement stmt;
		UserRole userRole = null;
		
		try {
			stmt = connection.prepareStatement("select * from userrole where login=?");
			stmt.setString(1, login);
			ResultSet rs = stmt.executeQuery();
			
			
			userRole = new UserRole();
			
			while(rs.next()){
				userRole.setId(rs.getInt("idUserRole"));
				userRole.setLogin(rs.getString("login"));
				userRole.setRole(rs.getString("role"));
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
		
		return userRole;
	}
	
	public UserRole retrieve(String sql, String login){
		PreparedStatement stmt;
		UserRole userRole = null;
		
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, login);
			ResultSet rs = stmt.executeQuery();
			
			
			userRole = new UserRole();
			
			while(rs.next()){
				userRole.setId(rs.getInt("idUserRole"));
				userRole.setLogin(rs.getString("login"));
				userRole.setRole(rs.getString("role"));
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
		
		return userRole;
	}


}

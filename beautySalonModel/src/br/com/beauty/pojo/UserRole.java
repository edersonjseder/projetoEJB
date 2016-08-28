package br.com.beauty.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userrole")
public class UserRole implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -938708316349622883L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idUserRole")
	private Integer id;
	
	private String login;
	private String role;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}

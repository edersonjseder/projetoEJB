package br.com.beauty.seguranca;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.Arrays;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import br.com.beauty.pojo.User;
import br.com.beauty.pojo.UserRole;

public class SegurancaCustom extends UsernamePasswordLoginModule {
	
	User user = null;
	UserRole userRole = null;
	
	String principalsQuery = "";
	String rolesQuery = "";
	
	@Override
	public void initialize(Subject arg0, CallbackHandler arg1,
			Map<String, ?> sharedState, Map<String, ?> options) {
		
		super.initialize(arg0, arg1, sharedState, options);
		
		principalsQuery = (String) options.get("principalsQuery");
		rolesQuery = (String) options.get("rolesQuery");
		
		System.out.println(principalsQuery);
		System.out.println(rolesQuery);
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		
		//Obter login do usuário
		String login = getUsername();
		
		user = new User();
		UserDAO userDAO = new UserDAO();
		
		user = userDAO.retrieve(principalsQuery, login);
		
		//Obter a senha correspondente ao login do Map usuarios
		String senha = user.getSenha();
		
		System.out.println("Passou:" + senha);
		
		return senha;
	}

	@Override
	protected boolean validatePassword(String inputPassword,
			String expectedPassword) {
		
		byte[] inputPasswordCripto = criptografarInformacao(inputPassword);
		System.out.println("expected criptografado: " + Arrays.toString(inputPasswordCripto));
		
		inputPassword = Arrays.toString(inputPasswordCripto);
		
		return expectedPassword.equals(inputPassword);
	}
	
	@Override
	protected Group[] getRoleSets() throws LoginException {
		Group[] retorno = new Group[1];
		
		//Obter login do usuário
		String login = getUsername();
		
		UserRoleDAO userRoleDAO = new UserRoleDAO();
		userRole = userRoleDAO.retrieve(rolesQuery, login);

		//Obter papel do usuário
		String papel = userRole.getRole();
		
		//criar credencial
		SimplePrincipal sp = new SimplePrincipal(papel);
		
		//criar um grupo de credencial
		SimpleGroup sg = new SimpleGroup("Roles");
		
		sg.addMember(sp);
		
		retorno[0] = sg;
		
		return retorno;
	}
	
	private byte[] criptografarInformacao(String informacao){
		MessageDigest md;
		byte[] criptografado = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			criptografado = md.digest(informacao.getBytes());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return criptografado;
	}

}

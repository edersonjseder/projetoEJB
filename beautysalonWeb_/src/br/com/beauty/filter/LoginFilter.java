package br.com.beauty.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.jboss.security.SecurityAssociation;

import br.com.beauty.pojo.UserRole;
import br.com.beauty.seguranca.UserRoleDAO;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {

		String userName = SecurityAssociation.getPrincipal().getName();
	    System.out.println("Yeeey! Get me here and find me in the database: " + userName);
	    
	    HttpServletRequest rq = (HttpServletRequest) servletRequest;
		
	    if(rq.getSession().getAttribute("usuarioLogado") == null){
	    	UserRoleDAO userRoleDAO = new UserRoleDAO();
	    	UserRole userRole = userRoleDAO.retrieveRole(userName);
	    	rq.getSession().setAttribute("usuarioLogado", userRole);
	    	
	    }else{
	    	UserRole userRole = (UserRole) rq.getSession().getAttribute("usuarioLogado");
	    	userRole.getRole();
	    }
	    
	    filterChain.doFilter(servletRequest, servletResponse);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}

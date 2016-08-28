package br.com.beauty.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.beauty.pojo.UserRole;
import br.com.beauty.utils.AcessoAdministracao;
import br.com.beauty.utils.AcessoAtendente;
import br.com.beauty.utils.AcessoCaixa;
import br.com.beauty.utils.AcessoGerente;
import br.com.beauty.utils.StringUtils;

@ManagedBean(name="home")
@SessionScoped
public class HomeController {
	
	private String nome = "Tutorial JSF e PrimeFaces";

	public String getNome() {
		return nome;
	}

	public String logout(){
		UserRole userRole = (UserRole)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = request.getRequestURI();
		
		if(userRole.getRole().equals(StringUtils.ROLE_ADMINISTRACAO)){
			session.invalidate();
			return AcessoAdministracao.ACESSO_HOME_ATENDIMENTO;
			
		}else if(userRole.getRole().equals(StringUtils.ROLE_ATENDENTE)){
			session.invalidate();
			return AcessoAtendente.ACESSO_HOME_ATENDIMENTO;
			
		}else if(userRole.getRole().equals(StringUtils.ROLE_CAIXA)){
			session.invalidate();
			return url + "?faces-redirect=true";
			
		}else if(userRole.getRole().equals(StringUtils.ROLE_GERENTE)){
			session.invalidate();
			return AcessoGerente.ACESSO_HOME_GERENTE;
		}
		
		return "";
	}
	
	public String acessoAdministracao(){
		return AcessoAdministracao.ACESSO_ATENDIMENTO;
	}
	
	public String acessoAtendente(){
		return AcessoAtendente.ACESSO_ATENDIMENTO;
	}
	
	public String acessoCaixa(){
		return AcessoCaixa.ACESSO_CAIXA;
	}
	
	public String acessoGerente(){
		try {
			HttpServletRequest rq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpServletResponse rp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			StringBuffer sb = rq.getRequestURL();
			String path = sb.toString();
			String[] one = path.split("/beautysalonWeb_");
			rp.sendRedirect(one[0] + AcessoGerente.ACESSO_GERENTE);
			
			FacesContext.getCurrentInstance().getExternalContext().redirect(one[0] + AcessoGerente.ACESSO_GERENTE + "index.jsf");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "gerente";
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}

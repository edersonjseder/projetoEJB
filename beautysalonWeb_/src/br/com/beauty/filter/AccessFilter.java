package br.com.beauty.filter;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.beauty.pojo.UserRole;
import br.com.beauty.utils.AcessoAdministracao;
import br.com.beauty.utils.AcessoAtendente;
import br.com.beauty.utils.AcessoCaixa;
import br.com.beauty.utils.PropertiesUtil;
import br.com.beauty.utils.StringUtils;

public class AccessFilter implements Filter {

	private String mensagem;
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;
		
		UserRole userRole = (UserRole)rq.getSession().getAttribute("usuarioLogado");
		
		String paginaAtual = rq.getRequestURI();
		
        if(null != userRole){
        	if(userRole.getRole().equals(StringUtils.ROLE_ADMINISTRACAO)){
        		// pass the request along the filter chain
        		if(!AcessoAdministracao.controlarAcesso(paginaAtual)){
        			rp.sendRedirect(AcessoAdministracao.ACESSO_ERRO_ATENDIMENTO);
        			System.out.println(paginaAtual);
        			
        		}else{
        			chain.doFilter(request, response);
        			
        		}
        		
        	}else if(userRole.getRole().equals(StringUtils.ROLE_ATENDENTE)){
        		// pass the request along the filter chain
        		if(!AcessoAtendente.controlarAcesso(paginaAtual)){
        			rp.sendRedirect(AcessoAtendente.ACESSO_ERRO_ATENDIMENTO);
        			System.out.println(paginaAtual);
        			
        		}else{
        			chain.doFilter(request, response);
        			
        		}
        		
        	}else if(userRole.getRole().equals(StringUtils.ROLE_CAIXA)){
        		// pass the request along the filter chain
        		if(!AcessoCaixa.controlarAcesso(paginaAtual)){
        			
        			FacesContext ctx = FacesContext.getCurrentInstance();
        			FacesMessage msg = null;

        			rp.sendRedirect(AcessoCaixa.ACESSO_ERRO_CAIXA);
        			
        			if(ctx != null){
            			setMensagem(PropertiesUtil.getProperty("mensagem_erro_acesso"));
            			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
            			ctx.addMessage(null, msg);
        			}
        			
        			System.out.println(paginaAtual);
        			
        		}else{
        			chain.doFilter(request, response);
        			
        		}
        		
        	}else if(userRole.getRole().equals(StringUtils.ROLE_GERENTE)){
        		chain.doFilter(request, response);
        	}
        	
        } else {
        	rp.sendRedirect("/beautysalonWeb_/pages/expired/sessionExpired.xhtml"); 
        }
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}

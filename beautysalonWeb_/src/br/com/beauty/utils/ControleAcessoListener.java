package br.com.beauty.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.annotation.WebListener;

import br.com.beauty.pojo.UserRole;

/**
 * Application Lifecycle Listener implementation class ControleAcessoListener
 *
 */
@WebListener
public class ControleAcessoListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5115317820280360822L;
	
	private String mensagem;
	
	public void afterPhase(PhaseEvent event) {  
        /* Manipula uma notificação que está procesando uma fase do ciclo de vida JSF quando este já completou. */   
        FacesContext ctx = event.getFacesContext();
        FacesMessage msg = null;
        if(event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {  
            String paginaAtual = ctx.getViewRoot().getViewId();  
            boolean paginaLogin = (paginaAtual.lastIndexOf("login.xhtml") > -1);  
            UserRole userRole = (UserRole)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
            
            if (userRole == null && !paginaLogin) {  
                NavigationHandler nh = ctx.getApplication().getNavigationHandler();  
                nh.handleNavigation(ctx, null, "loginPagina"); 
/*              HttpServletRequest request = (HttpServletRequest) ctx.getCurrentInstance().getExternalContext().getRequest();
                String url = request.getRequestURI();
                
                try {
                	ctx.getCurrentInstance().getExternalContext().redirect(url);
				} catch (IOException e) {
					e.printStackTrace();
				}*/
            } 
            
            if(null != userRole){
            	if(userRole.getRole().equals(StringUtils.ROLE_ADMINISTRACAO)){
            		// pass the request along the filter chain
            		if(!AcessoAdministracao.controlarAcesso(paginaAtual)){
//            			event.getFacesContext().getApplication().getNavigationHandler().handleNavigation(event.getFacesContext(), null, AcessoAdministracao.ACESSO_HOME_ATENDIMENTO);
            			
            			setMensagem(PropertiesUtil.getProperty("mensagem_erro_acesso"));
            			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
            			ctx.addMessage(null, msg);
            		}
            		
            	}else if(userRole.getRole().equals(StringUtils.ROLE_ATENDENTE)){
            		// pass the request along the filter chain
            		if(!AcessoAtendente.controlarAcesso(paginaAtual)){
            			event.getFacesContext().getApplication().getNavigationHandler().handleNavigation(event.getFacesContext(), null, AcessoAtendente.ACESSO_HOME_ATENDIMENTO);
            			
            			setMensagem(PropertiesUtil.getProperty("mensagem_erro_acesso"));
            			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
            			ctx.addMessage(null, msg);
            		}
            		
            	}else if(userRole.getRole().equals(StringUtils.ROLE_CAIXA)){
            		// pass the request along the filter chain
            		if(!AcessoCaixa.controlarAcesso(paginaAtual)){
//            			event.getFacesContext().getApplication().getNavigationHandler().handleNavigation(event.getFacesContext(), null, AcessoCaixa.ACESSO_HOME_CAIXA);
                        NavigationHandler nh = ctx.getApplication().getNavigationHandler();  
                        nh.handleNavigation(ctx, null, "caixa");
            			
            			setMensagem(PropertiesUtil.getProperty("mensagem_erro_acesso"));
            			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
            			ctx.addMessage(null, msg);
            		}
            		
            	}else if(userRole.getRole().equals(StringUtils.ROLE_GERENTE)){
            		// pass the request along the filter chain
            		if(!AcessoGerente.controlarAcesso(paginaAtual)){
            			event.getFacesContext().getApplication().getNavigationHandler().handleNavigation(event.getFacesContext(), null, AcessoGerente.ACESSO_HOME_GERENTE);
            			
            			setMensagem(PropertiesUtil.getProperty("mensagem_erro_acesso"));
            			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
            			ctx.addMessage(null, msg);
            		}
            		
            	}
            	
            }
            
            if (ctx.getViewRoot() != null) {  
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("paginaDestino", ctx.getViewRoot().getViewId());    
            }  
        }  
        else{
        	System.out.println(ctx.getViewRoot().getViewId());
        }
              
    }  
  
    public void beforePhase(PhaseEvent event) {  
        /* Manipula uma notificação que está procesando uma fase do ciclo de vida JSF quando este está prestes a iniciar. */   
            FacesContext ctx = event.getFacesContext();  
            if (ctx.getViewRoot() != null)  
                System.out.println(ctx.getViewRoot().getViewId());  
    }  
  
    public PhaseId getPhaseId() {  
        /* Retorna o Tipo de Fase no JSF que o objeto será notificado. Pode ser qualquer uma das 6 fases, ou todas as fases. */   
        return PhaseId.ANY_PHASE;  
    }

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}	
}

package br.com.beauty.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.event.SelectEvent;

import br.com.beauty.datamodel.HistoricoFinanceiroDataModel;
import br.com.beauty.datamodel.HistoricoFinanceiroModel;
import br.com.beauty.interfaces.HistoricoFinanceiro;
import br.com.beauty.pojo.HistoricoFinanceiroPOJO;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="historicoController")
@SessionScoped
public class HistoricoController {

	@EJB
	private HistoricoFinanceiro historicoFinanceiro;
	
	private HistoricoFinanceiroPOJO historicoFinanceiroPOJO;
	private HistoricoFinanceiroModel historicoFinanceiroSelected;
	private String mensagem;
	private List<HistoricoFinanceiroModel> listaHistoricoFinanceiro;
	private HistoricoFinanceiroDataModel listaHistoricoFinanceiroDataModel;
	
	public HistoricoController(){
		inicializarComponentes();
		prepararInclusao();
	}
	
	private void inicializarComponentes(){
		listaHistoricoFinanceiro = new ArrayList<HistoricoFinanceiroModel>();
	}
	
	private void prepararInclusao(){
		setHistoricoFinanceiroPOJO(new HistoricoFinanceiroPOJO());
		setHistoricoFinanceiroSelected(new HistoricoFinanceiroModel());
	}
	
	/**
	 * Método de cadastro de Produtos
	 * @return String
	 */
	public String cadastrarHistoricoFinanceiro(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		
		try {
			historicoFinanceiro.insert(getHistoricoFinanceiroPOJO());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_historico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarHistoricoFinanceiro();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_historico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	/**
	 * Método de atualização de Produtos
	 * @return String
	 */
	public String alterarHistoricoFinanceiro(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			
			getHistoricoFinanceiroPOJO().setId(getHistoricoFinanceiroSelected().getId());
			getHistoricoFinanceiroPOJO().setHistorico(getHistoricoFinanceiroSelected().getHistorico());

			historicoFinanceiro.update(getHistoricoFinanceiroPOJO());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_historico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarHistoricoFinanceiro();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_historico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirHistoricoFinanceiro(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		getHistoricoFinanceiroPOJO().setId(getHistoricoFinanceiroSelected().getId());
		getHistoricoFinanceiroPOJO().setHistorico(getHistoricoFinanceiroSelected().getHistorico());
		
		try {
			historicoFinanceiro.deleteById(getHistoricoFinanceiroPOJO().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_historico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarHistoricoFinanceiro();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_historico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}
	}
	
    public void onRowSelect(SelectEvent event) {  
        FacesMessage msg = new FacesMessage("Histórico Selecionado ", ((HistoricoFinanceiroPOJO) event.getObject()).getHistorico());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public void onRowDblselect(SelectEvent event) {  
        FacesContext context = FacesContext.getCurrentInstance();  
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();  
        Flash flash = context.getExternalContext().getFlash();  
        flash.put("historicoFinanceiroSelected", (HistoricoFinanceiroModel) event.getObject());  
          
        handler.performNavigation("historicoFinanceiroDetail");  
    }
	
	/**
	 * Método que vai recuperar informações de produtos e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarHistoricoFinanceiro(){
		
		List<HistoricoFinanceiroPOJO> lista = new ArrayList<HistoricoFinanceiroPOJO>();
		setListaHistoricoFinanceiroDataModel(null);
		setListaHistoricoFinanceiro(null);
		setListaHistoricoFinanceiro(new ArrayList<HistoricoFinanceiroModel>());
		
		try {
			lista = historicoFinanceiro.listar();
			
			if(lista.isEmpty()){
				setListaHistoricoFinanceiroDataModel(new HistoricoFinanceiroDataModel());
				throw new Exception();
			}
			
			for (HistoricoFinanceiroPOJO historicoFinanceiroPOJO : lista) {
				HistoricoFinanceiroModel historicoFinanceiro = new HistoricoFinanceiroModel();
				
				historicoFinanceiro.setId(historicoFinanceiroPOJO.getId());
				historicoFinanceiro.setHistorico(historicoFinanceiroPOJO.getHistorico());
				
				getListaHistoricoFinanceiro().add(historicoFinanceiro);
			}
			
			setListaHistoricoFinanceiroDataModel(new HistoricoFinanceiroDataModel(getListaHistoricoFinanceiro()));
			
			
		} catch (Exception e) {
			setListaHistoricoFinanceiroDataModel(new HistoricoFinanceiroDataModel());
			return "/pages/protected/historico/listaHistoricos.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/historico/listaHistoricos.xhtml?faces-redirect=true";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public HistoricoFinanceiroPOJO getHistoricoFinanceiroPOJO() {
		return historicoFinanceiroPOJO;
	}

	public void setHistoricoFinanceiroPOJO(
			HistoricoFinanceiroPOJO historicoFinanceiroPOJO) {
		this.historicoFinanceiroPOJO = historicoFinanceiroPOJO;
	}

	public HistoricoFinanceiroModel getHistoricoFinanceiroSelected() {
		return historicoFinanceiroSelected;
	}

	public void setHistoricoFinanceiroSelected(
			HistoricoFinanceiroModel historicoFinanceiroSelected) {
		this.historicoFinanceiroSelected = historicoFinanceiroSelected;
	}

	public List<HistoricoFinanceiroModel> getListaHistoricoFinanceiro() {
		return listaHistoricoFinanceiro;
	}

	public void setListaHistoricoFinanceiro(
			List<HistoricoFinanceiroModel> listaHistoricoFinanceiro) {
		this.listaHistoricoFinanceiro = listaHistoricoFinanceiro;
	}

	public HistoricoFinanceiroDataModel getListaHistoricoFinanceiroDataModel() {
		return listaHistoricoFinanceiroDataModel;
	}

	public void setListaHistoricoFinanceiroDataModel(
			HistoricoFinanceiroDataModel listaHistoricoFinanceiroDataModel) {
		this.listaHistoricoFinanceiroDataModel = listaHistoricoFinanceiroDataModel;
	}

}

package br.com.beauty.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.event.SelectEvent;

import br.com.beauty.datamodel.ServicoDataModel;
import br.com.beauty.datamodel.ServicoModel;
import br.com.beauty.interfaces.Servicos;
import br.com.beauty.pojo.ServicosPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="servicoController")
@SessionScoped
public class ServicoController {

	@EJB
	private Servicos servicos;
	
	private ServicosPOJO servico;
	private ServicoModel servicoSelected;
	private String mensagem;
	private String preco;
	private List<ServicoModel> listaServicos;
	private ServicoDataModel listaServicoDataModel;	
	private Calendar dataAtual;
	
	public ServicoController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void inicializarComponentes(){
		dataAtual = new Calendar();
		listaServicos = new ArrayList<ServicoModel>();
	}
	
	private void prepararCondicoesIniciais(){
		Date data = new Date(System.currentTimeMillis());    
		getDataAtual().setValue(data);
	}
	
	private void prepararInclusao(){
		setServico(new ServicosPOJO());
		setServicoSelected(new ServicoModel());
	}
	
	/**
	 * Método de cadastro de Serviços
	 * @return String
	 */
	public String cadastrarServico(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		
		try {
			num = nf.parse(getPreco());
			getServico().setDataServico((Date) getDataAtual().getValue());
			getServico().setPreco(new BigDecimal(num.doubleValue()));
			
			servicos.insert(getServico());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_cliente"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarServicos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_cliente"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	/**
	 * Método de atualização de Serviços
	 * @return String
	 */
	public String alterarServico(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		try {
			num = nf.parse(getServicoSelected().getPreco().replace("R$ ", "0"));
			
			getServico().setId(getServicoSelected().getId());
			getServico().setDataServico(new SimpleDateFormat("dd/MM/yyyy").parse(getServicoSelected().getDataServico()));
			getServico().setNome(getServicoSelected().getNome());
			getServico().setPreco(new BigDecimal(num.doubleValue()));
			getServico().setDescricao(getServicoSelected().getDescricao());
			
			servicos.update(getServico());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_servico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarServicos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_servico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirServico(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		getServico().setId(getServicoSelected().getId());
		
		try {
			servicos.deleteById(getServico().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_servico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_servico"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}
	}
	
    public void onRowDblselect(SelectEvent event) {  
        FacesContext context = FacesContext.getCurrentInstance();  
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();  
        Flash flash = context.getExternalContext().getFlash();  
        flash.put("servicoSelected", (ServicoModel) event.getObject());  
          
        handler.performNavigation("servicoDetail");  
    }
	
	/**
	 * Método que vai recuperar informações de servicos e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarServicos(){
		
		List<ServicosPOJO> lista = new ArrayList<ServicosPOJO>();
		StringUtils utils = new StringUtils(); 
		setListaServicoDataModel(null);
		setListaServicos(null);
		setListaServicos(new ArrayList<ServicoModel>());
		
		try {
			lista = servicos.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (ServicosPOJO servicos : lista) {
				ServicoModel servico = new ServicoModel();
				
				servico.setId(servicos.getId());
				servico.setDataServico(utils.dateToString(servicos.getDataServico()));
				servico.setNome(servicos.getNome());
				servico.setPreco(utils.bigDecimalToString(servicos.getPreco()));
				servico.setDescricao(servicos.getDescricao());
				
				getListaServicos().add(servico);
			}
			
			setListaServicoDataModel(new ServicoDataModel(getListaServicos()));
			
		} catch (Exception e) {
			setListaServicoDataModel(new ServicoDataModel());
			return "/pages/protected/servico/listaServicos.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/servico/listaServicos.xhtml?faces-redirect=true";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public ServicosPOJO getServico() {
		return servico;
	}

	public void setServico(ServicosPOJO servico) {
		this.servico = servico;
	}

	public List<ServicoModel> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(List<ServicoModel> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public ServicoModel getServicoSelected() {
		return servicoSelected;
	}

	public void setServicoSelected(ServicoModel servicoSelected) {
		this.servicoSelected = servicoSelected;
	}

	public ServicoDataModel getListaServicoDataModel() {
		return listaServicoDataModel;
	}

	public void setListaServicoDataModel(ServicoDataModel listaServicoDataModel) {
		this.listaServicoDataModel = listaServicoDataModel;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public Calendar getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Calendar dataAtual) {
		this.dataAtual = dataAtual;
	}

}

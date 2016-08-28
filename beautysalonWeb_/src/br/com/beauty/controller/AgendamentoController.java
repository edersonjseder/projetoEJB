package br.com.beauty.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;

import br.com.beauty.beans.ClientesBean;
import br.com.beauty.beans.FuncionariosBean;
import br.com.beauty.beans.ServicosBean;
import br.com.beauty.datamodel.AgendamentoModel;
import br.com.beauty.datamodel.AgendamentosDataModel;
import br.com.beauty.interfaces.Agendamentos;
import br.com.beauty.interfaces.Clientes;
import br.com.beauty.interfaces.Funcionarios;
import br.com.beauty.interfaces.Servicos;
import br.com.beauty.pojo.AgendamentosPOJO;
import br.com.beauty.pojo.ClientesPOJO;
import br.com.beauty.pojo.FuncionarioPOJO;
import br.com.beauty.pojo.ServicosPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="agendamentoController")
@SessionScoped
public class AgendamentoController {

	@EJB
	private Agendamentos agendamentos;
	
	private Servicos servicos;
	private Funcionarios funcionarios;
	private Clientes clientes;
	
	private AgendamentosPOJO agendamento;
	private AgendamentoModel agendamentoSelected;
	private ServicosPOJO servicoPOJO;
	private FuncionarioPOJO funcionariosPOJO;
	private ClientesPOJO clientesPOJO;
	private String mensagem;
	private String preco;
	private String data;
	private List<ServicosPOJO> listaServico;
	private List<FuncionarioPOJO> listaFuncionarios;
	private List<ClientesPOJO> listaClientes;
	private List<AgendamentoModel> listaAgendamento;
	private AgendamentosDataModel listaAgendamentosDataModel;
	
	private Calendar dataAtual;
	private InputText horaAtual;
	private InputText precoServicoText;
	
	public AgendamentoController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void prepararCondicoesIniciais(){
		Date data = new Date(System.currentTimeMillis());
		
		try {
			getHoraAtual().setValue(getHora());
			getDataAtual().setValue(data);
			
		} catch (Exception e) {
		}
	}
	
	public String acessarCadastro(){
		
		try {
			setListaServico(servicos.listar());
			setListaFuncionarios(funcionarios.listar());
			setListaClientes(clientes.listar());
			
		} catch (Exception e) {}
		
		return "/pages/protected/agendamento/agendamentoServico.xhtml?faces-redirect=true";
	}
	
	private void inicializarComponentes(){
		dataAtual = new Calendar();
		horaAtual = new InputText();
		precoServicoText = new InputText();
		servicoPOJO = new ServicosPOJO();
		funcionariosPOJO = new FuncionarioPOJO();
		clientesPOJO = new ClientesPOJO();
		listaAgendamento = new ArrayList<AgendamentoModel>();
		listaServico = new ArrayList<ServicosPOJO>();
		listaFuncionarios = new ArrayList<FuncionarioPOJO>();
		listaClientes = new ArrayList<ClientesPOJO>();
		servicos = new ServicosBean();
		funcionarios = new FuncionariosBean();
		clientes = new ClientesBean();
	}
	
	private void prepararInclusao(){
		setAgendamento(new AgendamentosPOJO());
		setAgendamentoSelected(new AgendamentoModel());
	}
	
	public void atribuirValorServicoListener(){
		StringUtils utils = new StringUtils();
		setServicoPOJO(servicos.retrieve(getAgendamento().getServicos().getId()));
		getPrecoServicoText().setValue(utils.bigDecimalToStringSimple(getServicoPOJO().getPreco()));
	}
	
	/**
	 * Método de cadastro de agendamento
	 * @return String
	 */
	public String cadastrarAgendamento(){
		FacesContext context = FacesContext.getCurrentInstance();
		StringUtils utils = new StringUtils();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number precoServico = null;
		
		try {
			precoServico = nf.parse(((String)getPrecoServicoText().getValue()));
			data = utils.dateToString((Date) getDataAtual().getValue());
			
			setFuncionariosPOJO(funcionarios.retrieve(getAgendamento().getFuncionarios().getId()));
			setClientesPOJO(clientes.retrieve(getAgendamento().getClientes().getId()));
			
			getAgendamento().setServicos(getServicoPOJO());
			getAgendamento().setFuncionarios(getFuncionariosPOJO());
			getAgendamento().setClientes(getClientesPOJO());
			
			getAgendamento().setPrecoServico(new BigDecimal(precoServico.doubleValue()));
			getAgendamento().setDataAgendamento(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			getAgendamento().setHoraAgendamento((String) getHoraAtual().getValue());
			
			agendamentos.insert(getAgendamento());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_agendamento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarAgendamentos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_agendamento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			return "";
		}
		
	}
	
	/**
	 * Método de atualização de agendamentos
	 * @return String
	 */
	public String alterarAgendamento(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			getAgendamento().setId(getAgendamentoSelected().getId());
			getAgendamento().setDataAgendamento(new SimpleDateFormat("dd/MM/yyyy").parse(getAgendamentoSelected().getDataAgendamento()));
			getAgendamento().setHoraAgendamento(getAgendamentoSelected().getHoraAgendamento());
			getAgendamento().setStatusAgendamento(getAgendamentoSelected().getStatusAgendamento());
			getAgendamento().setDescricao(getAgendamentoSelected().getDescricao());

			agendamentos.update(getAgendamento());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_agendamento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarAgendamentos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_agendamento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirAgendamento(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			agendamentos.deleteById(getAgendamentoSelected().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_agendamento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarAgendamentos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_agendamento"));
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
        flash.put("agendamentoSelected", (AgendamentoModel) event.getObject());  
          
        handler.performNavigation("agendamentoDetail");  
    }
    
	/**
	 * Método que vai recuperar informações de agendamentos e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarAgendamentos(){
		List<AgendamentosPOJO> lista = new ArrayList<AgendamentosPOJO>();
		StringUtils utils = new StringUtils();
		setListaAgendamentosDataModel(null);
		setListaAgendamento(null);
		setListaAgendamento(new ArrayList<AgendamentoModel>());
		
		try {
			lista = agendamentos.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (AgendamentosPOJO agendamentos : lista) {
				AgendamentoModel agendamento = new AgendamentoModel();
				
				agendamento.setId(agendamentos.getId());
				agendamento.setDataAgendamento(utils.dateToString(agendamentos.getDataAgendamento()));
				agendamento.setHoraAgendamento(agendamentos.getHoraAgendamento());
				agendamento.setDescricao(agendamentos.getDescricao());
				agendamento.setStatusAgendamento(agendamentos.getStatusAgendamento());
				agendamento.setClientes(agendamentos.getClientes());
				agendamento.setFuncionarios(agendamentos.getFuncionarios());
				agendamento.setServicos(agendamentos.getServicos());
				
				getListaAgendamento().add(agendamento);
			}
			
			setListaAgendamentosDataModel(new AgendamentosDataModel(getListaAgendamento()));
			
		} catch (Exception e) {
			setListaAgendamentosDataModel(new AgendamentosDataModel());
			return "/pages/protected/caixa/listaAgendamentos.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/caixa/listaAgendamentos.xhtml?faces-redirect=true";
	}
	
	public String getHora() {  
	      
	    // cria um StringBuilder  
	    StringBuilder sb = new StringBuilder();  
	  
	    // cria um GregorianCalendar que vai conter a hora atual  
	    GregorianCalendar d = new GregorianCalendar();  
	      
	    // anexa do StringBuilder os dados da hora  
	    sb.append( d.get( GregorianCalendar.HOUR_OF_DAY ) );  
	    sb.append( ":" );
	    sb.append( d.get(GregorianCalendar.MINUTE) == 0 ? "00" : String.valueOf(GregorianCalendar.MINUTE) );   
	      
	    // retorna a String do StringBuilder  
	    return sb.toString();  
	      
	}  

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public AgendamentosPOJO getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(AgendamentosPOJO agendamento) {
		this.agendamento = agendamento;
	}

	public AgendamentoModel getAgendamentoSelected() {
		return agendamentoSelected;
	}

	public void setAgendamentoSelected(AgendamentoModel agendamentoSelected) {
		this.agendamentoSelected = agendamentoSelected;
	}

	public List<AgendamentoModel> getListaAgendamento() {
		return listaAgendamento;
	}

	public void setListaAgendamento(List<AgendamentoModel> listaAgendamento) {
		this.listaAgendamento = listaAgendamento;
	}

	public Calendar getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Calendar dataAtual) {
		this.dataAtual = dataAtual;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public AgendamentosDataModel getListaAgendamentosDataModel() {
		return listaAgendamentosDataModel;
	}

	public void setListaAgendamentosDataModel(
			AgendamentosDataModel listaAgendamentosDataModel) {
		this.listaAgendamentosDataModel = listaAgendamentosDataModel;
	}

	public List<ServicosPOJO> getListaServico() {
		return listaServico;
	}

	public void setListaServico(List<ServicosPOJO> listaServico) {
		this.listaServico = listaServico;
	}

	public List<FuncionarioPOJO> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<FuncionarioPOJO> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public InputText getHoraAtual() {
		return horaAtual;
	}

	public void setHoraAtual(InputText horaAtual) {
		this.horaAtual = horaAtual;
	}

	public ServicosPOJO getServicoPOJO() {
		return servicoPOJO;
	}

	public void setServicoPOJO(ServicosPOJO servicoPOJO) {
		this.servicoPOJO = servicoPOJO;
	}

	public FuncionarioPOJO getFuncionariosPOJO() {
		return funcionariosPOJO;
	}

	public void setFuncionariosPOJO(FuncionarioPOJO funcionariosPOJO) {
		this.funcionariosPOJO = funcionariosPOJO;
	}

	public ClientesPOJO getClientesPOJO() {
		return clientesPOJO;
	}

	public void setClientesPOJO(ClientesPOJO clientesPOJO) {
		this.clientesPOJO = clientesPOJO;
	}

	public List<ClientesPOJO> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<ClientesPOJO> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public InputText getPrecoServicoText() {
		return precoServicoText;
	}

	public void setPrecoServicoText(InputText precoServicoText) {
		this.precoServicoText = precoServicoText;
	}

}

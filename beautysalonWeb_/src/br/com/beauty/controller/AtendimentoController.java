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
import br.com.beauty.datamodel.AtendimentoModel;
import br.com.beauty.datamodel.AtendimentosDataModel;
import br.com.beauty.interfaces.Atendimentos;
import br.com.beauty.interfaces.Clientes;
import br.com.beauty.interfaces.Funcionarios;
import br.com.beauty.interfaces.Servicos;
import br.com.beauty.pojo.AtendimentosPOJO;
import br.com.beauty.pojo.ClientesPOJO;
import br.com.beauty.pojo.FuncionarioPOJO;
import br.com.beauty.pojo.ServicosPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="atendimentoController")
@SessionScoped
public class AtendimentoController {

	@EJB
	private Atendimentos atendimentos;
	
	private Servicos servicos;
	private Funcionarios funcionarios;
	private Clientes clientes;
	
	private AtendimentosPOJO atendimento;
	private AtendimentoModel atendimentoSelected;
	private ServicosPOJO servicoPOJO;
	private FuncionarioPOJO funcionariosPOJO;
	private ClientesPOJO clientesPOJO;
	private String mensagem;
	private String preco;
	private String data;
	private List<ServicosPOJO> listaServico;
	private List<FuncionarioPOJO> listaFuncionarios;
	private List<ClientesPOJO> listaClientes;
	private List<AtendimentoModel> listaAtendimento;
	private AtendimentosDataModel listaAtendimentosDataModel;
	
	private Calendar dataAtual;
	private InputText horaAtual;
	private InputText precoServicoText;
	
	public AtendimentoController(){
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
		
		return "/pages/protected/atendimento/cadastroAtendimento.xhtml?faces-redirect=true";
	}
	
	private void inicializarComponentes(){
		dataAtual = new Calendar();
		horaAtual = new InputText();
		precoServicoText = new InputText();
		servicoPOJO = new ServicosPOJO();
		funcionariosPOJO = new FuncionarioPOJO();
		clientesPOJO = new ClientesPOJO();
		listaAtendimento = new ArrayList<AtendimentoModel>();
		listaServico = new ArrayList<ServicosPOJO>();
		listaFuncionarios = new ArrayList<FuncionarioPOJO>();
		listaClientes = new ArrayList<ClientesPOJO>();
		servicos = new ServicosBean();
		funcionarios = new FuncionariosBean();
		clientes = new ClientesBean();
	}
	
	private void prepararInclusao(){
		setAtendimento(new AtendimentosPOJO());
		setAtendimentoSelected(new AtendimentoModel());
	}
	
	public void atribuirValorServicoListener(){
		StringUtils utils = new StringUtils();
		setServicoPOJO(servicos.retrieve(getAtendimento().getServicos().getId()));
		getPrecoServicoText().setValue(utils.bigDecimalToStringSimple(getServicoPOJO().getPreco()));
	}
	
	/**
	 * Método de cadastro de atendimento
	 * @return String
	 */
	public String cadastrarAtendimento(){
		FacesContext context = FacesContext.getCurrentInstance();
		StringUtils utils = new StringUtils();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number precoServico = null;
		
		try {
			precoServico = nf.parse(((String)getPrecoServicoText().getValue()));
			data = utils.dateToString((Date) getDataAtual().getValue());
			
			setFuncionariosPOJO(funcionarios.retrieve(getAtendimento().getFuncionarios().getId()));
			setClientesPOJO(clientes.retrieve(getAtendimento().getClientes().getId()));
			
			getAtendimento().setServicos(getServicoPOJO());
			getAtendimento().setFuncionarios(getFuncionariosPOJO());
			getAtendimento().setClientes(getClientesPOJO());
			
			getAtendimento().setPrecoServico(new BigDecimal(precoServico.doubleValue()));
			getAtendimento().setDataAtendimento(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			getAtendimento().setHoraAtendimento((String) getHoraAtual().getValue());
			
			atendimentos.insert(getAtendimento());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_atendimento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarAtendimentos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_atendimento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			return "";
		}
		
	}
	
	/**
	 * Método de atualização de Atendimentos
	 * @return String
	 */
	public String alterarAtendimento(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
			
		
		try {
			getAtendimento().setId(getAtendimentoSelected().getId());
			getAtendimento().setDataAtendimento(new SimpleDateFormat("dd/MM/yyyy").parse(getAtendimentoSelected().getDataAtendimento()));
			getAtendimento().setHoraAtendimento(getAtendimentoSelected().getHoraAtendimento());

			atendimentos.update(getAtendimento());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_atendimento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarAtendimentos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_atendimento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirAtendimento(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			
			atendimentos.deleteById(getAtendimentoSelected().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_atendimento"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarAtendimentos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_atendimento"));
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
        flash.put("atendimentoSelected", (AtendimentoModel) event.getObject());  
          
        handler.performNavigation("atendimentoDetail");  
    }
    
	/**
	 * Método que vai recuperar informações de atendimentos e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarAtendimentos(){
		List<AtendimentosPOJO> lista = new ArrayList<AtendimentosPOJO>();
		StringUtils utils = new StringUtils();
		setListaAtendimentosDataModel(null);
		setListaAtendimento(null);
		setListaAtendimento(new ArrayList<AtendimentoModel>());
		
		try {
			lista = atendimentos.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (AtendimentosPOJO atendimentos : lista) {
				AtendimentoModel atendimento = new AtendimentoModel();
				
				atendimento.setId(atendimentos.getId());
				atendimento.setDataAtendimento(utils.dateToString(atendimentos.getDataAtendimento()));
				atendimento.setHoraAtendimento(atendimentos.getHoraAtendimento());
				atendimento.setDescricao(atendimentos.getDescricao());
				atendimento.setClientes(atendimentos.getClientes());
				atendimento.setFuncionarios(atendimentos.getFuncionarios());
				atendimento.setStatusAtendimento(atendimentos.getStatusAtendimento());
				atendimento.setServicos(atendimentos.getServicos());
				
				getListaAtendimento().add(atendimento);
			}
			
			setListaAtendimentosDataModel(new AtendimentosDataModel(getListaAtendimento()));
			
		} catch (Exception e) {
			setListaAtendimentosDataModel(new AtendimentosDataModel());
			return "/pages/protected/atendimento/listaAtendimentos.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/atendimento/listaAtendimentos.xhtml?faces-redirect=true";
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

	public AtendimentosPOJO getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(AtendimentosPOJO atendimento) {
		this.atendimento = atendimento;
	}

	public AtendimentoModel getAtendimentoSelected() {
		return atendimentoSelected;
	}

	public void setAtendimentoSelected(AtendimentoModel atendimentoSelected) {
		this.atendimentoSelected = atendimentoSelected;
	}

	public List<AtendimentoModel> getListaAtendimento() {
		return listaAtendimento;
	}

	public void setListaAtendimento(List<AtendimentoModel> listaAtendimento) {
		this.listaAtendimento = listaAtendimento;
	}

	public AtendimentosDataModel getListaAtendimentosDataModel() {
		return listaAtendimentosDataModel;
	}

	public void setListaAtendimentosDataModel(
			AtendimentosDataModel listaAtendimentosDataModel) {
		this.listaAtendimentosDataModel = listaAtendimentosDataModel;
	}

	public InputText getHoraAtual() {
		return horaAtual;
	}

	public void setHoraAtual(InputText horaAtual) {
		this.horaAtual = horaAtual;
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

	public List<ClientesPOJO> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<ClientesPOJO> listaClientes) {
		this.listaClientes = listaClientes;
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

	public InputText getPrecoServicoText() {
		return precoServicoText;
	}

	public void setPrecoServicoText(InputText precoServicoText) {
		this.precoServicoText = precoServicoText;
	}

}

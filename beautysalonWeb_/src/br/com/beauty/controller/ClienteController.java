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

import org.apache.log4j.Logger;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;

import br.com.beauty.beans.CidadeBean;
import br.com.beauty.beans.EstadoBean;
import br.com.beauty.datamodel.ClienteDataModel;
import br.com.beauty.datamodel.ClienteModel;
import br.com.beauty.interfaces.Cidade;
import br.com.beauty.interfaces.Clientes;
import br.com.beauty.interfaces.Estado;
import br.com.beauty.pojo.CidadePOJO;
import br.com.beauty.pojo.ClientesPOJO;
import br.com.beauty.pojo.EnderecoPOJO;
import br.com.beauty.pojo.EstadoPOJO;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="clienteController")
@SessionScoped
public class ClienteController {

	private static final Logger LOG = Logger.getLogger(ClienteController.class);
	
	@EJB
	private Clientes clientes;
	private Estado estados;
	private Cidade cidades;
	
	private ClientesPOJO cliente;
	private ClienteModel clienteSelected;
	private EnderecoPOJO endereco;
	private String mensagem;
	private EstadoPOJO estadoPojo;
	private CidadePOJO cidadePojo;
	private Integer idEstado;
	private Integer idCidade;
	private List<ClienteModel> listaClientes;
	private ClienteDataModel listaClienteDataModel;
	private List<EstadoPOJO> listaEstados;
	private List<CidadePOJO> listaCidades;
	private SelectOneMenu comboCidade;
	
	public ClienteController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void inicializarComponentes(){
		estados = new EstadoBean();
		cidades = new CidadeBean();
		listaClientes = new ArrayList<ClienteModel>();
		listaEstados = new ArrayList<EstadoPOJO>();
		listaCidades = new ArrayList<CidadePOJO>();
		comboCidade = new SelectOneMenu();
	}
	
	private void prepararCondicoesIniciais(){
		setListaEstados(estados.listar());
	}
	
	private void prepararInclusao(){
		setCliente(new ClientesPOJO());
		setClienteSelected(new ClienteModel());
		setEndereco(new EnderecoPOJO());
	}
	
	public void pesquisarCidades(){
		setListaCidades(cidades.listarById(getIdEstado()));
		setEstadoPojo(estados.retrieve(getIdEstado()));
		getComboCidade().setDisabled(false);
	}	
	
	public void pesquisarCidadesAlteracao(){
		setListaCidades(cidades.listarById(getClienteSelected().getEndereco().getEstado().getId()));
		setEstadoPojo(estados.retrieve(getClienteSelected().getEndereco().getEstado().getId()));
		getComboCidade().setDisabled(false);
	}
	
	/**
	 * Método de cadastro de Clientes
	 * @return String
	 */
	public String cadastrarClientes(){
		LOG.info("Estou apenas colocando uma informação qualquer ao arquivo de log");
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			getCliente().getEndereco().getCidade().setEstado(getEstadoPojo());
			setCidadePojo(cidades.retrieve(getIdCidade()));
			getCliente().getEndereco().setCidade(getCidadePojo());
			getCliente().getEndereco().setEstado(getEstadoPojo());
			
			clientes.insert(getCliente());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_cliente"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			LOG.info("VEJA A MENSAGEM AQUI >>>>>>>>>>>>> " + getMensagem());
			
			return listarClientes();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_cliente"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			LOG.error(e);
			return "";
		}		
	}
	
	/**
	 * Método de atualização de Clientes
	 * @return String
	 */
	public String alterarCliente(){
		LOG.info("Estou apenas colocando uma informação qualquer ao arquivo de log");
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			getCliente().setId(getClienteSelected().getId());
			getCliente().setNome(getClienteSelected().getNome());
			getCliente().setCpf(getClienteSelected().getCpf());
			getCliente().setSexo(getClienteSelected().getSexo().charAt(0));
			getCliente().setTelefone(getClienteSelected().getTelefone());
			getCliente().setEmail(getClienteSelected().getEmail());

			getCliente().getEndereco().getCidade().setEstado(getEstadoPojo());
			setCidadePojo(cidades.retrieve(getClienteSelected().getEndereco().getCidade().getId()));
			getClienteSelected().getEndereco().setCidade(getCidadePojo());
			getClienteSelected().getEndereco().setEstado(getEstadoPojo());
			
			getCliente().setEndereco(getClienteSelected().getEndereco());
			
			clientes.update(getCliente());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_cliente"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			LOG.info("VEJA A MENSAGEM AQUI >>>>>>>>>>>>> " + getMensagem());
			
			return listarClientes();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_cliente"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			LOG.error(e);
			
			return "";
		}		
	}
	
	public String excluirCliente(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			clientes.deleteById(getClienteSelected().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_cliente"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarClientes();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_cliente"));
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
        flash.put("clienteSelected", (ClienteModel) event.getObject());  
          
        handler.performNavigation("clienteDetail");  
    }
	
	/**
	 * Método que vai recuperar informações de clientes e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarClientes(){
		
		List<ClientesPOJO> lista = new ArrayList<ClientesPOJO>();
		setListaClienteDataModel(null);
		setListaClientes(null);
		setListaClientes(new ArrayList<ClienteModel>());
		
		try {
			lista = clientes.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (ClientesPOJO clientesPOJO : lista) {
				ClienteModel cliente = new ClienteModel();
				
				cliente.setId(clientesPOJO.getId());
				cliente.setNome(clientesPOJO.getNome());
				cliente.setCpf(clientesPOJO.getCpf());
				cliente.setSexo(clientesPOJO.getSexo().toString());
				cliente.setTelefone(clientesPOJO.getTelefone());
				cliente.setEmail(clientesPOJO.getEmail());
				cliente.setEndereco(clientesPOJO.getEndereco());
				
				getListaClientes().add(cliente);
			}
			
			setListaClienteDataModel(new ClienteDataModel(getListaClientes()));
			
		} catch (Exception e) {
			setListaClienteDataModel(new ClienteDataModel());
			return "/pages/protected/cliente/listaClientes.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/cliente/listaClientes.xhtml?faces-redirect=true";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public ClientesPOJO getCliente() {
		return cliente;
	}

	public void setCliente(ClientesPOJO cliente) {
		this.cliente = cliente;
	}

	public EnderecoPOJO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoPOJO endereco) {
		this.endereco = endereco;
	}

	public List<ClienteModel> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<ClienteModel> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ClienteDataModel getListaClienteDataModel() {
		return listaClienteDataModel;
	}

	public void setListaClienteDataModel(ClienteDataModel listaClienteDataModel) {
		this.listaClienteDataModel = listaClienteDataModel;
	}

	public ClienteModel getClienteSelected() {
		return clienteSelected;
	}

	public void setClienteSelected(ClienteModel clienteSelected) {
		this.clienteSelected = clienteSelected;
	}

	public List<EstadoPOJO> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<EstadoPOJO> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public List<CidadePOJO> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<CidadePOJO> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public SelectOneMenu getComboCidade() {
		return comboCidade;
	}

	public void setComboCidade(SelectOneMenu comboCidade) {
		this.comboCidade = comboCidade;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}

	public EstadoPOJO getEstadoPojo() {
		return estadoPojo;
	}

	public void setEstadoPojo(EstadoPOJO estadoPojo) {
		this.estadoPojo = estadoPojo;
	}

	public CidadePOJO getCidadePojo() {
		return cidadePojo;
	}

	public void setCidadePojo(CidadePOJO cidadePojo) {
		this.cidadePojo = cidadePojo;
	}

}

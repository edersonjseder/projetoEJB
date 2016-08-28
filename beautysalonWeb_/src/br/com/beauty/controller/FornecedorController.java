package br.com.beauty.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.SelectEvent;

import br.com.beauty.beans.CidadeBean;
import br.com.beauty.beans.EstadoBean;
import br.com.beauty.datamodel.FornecedorDataModel;
import br.com.beauty.datamodel.FornecedorModel;
import br.com.beauty.interfaces.Cidade;
import br.com.beauty.interfaces.Estado;
import br.com.beauty.interfaces.Fornecedor;
import br.com.beauty.pojo.CidadePOJO;
import br.com.beauty.pojo.EstadoPOJO;
import br.com.beauty.pojo.FornecedorPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="fornecedorController")
@SessionScoped
public class FornecedorController {

	@EJB
	private Fornecedor fornecedores;
	private Estado estados;
	private Cidade cidades;
	
	private FornecedorPOJO fornecedor;
	private FornecedorModel fornecedorSelected;
	private String mensagem;
	private String data;
	private Integer idEstado;
	private Integer idCidade;
	private EstadoPOJO estadoPojo;
	private CidadePOJO cidadePojo;
	private List<FornecedorModel> listaFornecedores;
	private FornecedorDataModel listaFornecedoresDataModel;
	private List<EstadoPOJO> listaEstados;
	private List<CidadePOJO> listaCidades;
	
	private Calendar dataAtual;
	private SelectOneMenu comboCidade;
	
	public FornecedorController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void prepararCondicoesIniciais(){
		Date data = new Date(System.currentTimeMillis());
		getDataAtual().setValue(data);
		setListaEstados(estados.listar());
	}
	
	private void inicializarComponentes(){
		estados = new EstadoBean();
		cidades = new CidadeBean();
		dataAtual = new Calendar();
		listaFornecedores = new ArrayList<FornecedorModel>();
		listaEstados = new ArrayList<EstadoPOJO>();
		listaCidades = new ArrayList<CidadePOJO>();
		comboCidade = new SelectOneMenu();
	}
	
	private void prepararInclusao(){
		setFornecedor(new FornecedorPOJO());
		setFornecedorSelected(new FornecedorModel());
	}
	
	public void pesquisarCidadesAlteracao(){
		setListaCidades(cidades.listarById(getFornecedorSelected().getEndereco().getEstado().getId()));
		setEstadoPojo(estados.retrieve(getFornecedorSelected().getEndereco().getEstado().getId()));
		getComboCidade().setDisabled(false);
	}	
	
	public void pesquisarCidades(){
		setListaCidades(cidades.listarById(getIdEstado()));
		setEstadoPojo(estados.retrieve(getIdEstado()));
		getComboCidade().setDisabled(false);
	}	
	
	/**
	 * Método de cadastro de Fornecedor
	 * @return String
	 */
	public String cadastrarFornecedor(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		StringUtils utils = new StringUtils();
		
		try {
			data = utils.dateToString((Date) getDataAtual().getValue());
			getFornecedor().setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			getFornecedor().getEndereco().getCidade().setEstado(getEstadoPojo());
			setCidadePojo(cidades.retrieve(getIdCidade()));
			getFornecedor().getEndereco().setCidade(getCidadePojo());
			getFornecedor().getEndereco().setEstado(getEstadoPojo());
			
			fornecedores.insert(getFornecedor());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_fornecedor"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarFornecedores();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_fornecedor"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			e.printStackTrace();
			context.addMessage(null, msg);
			return "";
		}
	}
	
	/**
	 * Método de atualização de Fornecedor
	 * @return String
	 */
	public String alterarFornecedor(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			getFornecedor().setId(getFornecedorSelected().getId());
			getFornecedor().setNomeFornecedor(getFornecedorSelected().getNomeFornecedor());
			getFornecedor().setCpfOuCnpj(getFornecedorSelected().getCpfOuCnpj());
			getFornecedor().setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse(getFornecedorSelected().getDataCadastro()));
			getFornecedor().setEmail(getFornecedorSelected().getEmail());
			getFornecedor().setTelefone(getFornecedorSelected().getTelefone());
			getFornecedor().setFax(getFornecedorSelected().getFax());
			
			getFornecedor().getEndereco().getCidade().setEstado(getEstadoPojo());
			setCidadePojo(cidades.retrieve(getFornecedor().getEndereco().getCidade().getId()));
			getFornecedor().getEndereco().setCidade(getCidadePojo());
			getFornecedor().getEndereco().setEstado(getEstadoPojo());
			
			fornecedores.update(getFornecedor());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_fornecedor"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_fornecedor"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirFornecedor(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			fornecedores.deleteById(getFornecedorSelected().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_fornecedor"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_fornecedor"));
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
        flash.put("fornecedorSelected", (FornecedorModel) event.getObject());  
          
        handler.performNavigation("fornecedorDetail");  
    }	
	
	/**
	 * Método que vai recuperar informações de Fornecedores e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarFornecedores(){
		
		List<FornecedorPOJO> lista = new ArrayList<FornecedorPOJO>();
		StringUtils utils = new StringUtils(); 
		setListaFornecedoresDataModel(null);
		setListaFornecedores(null);
		setListaFornecedores(new ArrayList<FornecedorModel>());
		
		try {
			lista = fornecedores.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (FornecedorPOJO fornecedores : lista) {
				FornecedorModel fornecedor = new FornecedorModel();
				
				fornecedor.setId(fornecedores.getId());
				fornecedor.setNomeFornecedor(fornecedores.getNomeFornecedor());
				fornecedor.setCpfOuCnpj(fornecedores.getCpfOuCnpj());
				fornecedor.setDataCadastro(utils.dateToString(fornecedores.getDataCadastro()));
				fornecedor.setEmail(fornecedores.getEmail());
				fornecedor.setTelefone(fornecedores.getTelefone());
				fornecedor.setFax(fornecedores.getFax());
				fornecedor.setEndereco(fornecedores.getEndereco());

				getListaFornecedores().add(fornecedor);
			}
			
			setListaFornecedoresDataModel(new FornecedorDataModel(getListaFornecedores()));
			
		} catch (Exception e) {
			setListaFornecedoresDataModel(new FornecedorDataModel());
			return "/pages/protected/fornecedor/listaFornecedor.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/fornecedor/listaFornecedor.xhtml?faces-redirect=true";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public FornecedorModel getFornecedorSelected() {
		return fornecedorSelected;
	}

	public void setFornecedorSelected(FornecedorModel fornecedorSelected) {
		this.fornecedorSelected = fornecedorSelected;
	}

	public List<FornecedorModel> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<FornecedorModel> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}

	public FornecedorDataModel getListaFornecedoresDataModel() {
		return listaFornecedoresDataModel;
	}

	public void setListaFornecedoresDataModel(
			FornecedorDataModel listaFornecedoresDataModel) {
		this.listaFornecedoresDataModel = listaFornecedoresDataModel;
	}

	public FornecedorPOJO getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorPOJO fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Calendar getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Calendar dataAtual) {
		this.dataAtual = dataAtual;
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

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public SelectOneMenu getComboCidade() {
		return comboCidade;
	}

	public void setComboCidade(SelectOneMenu comboCidade) {
		this.comboCidade = comboCidade;
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

	public Integer getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}
}

package br.com.beauty.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.log4j.Logger;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

import br.com.beauty.beans.CidadeBean;
import br.com.beauty.beans.EstadoBean;
import br.com.beauty.datamodel.FuncionarioDataModel;
import br.com.beauty.datamodel.FuncionarioModel;
import br.com.beauty.interfaces.Cidade;
import br.com.beauty.interfaces.Estado;
import br.com.beauty.interfaces.Funcionarios;
import br.com.beauty.pojo.CidadePOJO;
import br.com.beauty.pojo.EstadoPOJO;
import br.com.beauty.pojo.FuncionarioPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="funcionarioController")
@SessionScoped
public class FuncionarioController {
	
	private static final Logger LOG = Logger.getLogger(FuncionarioController.class);

	@EJB
	private Funcionarios funcionarios;
	private Estado estados;
	private Cidade cidades;
	
	private FuncionarioPOJO funcionario;
	private FuncionarioModel funcionarioSelected;
	private String mensagem;
	private String remuneracao;
	private boolean ativo;
	private boolean skip;
	private String senha;
	private EstadoPOJO estadoPojo;
	private CidadePOJO cidadePojo;
	private Integer idEstado;
	private Integer idCidade;
	private String confirmaSenha;
	private List<FuncionarioModel> listaFuncionarios;
	private List<EstadoPOJO> listaEstados;
	private List<CidadePOJO> listaCidades;
	private FuncionarioDataModel listaFuncionarioDataModel;
	
	private Calendar dataDemissaoComponent;
	private InputText dataDemissaoText;
	private SelectOneMenu comboCidade;
	
	public FuncionarioController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void prepararCondicoesIniciais(){
		if(ativo){
			getDataDemissaoComponent().setDisabled(ativo);
		}
		setListaEstados(estados.listar());
	}
	
	private void inicializarComponentes(){
		estados = new EstadoBean();
		cidades = new CidadeBean();
		dataDemissaoComponent = new Calendar();
		dataDemissaoText = new InputText();
		listaFuncionarios = new ArrayList<FuncionarioModel>();
		listaEstados = new ArrayList<EstadoPOJO>();
		listaCidades = new ArrayList<CidadePOJO>();
		comboCidade = new SelectOneMenu();
	}
	
	private void prepararInclusao(){
		setAtivo(true);
		setFuncionario(new FuncionarioPOJO());
		setFuncionarioSelected(new FuncionarioModel());
	}
	
	public boolean validarSenha(){
		if(getSenha().equals(getConfirmaSenha())){
			getFuncionario().getUser().setSenha(getSenha());
			return true;
		}else{
			return false;
		}
	}
	
	public void habilitarDesabilitarCampoDataDemissao(){
		if(ativo){
			getDataDemissaoComponent().setDisabled(ativo);
		}else{
			getDataDemissaoComponent().setDisabled(ativo);
		}
	}
	
	public void habilitarDesabilitarCampoDataDemissaoDialog(){
		if(getFuncionarioSelected().isAtivo()){
			getDataDemissaoText().setDisabled(getFuncionarioSelected().isAtivo());
		}else{
			getDataDemissaoText().setDisabled(getFuncionarioSelected().isAtivo());
		}
	}
	
	public void pesquisarCidades(){
		setListaCidades(cidades.listarById(getIdEstado()));
		setEstadoPojo(estados.retrieve(getIdEstado()));
		getComboCidade().setDisabled(false);
	}
	
	public void pesquisarCidadesAlteracao(){
		setListaCidades(cidades.listarById(getFuncionarioSelected().getEndereco().getEstado().getId()));
		setEstadoPojo(estados.retrieve(getFuncionarioSelected().getEndereco().getEstado().getId()));
		getComboCidade().setDisabled(false);
	}
	
    public String onFlowProcess(FlowEvent event) {            
        if(skip) {  
            skip = false;   //reset in case user goes back  
            return "confirm";  
        }  
        else {  
            return event.getNewStep();  
        }  
    }
	
	/**
	 * Método de cadastro de Funcionários
	 * @return String
	 */
	public String cadastrarFuncionario(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		if(validarSenha()){
			
			try {
				num = nf.parse(getRemuneracao());
				
				getFuncionario().setRemuneracao(new BigDecimal(num.doubleValue()));
				getFuncionario().getEndereco().getCidade().setEstado(getEstadoPojo());
				setCidadePojo(cidades.retrieve(getIdCidade()));
				getFuncionario().getEndereco().setCidade(getCidadePojo());
				getFuncionario().getEndereco().setEstado(getEstadoPojo());
				getFuncionario().setStatusFuncionario(isAtivo());
				getFuncionario().getUserRole().setLogin(getFuncionario().getUser().getLogin());
				
				funcionarios.insert(getFuncionario());
				
				setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_funcionario"));
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
				context.addMessage(null, msg);
				return listarFuncionarios();
				
			} catch (Exception e) {
				setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_funcionario"));
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
				e.printStackTrace();
				context.addMessage(null, msg);
				return "";
			}
			
		}else{
			setMensagem(PropertiesUtil.getProperty("mensagem_senha_erro"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			return "";
		}
		
	}
	
	/**
	 * Método de atualização de Funcionarios
	 * @return String
	 */
	public String alterarFuncionario(){
		LOG.info("Estou apenas colocando uma informação qualquer ao arquivo de log");
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		if(validarSenha()){
			
			try {
				num = nf.parse(getFuncionarioSelected().getRemuneracao().replace("R$ ", "0"));
				
				getFuncionario().setId(getFuncionarioSelected().getId());
				getFuncionario().setNome(getFuncionarioSelected().getNome());
				getFuncionario().setCpf(getFuncionarioSelected().getCpf());
				getFuncionario().setSexo(getFuncionarioSelected().getSexo());
				getFuncionario().setCargo(getFuncionarioSelected().getCargo());
				getFuncionario().setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(getFuncionarioSelected().getDataNascimento()));
				getFuncionario().setDataAdmissao(new SimpleDateFormat("dd/MM/yyyy").parse(getFuncionarioSelected().getDataAdmissao()));
				if(getFuncionarioSelected().getDataDemissao() != null){
					getFuncionario().setDataDemissao(new SimpleDateFormat("dd/MM/yyyy").parse(getFuncionarioSelected().getDataDemissao()));
				}
				getFuncionario().setTelefone(getFuncionarioSelected().getTelefone());
				getFuncionario().setEmail(getFuncionarioSelected().getEmail());
				getFuncionario().setStatusFuncionario(getFuncionarioSelected().isAtivo());
				getFuncionario().setRemuneracao(new BigDecimal(num.doubleValue()));
				
				
				getFuncionario().getEndereco().setId(getFuncionarioSelected().getEndereco().getId());
				setCidadePojo(cidades.retrieve(getFuncionarioSelected().getEndereco().getCidade().getId()));
				getFuncionario().getEndereco().setCidade(getCidadePojo());
				getFuncionario().getEndereco().getCidade().setEstado(getFuncionarioSelected().getEndereco().getEstado());
				getFuncionario().getEndereco().setEstado(getFuncionarioSelected().getEndereco().getEstado());
				getFuncionario().getEndereco().setBairro(getFuncionarioSelected().getEndereco().getBairro());
				getFuncionario().getEndereco().setEndereco(getFuncionarioSelected().getEndereco().getEndereco());
				getFuncionario().getEndereco().setCep(getFuncionarioSelected().getEndereco().getCep());
				
				getFuncionario().getUser().setId(getFuncionarioSelected().getUser().getId());
				getFuncionario().getUser().setLogin(getFuncionarioSelected().getUser().getLogin());
				
				getFuncionario().getUserRole().setId(getFuncionarioSelected().getUserRole().getId());
				getFuncionario().getUserRole().setLogin(getFuncionarioSelected().getUserRole().getLogin());
				getFuncionario().getUserRole().setRole(getFuncionarioSelected().getUserRole().getRole());
				
				funcionarios.update(getFuncionario());
				
				setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_funcionario"));
				msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
				context.addMessage(null, msg);
				LOG.info("VEJA A MENSAGEM AQUI >>>>>>>>>>>>> " + getMensagem());
				return listarFuncionarios();
				
			} catch (Exception e) {
				setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_funcionario"));
				msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
				context.addMessage(null, msg);
				e.printStackTrace();
				LOG.error(e);
				return "";
			}		
			
		}else{
			setMensagem(PropertiesUtil.getProperty("mensagem_senha_erro"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			LOG.error("ANALISAR A MENSAGEM DE ERRO DE SENHA AQUI >>>>>>>>>>>>>> " + getMensagem());
			return "";
		}
	}
	
	public String excluirFuncionario(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			funcionarios.deleteById(getFuncionarioSelected().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_funcionario"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarFuncionarios();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_funcionario"));
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
        flash.put("funcionarioSelected", (FuncionarioModel) event.getObject());  
          
        handler.performNavigation("funcionarioDetail");  
    }	
	
	/**
	 * Método que vai recuperar informações de funcionários e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarFuncionarios(){
		
		List<FuncionarioPOJO> lista = new ArrayList<FuncionarioPOJO>();
		StringUtils utils = new StringUtils();
		setListaFuncionarioDataModel(null);
		setListaFuncionarios(null);
		setListaFuncionarios(new ArrayList<FuncionarioModel>());
		
		try {
			lista = funcionarios.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (FuncionarioPOJO funcionarios : lista) {
				FuncionarioModel funcionario = new FuncionarioModel();
				
				funcionario.setId(funcionarios.getId());
				funcionario.setNome(funcionarios.getNome());
				funcionario.setCpf(funcionarios.getCpf());
				funcionario.setSexo(funcionarios.getSexo());
				funcionario.setDataNascimento(utils.dateToString(funcionarios.getDataNascimento()));
				funcionario.setDataAdmissao(utils.dateToString(funcionarios.getDataAdmissao()));
				funcionario.setDataDemissao(utils.dateToString(funcionarios.getDataDemissao()));
				funcionario.setTelefone(funcionarios.getTelefone());
				funcionario.setEmail(funcionarios.getEmail());
				funcionario.setCargo(funcionarios.getCargo());
				funcionario.setRemuneracao(utils.bigDecimalToString(funcionarios.getRemuneracao()));
				funcionario.setStatusFuncionario(funcionarios.isStatusFuncionario() == true ? "Ativo" : "Inativo");
				funcionario.setAtivo(funcionarios.isStatusFuncionario());
				funcionario.setUser(funcionarios.getUser());
				funcionario.setUserRole(funcionarios.getUserRole());
				funcionario.setEndereco(funcionarios.getEndereco());
				
				getListaFuncionarios().add(funcionario);
			}
			
			setListaFuncionarioDataModel(new FuncionarioDataModel(getListaFuncionarios()));
			
		} catch (Exception e) {
			setListaFuncionarioDataModel(new FuncionarioDataModel());
			return "/pages/protected/funcionario/listaFuncionarios.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/funcionario/listaFuncionarios.xhtml?faces-redirect=true";
	}

	public FuncionarioPOJO getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioPOJO funcionario) {
		this.funcionario = funcionario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getRemuneracao() {
		return remuneracao;
	}

	public void setRemuneracao(String remuneracao) {
		this.remuneracao = remuneracao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Calendar getDataDemissaoComponent() {
		return dataDemissaoComponent;
	}

	public void setDataDemissaoComponent(Calendar dataDemissaoComponent) {
		this.dataDemissaoComponent = dataDemissaoComponent;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public List<FuncionarioModel> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<FuncionarioModel> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public FuncionarioDataModel getListaFuncionarioDataModel() {
		return listaFuncionarioDataModel;
	}

	public void setListaFuncionarioDataModel(
			FuncionarioDataModel listaFuncionarioDataModel) {
		this.listaFuncionarioDataModel = listaFuncionarioDataModel;
	}

	public FuncionarioModel getFuncionarioSelected() {
		return funcionarioSelected;
	}

	public void setFuncionarioSelected(FuncionarioModel funcionarioSelected) {
		this.funcionarioSelected = funcionarioSelected;
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

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
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

	public InputText getDataDemissaoText() {
		return dataDemissaoText;
	}

	public void setDataDemissaoText(InputText dataDemissaoText) {
		this.dataDemissaoText = dataDemissaoText;
	}

}

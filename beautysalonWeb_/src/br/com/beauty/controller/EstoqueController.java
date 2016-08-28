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
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;

import br.com.beauty.beans.ProdutosBean;
import br.com.beauty.datamodel.EstoqueDataModel;
import br.com.beauty.datamodel.EstoqueModel;
import br.com.beauty.interfaces.Estoque;
import br.com.beauty.interfaces.Produtos;
import br.com.beauty.pojo.EstoquePOJO;
import br.com.beauty.pojo.ProdutosPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="estoqueController")
@SessionScoped
public class EstoqueController {

	@EJB
	private Estoque estoques;
	
	private Produtos produtos;
	
	private EstoquePOJO estoque;
	private ProdutosPOJO produto;
	private EstoqueModel estoqueSelected;
	private String mensagem;
	private String tipoEntrada = "Entrada";
	private String tipoSaida = "Saída";
	private String data;
	private String valorUnitario;
	private List<EstoqueModel> listaEstoques;
	private List<ProdutosPOJO> listaProdutos;
	private EstoqueDataModel listaEstoqueDataModel;
	
	private InputText textEntrada;
	private InputText textSaida;
	private InputText textValorUnitario;
	private Calendar dataAtual;
	
	public EstoqueController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void prepararCondicoesIniciais(){
		Date data = new Date(System.currentTimeMillis());    
		getDataAtual().setValue(data);
		getTextEntrada().setValue(getTipoEntrada());
		getTextSaida().setValue(getTipoSaida());
	}
	
	private void inicializarComponentes(){
		textEntrada = new InputText();
		textSaida = new InputText();
		textValorUnitario = new InputText();
		dataAtual = new Calendar();
		produtos = new ProdutosBean();
		listaEstoques = new ArrayList<EstoqueModel>();
		listaProdutos = new ArrayList<ProdutosPOJO>();
	}
	
	private void prepararInclusao(){
		setProduto(new ProdutosPOJO());
		setEstoque(new EstoquePOJO());
		getEstoque().setProdutos(getProduto());
		setEstoqueSelected(new EstoqueModel());
	}
	
	public String acessarCadastro(){
		
		try {
			
			setListaProdutos(produtos.listar());
			
		} catch (Exception e) {}
		
		return "/pages/protected/estoque/entrada/cadastroEntradaEstoque.xhtml?faces-redirect=true";
	}
	
	public void atribuirValorUnitarioListener(){
		StringUtils utils = new StringUtils();
		setProduto(produtos.retrieve(getEstoque().getProdutos().getId()));
		getTextValorUnitario().setValue(utils.bigDecimalToStringSimple(getProduto().getPreco()));
	}
	
	public void atribuirValorUnitarioListenerDialog(){
		StringUtils utils = new StringUtils();
		setProduto(produtos.retrieve(getEstoqueSelected().getProdutos().getId()));
		getTextValorUnitario().setValue(utils.bigDecimalToStringSimple(getProduto().getPreco()));
	}
	
	/**
	 * Método de cadastro de Saída de Estoque
	 * @return String
	 */
	public String cadastrarEstoqueSaida(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		StringUtils utils = new StringUtils();
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorUnitario = null;
		
		try {
			valorUnitario = nf.parse(((String)getTextValorUnitario().getValue()));
			getEstoque().setProdutos(getProduto());
			
			data = utils.dateToString((Date) getDataAtual().getValue());
			getEstoque().setTipoProcesso((String) getTextSaida().getValue());
			getEstoque().setDataProcesso(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			getEstoque().setValorUnitario(new BigDecimal(valorUnitario.doubleValue()));
			
			estoques.insert(getEstoque());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_saida_estoque"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarEstoqueSaida();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_saida_estoque"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			return "";
		}
		
	}
	
	/**
	 * Método de cadastro de Entrada de Estoque
	 * @return String
	 */
	public String cadastrarEstoqueEntrada(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		StringUtils utils = new StringUtils();
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorUnitario = null;
		
		try {
			valorUnitario = nf.parse(((String)getTextValorUnitario().getValue()));
			getEstoque().setProdutos(getProduto());
			
			data = utils.dateToString((Date) getDataAtual().getValue());
			getEstoque().setTipoProcesso((String) getTextEntrada().getValue());
			getEstoque().setDataProcesso(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			getEstoque().setValorUnitario(new BigDecimal(valorUnitario.doubleValue()));
			
			estoques.insert(getEstoque());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_entrada_estoque"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarEstoqueEntrada();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_entrada_estoque"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			return "";
		}
		
	}
	
	/**
	 * Método de atualização de Estoque
	 * @return String
	 */
	public String alterarEstoque(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorUnitario = null;
		
		try {
			valorUnitario = nf.parse(((String)getTextValorUnitario().getValue()).replace("R$", "0"));
			getEstoque().setProdutos(getProduto());
			
			getEstoque().setId(getEstoqueSelected().getId());
			getEstoque().setDataProcesso(new SimpleDateFormat("dd/MM/yyyy").parse(getEstoqueSelected().getDataProcesso()));
			getEstoque().setValorUnitario(new BigDecimal(valorUnitario.doubleValue()));
			
			estoques.update(getEstoque());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_entrada_estoque"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_entrada_estoque"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	
	public String excluirEstoque(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			estoques.deleteById(getEstoqueSelected().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_entrada_estoque"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_entrada_estoque"));
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
        flash.put("estoqueSelected", (EstoqueModel) event.getObject());  
          
        handler.performNavigation("estoqueDetail");  
    }
    
    /**
     * Método que vai recuperar informações de Entrada de Estoque e adicionar 
     * em uma lista para ser mostrada numa tabela na tela para o usuário
     */
    public String listarEstoqueEntrada(){
    	List<EstoquePOJO> lista = new ArrayList<EstoquePOJO>();
    	StringUtils utils = new StringUtils();
		setListaEstoqueDataModel(null);
		setListaEstoques(null);
		setListaEstoques(new ArrayList<EstoqueModel>());
    	
    	try {
    		lista = estoques.listarByTipo(tipoEntrada);
    		
    		if(lista.isEmpty()){
    			throw new Exception();
    		}
    		
    		for (EstoquePOJO estoques : lista) {
    			EstoqueModel estoque = new EstoqueModel();
    			
    			estoque.setId(estoques.getId());
    			estoque.setDataProcesso(utils.dateToString(estoques.getDataProcesso()));
    			estoque.setValorUnitario(utils.bigDecimalToString(estoques.getValorUnitario()));
    			estoque.setValorTotalEstoque(utils.bigDecimalToString(estoques.getValorTotalEstoque()));
    			estoque.setQuantidade(estoques.getQuantidade().toString());
    			estoque.setQtdeAtualEstoque(estoques.getQtdeAtualEstoque().toString());
    			estoque.setDescricao(estoques.getDescricao());
    			estoque.setTipoProcesso(estoques.getTipoProcesso());
    			estoque.setProdutos(estoques.getProdutos());
    			
    			getListaEstoques().add(estoque);
    		}
    		
    		setListaEstoqueDataModel(new EstoqueDataModel(getListaEstoques()));
    		
    	} catch (Exception e) {
    		setListaEstoqueDataModel(new EstoqueDataModel());
    		return "/pages/protected/estoque/entrada/listaCadastroEntradaEstoque.xhtml?faces-redirect=true";
    		
    	}
    	return "/pages/protected/estoque/entrada/listaCadastroEntradaEstoque.xhtml?faces-redirect=true";
    }
    
	/**
	 * Método que vai recuperar informações de Saída de Estoque e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarEstoqueSaida(){
		List<EstoquePOJO> lista = new ArrayList<EstoquePOJO>();
		StringUtils utils = new StringUtils();
		setListaEstoqueDataModel(null);
		setListaEstoques(null);
		setListaEstoques(new ArrayList<EstoqueModel>());
		
		try {
			lista = estoques.listarByTipo(tipoSaida);
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (EstoquePOJO estoques : lista) {
				EstoqueModel estoque = new EstoqueModel();
				
    			estoque.setId(estoques.getId());
    			estoque.setDataProcesso(utils.dateToString(estoques.getDataProcesso()));
    			estoque.setValorUnitario(utils.bigDecimalToString(estoques.getValorUnitario()));
    			estoque.setValorTotalEstoque(utils.bigDecimalToString(estoques.getValorTotalEstoque()));
    			estoque.setQuantidade(estoques.getQuantidade().toString());
    			estoque.setQtdeAtualEstoque(estoques.getQtdeAtualEstoque().toString());
    			estoque.setDescricao(estoques.getDescricao());
    			estoque.setTipoProcesso(estoques.getTipoProcesso());
				
				getListaEstoques().add(estoque);
			}
			
			setListaEstoqueDataModel(new EstoqueDataModel(getListaEstoques()));
			
		} catch (Exception e) {
			setListaEstoqueDataModel(new EstoqueDataModel());
			return "/pages/protected/estoque/saida/listaCadastroSaidaEstoque.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/estoque/saida/listaCadastroSaidaEstoque.xhtml?faces-redirect=true";
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

	public String getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public EstoquePOJO getEstoque() {
		return estoque;
	}

	public void setEstoque(EstoquePOJO estoque) {
		this.estoque = estoque;
	}

	public EstoqueModel getEstoqueSelected() {
		return estoqueSelected;
	}

	public void setEstoqueSelected(EstoqueModel estoqueSelected) {
		this.estoqueSelected = estoqueSelected;
	}

	public List<EstoqueModel> getListaEstoques() {
		return listaEstoques;
	}

	public void setListaEstoques(List<EstoqueModel> listaEstoques) {
		this.listaEstoques = listaEstoques;
	}

	public EstoqueDataModel getListaEstoqueDataModel() {
		return listaEstoqueDataModel;
	}

	public void setListaEstoqueDataModel(
			EstoqueDataModel listaEstoqueDataModel) {
		this.listaEstoqueDataModel = listaEstoqueDataModel;
	}

	public String getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(String tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}

	public String getTipoSaida() {
		return tipoSaida;
	}

	public void setTipoSaida(String tipoSaida) {
		this.tipoSaida = tipoSaida;
	}

	public InputText getTextEntrada() {
		return textEntrada;
	}

	public void setTextEntrada(InputText textEntrada) {
		this.textEntrada = textEntrada;
	}

	public InputText getTextSaida() {
		return textSaida;
	}

	public void setTextSaida(InputText textSaida) {
		this.textSaida = textSaida;
	}

	public List<ProdutosPOJO> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<ProdutosPOJO> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public InputText getTextValorUnitario() {
		return textValorUnitario;
	}

	public void setTextValorUnitario(InputText textValorUnitario) {
		this.textValorUnitario = textValorUnitario;
	}

	public ProdutosPOJO getProduto() {
		return produto;
	}

	public void setProduto(ProdutosPOJO produto) {
		this.produto = produto;
	}
}

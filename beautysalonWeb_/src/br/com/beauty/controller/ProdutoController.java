package br.com.beauty.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
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

import org.primefaces.event.SelectEvent;

import br.com.beauty.datamodel.ProdutoDataModel;
import br.com.beauty.datamodel.ProdutoModel;
import br.com.beauty.interfaces.Produtos;
import br.com.beauty.pojo.ProdutosPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="produtoController")
@SessionScoped
public class ProdutoController {

	@EJB
	private Produtos produtos;
	
	private ProdutosPOJO produto;
	private ProdutoModel produtoSelected;
	private String mensagem;
	private String preco;
	private List<ProdutoModel> listaProdutos;
	private ProdutoDataModel listaProdutoDataModel;
	
	public ProdutoController(){
		inicializarComponentes();
		prepararInclusao();
	}
	
	private void inicializarComponentes(){
		listaProdutos = new ArrayList<ProdutoModel>();
	}
	
	private void prepararInclusao(){
		setProduto(new ProdutosPOJO());
		setProdutoSelected(new ProdutoModel());
	}
	
	/**
	 * Método de cadastro de Produtos
	 * @return String
	 */
	public String cadastrarProduto(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		
		try {
			num = nf.parse(getPreco());
			getProduto().setPreco(new BigDecimal(num.doubleValue()));
			
			produtos.insert(getProduto());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_produto"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarProdutos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_produto"));
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
	public String alterarProduto(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		try {
			num = nf.parse(getProdutoSelected().getPreco().replace("R$ ", "0"));
			
			getProduto().setId(getProdutoSelected().getId());
			getProduto().setNome(getProdutoSelected().getNome());
			getProduto().setMarca(getProdutoSelected().getMarca());
			getProduto().setCategoria(getProdutoSelected().getCategoria());
			getProduto().setPreco(new BigDecimal(num.doubleValue()));
			getProduto().setDescricao(getProdutoSelected().getDescricao());

			produtos.update(getProduto());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_produto"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarProdutos();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_produto"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirProduto(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		getProduto().setId(getProdutoSelected().getId());
		
		try {
			produtos.deleteById(getProduto().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_produto"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_produto"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}
	}
	
    public void onRowSelect(SelectEvent event) {  
        FacesMessage msg = new FacesMessage("Produto Selecionado ", ((ProdutosPOJO) event.getObject()).getMarca());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public void onRowDblselect(SelectEvent event) {  
        FacesContext context = FacesContext.getCurrentInstance();  
        ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();  
        Flash flash = context.getExternalContext().getFlash();  
        flash.put("produtoSelected", (ProdutoModel) event.getObject());  
          
        handler.performNavigation("produtoDetail");  
    }
	
	/**
	 * Método que vai recuperar informações de produtos e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarProdutos(){
		
		List<ProdutosPOJO> lista = new ArrayList<ProdutosPOJO>();
		StringUtils utils = new StringUtils();
		setListaProdutoDataModel(null);
		setListaProdutos(null);
		setListaProdutos(new ArrayList<ProdutoModel>());
		
		try {
			lista = produtos.listar();
			
			if(lista.isEmpty()){
				setListaProdutoDataModel(new ProdutoDataModel());
				throw new Exception();
			}
			
			for (ProdutosPOJO produtos : lista) {
				ProdutoModel produto = new ProdutoModel();
				
				produto.setId(produtos.getId());
				produto.setNome(produtos.getNome());
				produto.setMarca(produtos.getMarca());
				produto.setCategoria(produtos.getCategoria());
				produto.setPreco(utils.bigDecimalToString(produtos.getPreco()));
				produto.setDescricao(produtos.getDescricao());
				
				getListaProdutos().add(produto);
			}
			
			setListaProdutoDataModel(new ProdutoDataModel(getListaProdutos()));
			
			
		} catch (Exception e) {
			setListaProdutoDataModel(new ProdutoDataModel());
			return "/pages/protected/produto/listaProdutos.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/produto/listaProdutos.xhtml?faces-redirect=true";
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public ProdutosPOJO getProduto() {
		return produto;
	}

	public void setProduto(ProdutosPOJO produto) {
		this.produto = produto;
	}

	public List<ProdutoModel> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<ProdutoModel> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public ProdutoModel getProdutoSelected() {
		return produtoSelected;
	}

	public void setProdutoSelected(ProdutoModel produtoSelected) {
		this.produtoSelected = produtoSelected;
	}

	public ProdutoDataModel getListaProdutoDataModel() {
		return listaProdutoDataModel;
	}

	public void setListaProdutoDataModel(ProdutoDataModel listaProdutoDataModel) {
		this.listaProdutoDataModel = listaProdutoDataModel;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}
}

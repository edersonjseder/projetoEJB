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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.beauty.datamodel.ContasAPagarDataModel;
import br.com.beauty.datamodel.ContasAPagarModel;
import br.com.beauty.datamodel.FornecedorModel;
import br.com.beauty.interfaces.ContasAPagar;
import br.com.beauty.pojo.ContasAPagarPOJO;
import br.com.beauty.pojo.FornecedorPOJO;
import br.com.beauty.utils.PropertiesUtil;
import br.com.beauty.utils.StringUtils;

@ManagedBean(name="contasPagarController")
@SessionScoped
public class ContasPagarController {
	
	@EJB
	private ContasAPagar contasPagar;
	
	private ContasAPagarPOJO contasAPagar;
	private FornecedorModel fornecedorSelected;
	private ContasAPagarModel contasPagarSelected;
	private String mensagem;
	private String valorTotal;
	private String valorPagamento;
	private String valorParcela;
	private String numParcela;
	private String dataVencimento;
	private List<FornecedorPOJO> listaFornecedores;
	private List<ContasAPagarModel> listaContasPagar;
	private ContasAPagarDataModel listaContasPagarDataModel;
	
	private Calendar dataAtual;
	private Calendar vencimento;
	private InputText valorParcelaText;
	private InputText totalPagarText;
	private InputText nrParcelasText;
	private Calendar dataPagamentoText;
	
	public ContasPagarController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void prepararCondicoesIniciais(){
		Date data = new Date(System.currentTimeMillis());    
		getDataAtual().setValue(data);
		getDataPagamentoText().setValue(data);
	}
	
	private void inicializarComponentes(){
		dataAtual = new Calendar();
		vencimento = new Calendar();
		valorParcelaText = new InputText();
		totalPagarText = new InputText();
		nrParcelasText = new InputText();
		dataPagamentoText = new Calendar();
		listaFornecedores = new ArrayList<FornecedorPOJO>();
	}
	
	private void prepararInclusao(){
		setContasAPagar(new ContasAPagarPOJO());
		setFornecedorSelected(new FornecedorModel());
	}
	
	/**
	 * Método de cadastro de Contas a Pagar
	 * @return String
	 */
	public String cadastrarContasAPagar(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorPagamento = null;
		Number valorParcela = null;
		
		try {
			valorPagamento = nf.parse(getValorPagamento());
			valorParcela = nf.parse(getValorParcela());
			
			getContasAPagar().setNroParcelas(getNumParcela().equals("") ? 0 : Integer.parseInt(getNumParcela()));
			getContasAPagar().setDataVencimento((Date)getVencimento().getValue());
			getContasAPagar().setNroParcelasAPagar(getContasAPagar().getNroParcelas());
			getContasAPagar().setValorAPagar(new BigDecimal(valorPagamento.doubleValue()));
			getContasAPagar().setValorParcela(new BigDecimal(valorParcela.doubleValue()));
			
			contasPagar.insert(getContasAPagar());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_conta"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarContasAPagar();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_conta"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			return "";
		}
		
	}
	
	public String concluirBaixa(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorPagar = null;
		Number valorParcela = null;
		Number totalPagar = null;
		Number valorPago = null;
		
		try {
			
			getDataPagamentoText().getValue();
			
			valorPagar = nf.parse(getContasPagarSelected().getValorAPagar().replace("R$ ", "0"));
			valorParcela = nf.parse(getContasPagarSelected().getValorParcela().replace("R$ ", "0"));
			totalPagar = nf.parse(getContasPagarSelected().getTotalAPagar().replace("R$ ", "0"));
			valorPago = nf.parse(getContasPagarSelected().getValorPago().replace("R$ ", "0"));
			
			getContasAPagar().setId(getContasPagarSelected().getId());
			getContasAPagar().setCredor(getContasPagarSelected().getCredor());
			getContasAPagar().setValorAPagar(new BigDecimal(valorPagar.doubleValue()));
			getContasAPagar().setStatusContasPagar(getContasPagarSelected().getStatusContasPagar());
			getContasAPagar().setNroParcelas(Integer.parseInt(getContasPagarSelected().getNroParcelas()));
			getContasAPagar().setValorParcela(new BigDecimal(valorParcela.doubleValue()));
			getContasAPagar().setValorPago(new BigDecimal(valorPago.doubleValue()));
			getContasAPagar().setTotalAPagar(new BigDecimal(totalPagar.doubleValue()));
			getContasAPagar().setDataPagamento((Date)getDataPagamentoText().getValue());
			getContasAPagar().setDataVencimento(new SimpleDateFormat("dd/MM/yyyy").parse(getContasPagarSelected().getDataVencimento()));
			getContasAPagar().setDocumento(getContasPagarSelected().getDocumento());
			
			if(!((Integer.parseInt((String)getNrParcelasText().getValue()) + Integer.parseInt(getContasPagarSelected().getNroParcelasPagas())) > getContasAPagar().getNroParcelas())){
				getContasAPagar().setNroParcelasPagas( Integer.parseInt(getContasPagarSelected().getNroParcelasPagas()) + Integer.parseInt((String)getNrParcelasText().getValue()));
			}else{
				throw new Exception();
			}
			
			if(getContasPagarSelected().getNroParcelasAPagar().equals("0")){
				getContasAPagar().setNroParcelasAPagar(Integer.parseInt((String)getNrParcelasText().getValue()));
			}else{
				getContasAPagar().setNroParcelasAPagar(Integer.parseInt(getContasPagarSelected().getNroParcelasAPagar()) - Integer.parseInt((String)getNrParcelasText().getValue()));
			}
			
			contasPagar.update(getContasAPagar());
			
			RequestContext.getCurrentInstance().execute("baixaDialog.hide();contasPagarDialog.hide();");

			return listarContasAPagar();
			
		}catch (Exception e) {
			setMensagem("Você só possui " + getContasPagarSelected().getNroParcelas() + " parcelas.");
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			RequestContext.getCurrentInstance().execute("baixaDialog.hide();contasPagarDialog.show();");
			return "";
		}
	}
	
	public void darBaixa(){
		StringUtils utils = new StringUtils();
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorPagar = null;
		Number valorParcela = null;
		
		try {
			valorPagar = nf.parse(getContasPagarSelected().getTotalAPagar().replace("R$ ", "0"));
			valorParcela = nf.parse(getContasPagarSelected().getValorParcela().replace("R$ ", "0"));
			
			getNrParcelasText().setValue(Integer.parseInt(getContasPagarSelected().getNroParcelas()) - Integer.parseInt(getContasPagarSelected().getNroParcelasPagas().equals("") ? "0" : getContasPagarSelected().getNroParcelasPagas()));
			getValorParcelaText().setValue(utils.bigDecimalToString(new BigDecimal(valorParcela.doubleValue())));
			getTotalPagarText().setValue(utils.bigDecimalToString(new BigDecimal(valorPagar.doubleValue())));
			
			RequestContext.getCurrentInstance().execute("baixaDialog.show();contasPagarDialog.hide();");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método de atualização de Contas a Pagar
	 * @return String
	 */
	public String alterarContasAPagar(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorPagar = null;
		Number valorParcela = null;
		
		try {
			valorPagar = nf.parse(getContasPagarSelected().getTotalAPagar().replace("R$ ", "0"));
			valorParcela = nf.parse(getContasPagarSelected().getValorParcela().replace("R$ ", "0"));
			
			getContasAPagar().setCredor(getContasPagarSelected().getCredor());
			getContasAPagar().setValorAPagar(new BigDecimal(valorPagar.doubleValue()));
			getContasAPagar().setStatusContasPagar(getContasPagarSelected().getStatusContasPagar());
			getContasAPagar().setNroParcelas(Integer.parseInt(getContasPagarSelected().getNroParcelas()));
			getContasAPagar().setNroParcelasAPagar(getContasAPagar().getNroParcelas());
			getContasAPagar().setValorParcela(new BigDecimal(valorParcela.doubleValue()));
			
			contasPagar.update(getContasAPagar());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_conta"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_conta"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirContasAPagar(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		
		
		try {
			
			getContasAPagar().setId(getContasPagarSelected().getId());
			getContasAPagar().setCredor(getContasPagarSelected().getCredor());
			getContasAPagar().setDataVencimento(new SimpleDateFormat("dd/MM/yyyy").parse(getContasPagarSelected().getDataVencimento()));
			getContasAPagar().setDocumento(getContasPagarSelected().getDocumento());
			getContasAPagar().setNroParcelas(Integer.parseInt(getContasPagarSelected().getNroParcelas()));
			getContasAPagar().setStatusContasPagar(getContasPagarSelected().getStatusContasPagar());
			getContasAPagar().setValorParcela(new BigDecimal(nf.parse(getContasPagarSelected().getValorParcela()).doubleValue()));
			getContasAPagar().setValorAPagar(new BigDecimal(nf.parse(getContasPagarSelected().getValorAPagar()).doubleValue()));
			getContasAPagar().setTotalAPagar(new BigDecimal(nf.parse(getContasPagarSelected().getTotalAPagar()).doubleValue()));
			
			contasPagar.delete(getContasAPagar());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_conta"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_conta"));
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
        flash.put("contasAPagarSelected", (ContasAPagarModel) event.getObject());  
          
        handler.performNavigation("contasAPagarDetail");  
    }
    
	/**
	 * Método que vai recuperar informações de Contas a Pagar e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarContasAPagar(){
		List<ContasAPagarPOJO> lista = new ArrayList<ContasAPagarPOJO>();
		StringUtils utils = new StringUtils();
		setListaContasPagarDataModel(null);
		setListaContasPagar(null);
		setListaContasPagar(new ArrayList<ContasAPagarModel>());
		
		try {
			lista = contasPagar.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (ContasAPagarPOJO contasAPagarLista : lista) {
				ContasAPagarModel contasAPagar = new ContasAPagarModel();
				
				contasAPagar.setId(contasAPagarLista.getId());
				contasAPagar.setDataVencimento(utils.dateToString(contasAPagarLista.getDataVencimento()));
				contasAPagar.setDataPagamento(utils.dateToString(contasAPagarLista.getDataPagamento()));
				contasAPagar.setCredor(contasAPagarLista.getCredor());
				contasAPagar.setDocumento(contasAPagarLista.getDocumento());
				contasAPagar.setNroParcelas(contasAPagarLista.getNroParcelas().toString());
				contasAPagar.setNroParcelasPagas(null == contasAPagarLista.getNroParcelasPagas() ? "0" : contasAPagarLista.getNroParcelasPagas().toString());
				contasAPagar.setNroParcelasAPagar(null == contasAPagarLista.getNroParcelasAPagar() ? "0" : contasAPagarLista.getNroParcelasAPagar().toString());
				contasAPagar.setValorParcela(utils.bigDecimalToString(contasAPagarLista.getValorParcela()));
				contasAPagar.setValorAPagar(utils.bigDecimalToString(contasAPagarLista.getValorAPagar()));
				contasAPagar.setValorPago(utils.bigDecimalToString(contasAPagarLista.getValorPago()));
				contasAPagar.setTotalAPagar(utils.bigDecimalToString(contasAPagarLista.getTotalAPagar()));
				contasAPagar.setStatusContasPagar(contasAPagarLista.getStatusContasPagar());
				
				getListaContasPagar().add(contasAPagar);
			}
			
			setListaContasPagarDataModel(new ContasAPagarDataModel(getListaContasPagar()));
			
		} catch (Exception e) {
			setListaContasPagarDataModel(new ContasAPagarDataModel());
			return "/pages/protected/contasPagar/listaCadastroContasPagar.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/contasPagar/listaCadastroContasPagar.xhtml?faces-redirect=true";
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

	public List<FornecedorPOJO> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<FornecedorPOJO> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
	}

	public FornecedorModel getFornecedorSelected() {
		return fornecedorSelected;
	}

	public void setFornecedorSelected(FornecedorModel fornecedorSelected) {
		this.fornecedorSelected = fornecedorSelected;
	}

	public ContasAPagarPOJO getContasAPagar() {
		return contasAPagar;
	}

	public void setContasAPagar(ContasAPagarPOJO contasAPagar) {
		this.contasAPagar = contasAPagar;
	}

	public ContasAPagarModel getContasPagarSelected() {
		return contasPagarSelected;
	}

	public void setContasPagarSelected(ContasAPagarModel contasPagarSelected) {
		this.contasPagarSelected = contasPagarSelected;
	}

	public List<ContasAPagarModel> getListaContasPagar() {
		return listaContasPagar;
	}

	public void setListaContasPagar(List<ContasAPagarModel> listaContasPagar) {
		this.listaContasPagar = listaContasPagar;
	}

	public ContasAPagarDataModel getListaContasPagarDataModel() {
		return listaContasPagarDataModel;
	}

	public void setListaContasPagarDataModel(
			ContasAPagarDataModel listaContasPagarDataModel) {
		this.listaContasPagarDataModel = listaContasPagarDataModel;
	}

	public String getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(String valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public String getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(String valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Calendar getVencimento() {
		return vencimento;
	}

	public void setVencimento(Calendar vencimento) {
		this.vencimento = vencimento;
	}

	public InputText getValorParcelaText() {
		return valorParcelaText;
	}

	public void setValorParcelaText(InputText valorParcelaText) {
		this.valorParcelaText = valorParcelaText;
	}

	public InputText getTotalPagarText() {
		return totalPagarText;
	}

	public void setTotalPagarText(InputText totalPagarText) {
		this.totalPagarText = totalPagarText;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Calendar getDataPagamentoText() {
		return dataPagamentoText;
	}

	public void setDataPagamentoText(Calendar dataPagamentoText) {
		this.dataPagamentoText = dataPagamentoText;
	}

	public InputText getNrParcelasText() {
		return nrParcelasText;
	}

	public void setNrParcelasText(InputText nrParcelasText) {
		this.nrParcelasText = nrParcelasText;
	}

	public String getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(String numParcela) {
		this.numParcela = numParcela;
	}
}

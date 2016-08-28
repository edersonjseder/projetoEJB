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

import br.com.beauty.beans.CaixaBean;
import br.com.beauty.beans.HistoricoFinanceiroBean;
import br.com.beauty.datamodel.CaixaModel;
import br.com.beauty.datamodel.CaixasDataModel;
import br.com.beauty.interfaces.Caixa;
import br.com.beauty.interfaces.HistoricoFinanceiro;
import br.com.beauty.pojo.CaixaPOJO;
import br.com.beauty.pojo.HistoricoFinanceiroPOJO;
import br.com.beauty.utils.PropertiesUtil;
import br.com.beauty.utils.StringUtils;

@ManagedBean(name="caixaController")
@SessionScoped
public class CaixaController {

	@EJB
	private Caixa caixas;
	private HistoricoFinanceiro historicoFinanceiro;
	
	private CaixaPOJO caixa;
	private CaixaModel caixaSelected;
	private String mensagem;
	private String valor;
	private Number saldoDinheiro;
	private Number saldoCheque;
	private String selectedEspecie = "Cheque";
	private String selectedTipo = "Entrada";
	private List<CaixaModel> listaCaixas;
	private List<HistoricoFinanceiroPOJO> listaHistoricoFinanceiro;
	private CaixasDataModel listaCaixaDataModel;
	
	private InputText textBanco;
	private InputText textConta;
	private InputText textCheque;
	private InputText saldoCaixaDinheiro;
	private InputText saldoCaixaCheque;
	private Calendar dataAtual;
	
	public CaixaController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void prepararCondicoesIniciais(){
		Date data = new Date(System.currentTimeMillis());    
		getDataAtual().setValue(data);
		caixas = new CaixaBean();
		pesquisarSaldosMensais();
	}
	
	private void pesquisarSaldosMensais(){
		StringUtils utils = new StringUtils();
		String dataMes = utils.dateToString(new Date(System.currentTimeMillis()));
		
		setSaldoDinheiro(buscarSaldoDinheiroMes(Integer.parseInt(dataMes.substring(3, 5))).doubleValue());
		setSaldoCheque(buscarSaldoChequeMes(Integer.parseInt(dataMes.substring(3, 5))));
	}
	
	private void inicializarComponentes(){
		textBanco = new InputText();
		textConta = new InputText();
		textCheque = new InputText();
		saldoCaixaDinheiro = new InputText();
		saldoCaixaCheque = new InputText();
		dataAtual = new Calendar();
		listaCaixas = new ArrayList<CaixaModel>();
		listaHistoricoFinanceiro = new ArrayList<HistoricoFinanceiroPOJO>();
		historicoFinanceiro = new HistoricoFinanceiroBean();
	}
	
	private void prepararInclusao(){
		setCaixa(new CaixaPOJO());
		setCaixaSelected(new CaixaModel());
	}
	
	public String acessarCadastro(){
		
		try {
			StringUtils utils = new StringUtils();
			
			pesquisarSaldosMensais();
			
			setListaHistoricoFinanceiro(historicoFinanceiro.listar());
			
			getSaldoCaixaDinheiro().setValue(utils.bigDecimalToString(new BigDecimal(getSaldoDinheiro().doubleValue())));
			getSaldoCaixaCheque().setValue(utils.bigDecimalToString(new BigDecimal(getSaldoCheque().doubleValue())));
			
		} catch (Exception e) {}
		
		return "/pages/protected/caixa/cadastroCaixa.xhtml?faces-redirect=true";
	}
	
	/**
	 * Método de cadastro de Caixa
	 * @return String
	 */
	public String cadastrarCaixa(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		try {
			num = nf.parse(getValor());
			getCaixa().setDataLancamento((Date) getDataAtual().getValue());
			getCaixa().setTipoLancamento(getSelectedTipo());
			getCaixa().setEspecie(getSelectedEspecie().equals("Cheque") ? "Dinheiro" : "Cheque");
			getCaixa().setValor(new BigDecimal(num.doubleValue()));
			
			caixas.insert(getCaixa());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_caixa"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarCaixas();
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_erro_caixa"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			return "";
		}
		
	}
	
	/**
	 * Método de atualização de Caixa
	 * @return String
	 */
	public String alterarCaixa(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		StringUtils utils = new StringUtils();
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number num = null;
		
		try {
			num = nf.parse(getCaixaSelected().getValor().replace("R$ ", "0"));
			getCaixa().setId(getCaixaSelected().getId());
			getCaixa().setDataLancamento(new SimpleDateFormat("dd/MM/yyyy").parse(getCaixaSelected().getDataLancamento()));
			getCaixa().setValor(new BigDecimal(num.doubleValue()));
			getCaixa().setHistorico(getCaixaSelected().getHistorico());
			getCaixa().setDescricao(getCaixaSelected().getDescricao());
			getCaixa().setTipoLancamento(getCaixaSelected().getTipoLancamento());
			getCaixa().setEspecie(getCaixaSelected().getEspecie());
			
			String dataMes = utils.dateToString(getCaixa().getDataLancamento());
			getCaixa().setMes(Integer.parseInt(dataMes.substring(3, 5)));
			getCaixa().setDataCaixaMensal(getCaixa().getDataLancamento());
			
			if(getCaixa().getEspecie().equals("Dinheiro")){
				getCaixa().setSaldoAtualCaixaDinheiro(getCaixa().getValor());
				getCaixa().setSaldoCaixaDinheiroMes(getCaixa().getValor());
				getCaixa().setSaldoCaixaChequeMes(getCaixa().getValor().multiply(new BigDecimal(0)));
			} else{
				getCaixa().setSaldoAtualCaixaCheque(getCaixa().getValor());
				getCaixa().setSaldoCaixaChequeMes(getCaixa().getValor());
				getCaixa().setSaldoCaixaDinheiroMes(getCaixa().getValor().multiply(new BigDecimal(0)));
			}
			
			caixas.update(getCaixa());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_sucesso_caixa"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_alteracao_erro_caixa"));
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			e.printStackTrace();
			return "";
		}		
	}
	
	public String excluirCaixa(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		getCaixa().setId(getCaixaSelected().getId());
		
		try {
			caixas.deleteById(getCaixa().getId());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_sucesso_caixa"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return "";
			
		} catch (Exception e) {
			setMensagem(PropertiesUtil.getProperty("mensagem_exclusao_erro_caixa"));
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
        flash.put("caixaSelected", (CaixaModel) event.getObject());  
          
        handler.performNavigation("caixaDetail");  
    }
    
    private Number buscarSaldoDinheiroMes(Integer mes){
    	return caixas.buscarSaldoDinheiroPorMes(mes);
    }
    
    private Number buscarSaldoChequeMes(Integer mes){
    	return caixas.buscarSaldoChequePorMes(mes);
    }
    
    public String preencherDataPesquisa(){
    	Date data = new Date(System.currentTimeMillis());
    	getDataAtual().setValue(data);
    	
    	RequestContext.getCurrentInstance().execute("mesDialog.show();");
    	
    	return listarCaixas();
    }
	
	/**
	 * Método que vai recuperar informações de caixas e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarCaixas(){
		List<CaixaPOJO> lista = new ArrayList<CaixaPOJO>();
		StringUtils utils = new StringUtils();
		setListaCaixaDataModel(null);
		setListaCaixas(null);
		setListaCaixas(new ArrayList<CaixaModel>());
		
		try {
			lista = caixas.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (CaixaPOJO caixas : lista) {
				CaixaModel caixa = new CaixaModel();
				
				caixa.setId(caixas.getId());
				caixa.setDataLancamento(utils.dateToString(caixas.getDataLancamento()));
				caixa.setValor(utils.bigDecimalToString(caixas.getValor()));
				caixa.setHistorico(caixas.getHistorico());
				caixa.setTipoLancamento(caixas.getTipoLancamento());
				caixa.setEspecie(caixas.getEspecie());
				caixa.setDescricao(caixas.getDescricao());
				caixa.setSaldoAtualCaixaDinheiro(utils.bigDecimalToString(caixas.getSaldoAtualCaixaDinheiro()));
				caixas.getMes();
				caixas.getDataCaixaMensal();
				caixas.getSaldoCaixaDinheiroMes();
				
				getListaCaixas().add(caixa);
			}
			
			setListaCaixaDataModel(new CaixasDataModel(getListaCaixas()));
			
		} catch (Exception e) {
			setListaCaixaDataModel(new CaixasDataModel());
			return "/pages/protected/caixa/listaCaixas.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/caixa/listaCaixas.xhtml?faces-redirect=true";
	}

	public CaixaPOJO getCaixa() {
		return caixa;
	}

	public void setCaixa(CaixaPOJO caixa) {
		this.caixa = caixa;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public List<CaixaModel> getListaCaixas() {
		return listaCaixas;
	}

	public void setListaCaixas(List<CaixaModel> listaCaixas) {
		this.listaCaixas = listaCaixas;
	}

	public Calendar getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Calendar dataAtual) {
		this.dataAtual = dataAtual;
	}

	public CaixasDataModel getListaCaixaDataModel() {
		return listaCaixaDataModel;
	}

	public void setListaCaixaDataModel(CaixasDataModel listaCaixaDataModel) {
		this.listaCaixaDataModel = listaCaixaDataModel;
	}

	public CaixaModel getCaixaSelected() {
		return caixaSelected;
	}

	public void setCaixaSelected(CaixaModel caixaSelected) {
		this.caixaSelected = caixaSelected;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public InputText getTextBanco() {
		return textBanco;
	}

	public void setTextBanco(InputText textBanco) {
		this.textBanco = textBanco;
	}

	public InputText getTextConta() {
		return textConta;
	}

	public void setTextConta(InputText textConta) {
		this.textConta = textConta;
	}

	public InputText getTextCheque() {
		return textCheque;
	}

	public void setTextCheque(InputText textCheque) {
		this.textCheque = textCheque;
	}

	public String getSelectedEspecie() {
		return selectedEspecie;
	}

	public void setSelectedEspecie(String selectedEspecie) {
		this.selectedEspecie = selectedEspecie;
	}

	public String getSelectedTipo() {
		return selectedTipo;
	}

	public void setSelectedTipo(String selectedTipo) {
		this.selectedTipo = selectedTipo;
	}

	public Number getSaldoDinheiro() {
		return saldoDinheiro;
	}

	public void setSaldoDinheiro(Number saldoDinheiro) {
		this.saldoDinheiro = saldoDinheiro;
	}

	public Number getSaldoCheque() {
		return saldoCheque;
	}

	public void setSaldoCheque(Number saldoCheque) {
		this.saldoCheque = saldoCheque;
	}

	public InputText getSaldoCaixaDinheiro() {
		return saldoCaixaDinheiro;
	}

	public void setSaldoCaixaDinheiro(InputText saldoCaixaDinheiro) {
		this.saldoCaixaDinheiro = saldoCaixaDinheiro;
	}

	public InputText getSaldoCaixaCheque() {
		return saldoCaixaCheque;
	}

	public void setSaldoCaixaCheque(InputText saldoCaixaCheque) {
		this.saldoCaixaCheque = saldoCaixaCheque;
	}

	public List<HistoricoFinanceiroPOJO> getListaHistoricoFinanceiro() {
		return listaHistoricoFinanceiro;
	}

	public void setListaHistoricoFinanceiro(
			List<HistoricoFinanceiroPOJO> listaHistoricoFinanceiro) {
		this.listaHistoricoFinanceiro = listaHistoricoFinanceiro;
	}
}

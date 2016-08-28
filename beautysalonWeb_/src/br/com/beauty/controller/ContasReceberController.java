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

import br.com.beauty.datamodel.ContasAReceberDataModel;
import br.com.beauty.datamodel.ContasAReceberModel;
import br.com.beauty.interfaces.ContasAReceber;
import br.com.beauty.pojo.ContasAReceberPOJO;
import br.com.beauty.util.StringUtils;
import br.com.beauty.utils.PropertiesUtil;

@ManagedBean(name="contasReceberController")
@SessionScoped
public class ContasReceberController {
	
	@EJB
	private ContasAReceber contasReceber;
	
	private ContasAReceberPOJO contasAReceber;
	private ContasAReceberModel contasReceberSelected;
	private String mensagem;
	private String valorTotal;
	private String valorRecebimento;
	private String valorParcela;
	private String numParcela;
	private String dataVencimento;
	private List<ContasAReceberModel> listaContasReceber;
	private ContasAReceberDataModel listaContasReceberDataModel;
	
	private Calendar dataAtual;
	private Calendar vencimento;
	private InputText valorParcelaText;
	private InputText totalReceberText;
	private InputText nrParcelasText;
	private Calendar dataRecebimentoText;
	
	public ContasReceberController(){
		inicializarComponentes();
		prepararInclusao();
		prepararCondicoesIniciais();
	}
	
	private void prepararCondicoesIniciais(){
		Date data = new Date(System.currentTimeMillis());    
		getDataAtual().setValue(data);
		getDataRecebimentoText().setValue(data);
	}
	
	private void inicializarComponentes(){
		dataAtual = new Calendar();
		vencimento = new Calendar();
		valorParcelaText = new InputText();
		totalReceberText = new InputText();
		nrParcelasText = new InputText();
		dataRecebimentoText = new Calendar();		
		dataAtual = new Calendar();
		listaContasReceber = new ArrayList<ContasAReceberModel>();
	}
	
	private void prepararInclusao(){
		setContasAReceber(new ContasAReceberPOJO());
	}
	
	/**
	 * Método de cadastro de Contas a Receber
	 * @return String
	 */
	public String cadastrarContasAReceber(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorRecebimento = null;
		Number valorParcela = null;
		
		try {
			valorRecebimento = nf.parse(getValorRecebimento());
			valorParcela = nf.parse(getValorParcela());
			
			getContasAReceber().setNroParcelas(getNumParcela().equals("") ? 0 : Integer.parseInt(getNumParcela()));
			getContasAReceber().setDataVencimento((Date)getVencimento().getValue());
			getContasAReceber().setNroParcelasAReceber(getContasAReceber().getNroParcelas());
			getContasAReceber().setValorAReceber(new BigDecimal(valorRecebimento.doubleValue()));
			getContasAReceber().setValorParcela(new BigDecimal(valorParcela.doubleValue()));
			
			contasReceber.insert(getContasAReceber());
			
			setMensagem(PropertiesUtil.getProperty("mensagem_cadastro_sucesso_conta"));
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", getMensagem());
			context.addMessage(null, msg);
			return listarContasAReceber();
			
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
		Number valorReceber = null;
		Number valorParcela = null;
		Number totalReceber = null;
		Number valorRecebido = null;
		
		try {
			
			getDataRecebimentoText().getValue();
			
			valorReceber = nf.parse(getContasReceberSelected().getValorAReceber().replace("R$ ", "0"));
			valorParcela = nf.parse(getContasReceberSelected().getValorParcela().replace("R$ ", "0"));
			totalReceber = nf.parse(getContasReceberSelected().getTotalAReceber().replace("R$ ", "0"));
			valorRecebido = nf.parse(getContasReceberSelected().getValorRecebido().replace("R$ ", "0"));
			
			getContasAReceber().setId(getContasReceberSelected().getId());
			getContasAReceber().setCliente(getContasReceberSelected().getCliente());
			getContasAReceber().setValorAReceber(new BigDecimal(valorReceber.doubleValue()));
			getContasAReceber().setStatusContasReceber(getContasReceberSelected().getStatusContasReceber());
			getContasAReceber().setNroParcelas(Integer.parseInt(getContasReceberSelected().getNroParcelas()));
			getContasAReceber().setValorParcela(new BigDecimal(valorParcela.doubleValue()));
			getContasAReceber().setValorRecebido(new BigDecimal(valorRecebido.doubleValue()));
			getContasAReceber().setTotalAReceber(new BigDecimal(totalReceber.doubleValue()));
			getContasAReceber().setDataRecebimento((Date)getDataRecebimentoText().getValue());
			getContasAReceber().setDataVencimento(new SimpleDateFormat("dd/MM/yyyy").parse(getContasReceberSelected().getDataVencimento()));
			getContasAReceber().setDocumento(getContasReceberSelected().getDocumento());
			
			if(!((Integer.parseInt((String)getNrParcelasText().getValue()) + Integer.parseInt(getContasReceberSelected().getNroParcelasRecebidas())) > getContasAReceber().getNroParcelas())){
				getContasAReceber().setNroParcelasRecebidas( Integer.parseInt(getContasReceberSelected().getNroParcelasRecebidas()) + Integer.parseInt((String)getNrParcelasText().getValue()));
			}else{
				throw new Exception();
			}
			
			if(getContasReceberSelected().getNroParcelasAReceber().equals("0")){
				getContasAReceber().setNroParcelasAReceber(Integer.parseInt((String)getNrParcelasText().getValue()));
			}else{
				getContasAReceber().setNroParcelasAReceber(Integer.parseInt(getContasReceberSelected().getNroParcelasAReceber()) - Integer.parseInt((String)getNrParcelasText().getValue()));
			}
			
			contasReceber.update(getContasAReceber());
			
			RequestContext.getCurrentInstance().execute("baixaDialog.hide();contasReceberDialog.hide();");

			return listarContasAReceber();
			
		}catch (Exception e) {
			setMensagem("Você só possui " + getContasReceberSelected().getNroParcelas() + " parcelas.");
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", getMensagem());
			context.addMessage(null, msg);
			RequestContext.getCurrentInstance().execute("baixaDialog.hide();contasReceberDialog.show();");
			return "";
		}
	}	
	
	public void darBaixa(){
		StringUtils utils = new StringUtils();
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorReceber = null;
		Number valorParcela = null;
		
		try {
			valorReceber = nf.parse(getContasReceberSelected().getTotalAReceber().replace("R$ ", "0"));
			valorParcela = nf.parse(getContasReceberSelected().getValorParcela().replace("R$ ", "0"));
			
			getNrParcelasText().setValue(Integer.parseInt(getContasReceberSelected().getNroParcelas()) - Integer.parseInt(getContasReceberSelected().getNroParcelasRecebidas().equals("") ? "0" : getContasReceberSelected().getNroParcelasRecebidas()));
			getValorParcelaText().setValue(utils.bigDecimalToString(new BigDecimal(valorParcela.doubleValue())));
			getTotalReceberText().setValue(utils.bigDecimalToString(new BigDecimal(valorReceber.doubleValue())));
			
			RequestContext.getCurrentInstance().execute("baixaDialog.show();contasReceberDialog.hide();");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método de atualização de Contas a Receber
	 * @return String
	 */
	public String alterarContasAReceber(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		Number valorReceber = null;
		Number valorParcela = null;
		
		try {
			valorReceber = nf.parse(getContasReceberSelected().getTotalAReceber().replace("R$ ", "0"));
			valorParcela = nf.parse(getContasReceberSelected().getValorParcela().replace("R$ ", "0"));
			
			getContasAReceber().setCliente(getContasReceberSelected().getCliente());
			getContasAReceber().setValorAReceber(new BigDecimal(valorReceber.doubleValue()));
			getContasAReceber().setStatusContasReceber(getContasReceberSelected().getStatusContasReceber());
			getContasAReceber().setNroParcelas(Integer.parseInt(getContasReceberSelected().getNroParcelas()));
			getContasAReceber().setNroParcelasAReceber(getContasAReceber().getNroParcelas());
			getContasAReceber().setValorParcela(new BigDecimal(valorParcela.doubleValue()));
			
			contasReceber.update(getContasAReceber());
			
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
	
	public String excluirContasAReceber(){
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		Locale locBrazil = new Locale("pt", "BR"); 
		NumberFormat nf = NumberFormat.getInstance(locBrazil);
		
		try {
			
			getContasAReceber().setId(getContasReceberSelected().getId());
			getContasAReceber().setCliente(getContasReceberSelected().getCliente());
			getContasAReceber().setDataVencimento(new SimpleDateFormat("dd/MM/yyyy").parse(getContasReceberSelected().getDataVencimento()));
			getContasAReceber().setDocumento(getContasReceberSelected().getDocumento());
			getContasAReceber().setNroParcelas(Integer.parseInt(getContasReceberSelected().getNroParcelas()));
			getContasAReceber().setStatusContasReceber(getContasReceberSelected().getStatusContasReceber());
			getContasAReceber().setValorParcela(new BigDecimal(nf.parse(getContasReceberSelected().getValorParcela()).doubleValue()));
			getContasAReceber().setValorAReceber(new BigDecimal(nf.parse(getContasReceberSelected().getValorAReceber()).doubleValue()));
			getContasAReceber().setTotalAReceber(new BigDecimal(nf.parse(getContasReceberSelected().getTotalAReceber()).doubleValue()));
			
			contasReceber.deleteById(getContasAReceber().getId());
			
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
        flash.put("contasAReceberSelected", (ContasAReceberModel) event.getObject());  
          
        handler.performNavigation("contasAReceberDetail");  
    }
    
	/**
	 * Método que vai recuperar informações de Contas a Receber e adicionar 
	 * em uma lista para ser mostrada numa tabela na tela para o usuário
	 */
	public String listarContasAReceber(){
		List<ContasAReceberPOJO> lista = new ArrayList<ContasAReceberPOJO>();
		StringUtils utils = new StringUtils();
		setListaContasReceberDataModel(null);
		setListaContasReceber(null);
		setListaContasReceber(new ArrayList<ContasAReceberModel>());
		
		try {
			lista = contasReceber.listar();
			
			if(lista.isEmpty()){
				throw new Exception();
			}
			
			for (ContasAReceberPOJO contasAReceberLista : lista) {
				ContasAReceberModel contasAReceber = new ContasAReceberModel();
				
				contasAReceber.setId(contasAReceberLista.getId());
				contasAReceber.setDataVencimento(utils.dateToString(contasAReceberLista.getDataVencimento()));
				contasAReceber.setDataRecebimento(utils.dateToString(contasAReceberLista.getDataRecebimento()));
				contasAReceber.setCliente(contasAReceberLista.getCliente());
				contasAReceber.setDocumento(contasAReceberLista.getDocumento());
				contasAReceber.setNroParcelas(contasAReceberLista.getNroParcelas().toString());
				contasAReceber.setNroParcelasRecebidas(null == contasAReceberLista.getNroParcelasRecebidas() ? "0" : contasAReceberLista.getNroParcelasRecebidas().toString());
				contasAReceber.setNroParcelasAReceber(null == contasAReceberLista.getNroParcelasAReceber() ? "0" : contasAReceberLista.getNroParcelasAReceber().toString());
				contasAReceber.setValorParcela(utils.bigDecimalToString(contasAReceberLista.getValorParcela()));
				contasAReceber.setValorAReceber(utils.bigDecimalToString(contasAReceberLista.getValorAReceber()));
				contasAReceber.setValorRecebido(utils.bigDecimalToString(contasAReceberLista.getValorRecebido()));
				contasAReceber.setTotalAReceber(utils.bigDecimalToString(contasAReceberLista.getTotalAReceber()));
				contasAReceber.setStatusContasReceber(contasAReceberLista.getStatusContasReceber());
				
				getListaContasReceber().add(contasAReceber);
			}
			
			setListaContasReceberDataModel(new ContasAReceberDataModel(getListaContasReceber()));
			
		} catch (Exception e) {
			setListaContasReceberDataModel(new ContasAReceberDataModel());
			return "/pages/protected/contasReceber/listaCadastroContasReceber.xhtml?faces-redirect=true";
			
		}
		return "/pages/protected/contasReceber/listaCadastroContasReceber.xhtml?faces-redirect=true";
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

	public ContasAReceberPOJO getContasAReceber() {
		return contasAReceber;
	}

	public void setContasAReceber(ContasAReceberPOJO contasAReceber) {
		this.contasAReceber = contasAReceber;
	}

	public List<ContasAReceberModel> getListaContasReceber() {
		return listaContasReceber;
	}

	public void setListaContasReceber(List<ContasAReceberModel> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}

	public ContasAReceberDataModel getListaContasReceberDataModel() {
		return listaContasReceberDataModel;
	}

	public void setListaContasReceberDataModel(
			ContasAReceberDataModel listaContasReceberDataModel) {
		this.listaContasReceberDataModel = listaContasReceberDataModel;
	}

	public ContasAReceberModel getContasReceberSelected() {
		return contasReceberSelected;
	}

	public void setContasReceberSelected(ContasAReceberModel contasReceberSelected) {
		this.contasReceberSelected = contasReceberSelected;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getValorRecebimento() {
		return valorRecebimento;
	}

	public void setValorRecebimento(String valorRecebimento) {
		this.valorRecebimento = valorRecebimento;
	}

	public String getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(String valorParcela) {
		this.valorParcela = valorParcela;
	}

	public String getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(String numParcela) {
		this.numParcela = numParcela;
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

	public InputText getNrParcelasText() {
		return nrParcelasText;
	}

	public void setNrParcelasText(InputText nrParcelasText) {
		this.nrParcelasText = nrParcelasText;
	}

	public Calendar getDataRecebimentoText() {
		return dataRecebimentoText;
	}

	public void setDataRecebimentoText(Calendar dataRecebimentoText) {
		this.dataRecebimentoText = dataRecebimentoText;
	}

	public InputText getTotalReceberText() {
		return totalReceberText;
	}

	public void setTotalReceberText(InputText totalReceberText) {
		this.totalReceberText = totalReceberText;
	}
}

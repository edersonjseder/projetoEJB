package br.com.beauty.datamodel;




public class ContasAPagarModel {
	
	private Integer id;
	
	private String credor;
	private String nroParcelas;
	private String nroParcelasPagas;
	private String nroParcelasAPagar;
	private String statusContasPagar;	
	private String documento;	
	private String valorAPagar;
	private String valorParcela;
	private String valorPago;
	private String totalAPagar;
	private String dataVencimento;
	private String dataPagamento;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCredor() {
		return credor;
	}
	public void setCredor(String credor) {
		this.credor = credor;
	}
	public String getNroParcelas() {
		return nroParcelas;
	}
	public void setNroParcelas(String nroParcelas) {
		this.nroParcelas = nroParcelas;
	}
	public String getValorAPagar() {
		return valorAPagar;
	}
	public void setValorAPagar(String valorAPagar) {
		this.valorAPagar = valorAPagar;
	}
	public String getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(String valorParcela) {
		this.valorParcela = valorParcela;
	}
	public String getTotalAPagar() {
		return totalAPagar;
	}
	public void setTotalAPagar(String totalAPagar) {
		this.totalAPagar = totalAPagar;
	}
	public String getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getStatusContasPagar() {
		return statusContasPagar;
	}
	public void setStatusContasPagar(String statusContasPagar) {
		this.statusContasPagar = statusContasPagar;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNroParcelasPagas() {
		return nroParcelasPagas;
	}
	public void setNroParcelasPagas(String nroParcelasPagas) {
		this.nroParcelasPagas = nroParcelasPagas;
	}
	public String getValorPago() {
		return valorPago;
	}
	public void setValorPago(String valorPago) {
		this.valorPago = valorPago;
	}
	public String getNroParcelasAPagar() {
		return nroParcelasAPagar;
	}
	public void setNroParcelasAPagar(String nroParcelasAPagar) {
		this.nroParcelasAPagar = nroParcelasAPagar;
	}
}

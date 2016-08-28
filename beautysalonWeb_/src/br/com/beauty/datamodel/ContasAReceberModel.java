package br.com.beauty.datamodel;




public class ContasAReceberModel {
	
	private Integer id;
	
	private String cliente;
	private String nroParcelas;
	private String nroParcelasRecebidas;
	private String nroParcelasAReceber;
	private String statusContasReceber;	
	private String documento;	
	private String valorAReceber;
	private String valorParcela;
	private String valorRecebido;
	private String totalAReceber;
	private String dataVencimento;
	private String dataRecebimento;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getNroParcelas() {
		return nroParcelas;
	}
	public void setNroParcelas(String nroParcelas) {
		this.nroParcelas = nroParcelas;
	}
	public String getNroParcelasRecebidas() {
		return nroParcelasRecebidas;
	}
	public void setNroParcelasRecebidas(String nroParcelasRecebidas) {
		this.nroParcelasRecebidas = nroParcelasRecebidas;
	}
	public String getNroParcelasAReceber() {
		return nroParcelasAReceber;
	}
	public void setNroParcelasAReceber(String nroParcelasAReceber) {
		this.nroParcelasAReceber = nroParcelasAReceber;
	}
	public String getStatusContasReceber() {
		return statusContasReceber;
	}
	public void setStatusContasReceber(String statusContasReceber) {
		this.statusContasReceber = statusContasReceber;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(String valorParcela) {
		this.valorParcela = valorParcela;
	}
	public String getTotalAReceber() {
		return totalAReceber;
	}
	public void setTotalAReceber(String totalAReceber) {
		this.totalAReceber = totalAReceber;
	}
	public String getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(String dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public String getValorAReceber() {
		return valorAReceber;
	}
	public void setValorAReceber(String valorAReceber) {
		this.valorAReceber = valorAReceber;
	}
	public String getValorRecebido() {
		return valorRecebido;
	}
	public void setValorRecebido(String valorRecebido) {
		this.valorRecebido = valorRecebido;
	}
}

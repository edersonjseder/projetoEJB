package br.com.beauty.datamodel;


public class CaixaModel {
	
	private Integer id;
	
	private Integer mes;
	private String dataLancamento;
	private String dataCaixaMensal;
	private String tipoLancamento;
	private String especie;
	private String banco;
	private String conta;
	private String nroCheque;
	private String valor;
	private String saldoAtualCaixaDinheiro;
	private String saldoAtualCaixaCheque;
	private String saldoCaixaDinheiroMes;
	private String saldoCaixaChequeMes;
	private String historico;
	private String descricao;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(String tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getNroCheque() {
		return nroCheque;
	}

	public void setNroCheque(String nroCheque) {
		this.nroCheque = nroCheque;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getSaldoAtualCaixaDinheiro() {
		return saldoAtualCaixaDinheiro;
	}

	public void setSaldoAtualCaixaDinheiro(String saldoAtualCaixaDinheiro) {
		this.saldoAtualCaixaDinheiro = saldoAtualCaixaDinheiro;
	}

	public String getSaldoAtualCaixaCheque() {
		return saldoAtualCaixaCheque;
	}

	public void setSaldoAtualCaixaCheque(String saldoAtualCaixaCheque) {
		this.saldoAtualCaixaCheque = saldoAtualCaixaCheque;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getDataCaixaMensal() {
		return dataCaixaMensal;
	}

	public void setDataCaixaMensal(String dataCaixaMensal) {
		this.dataCaixaMensal = dataCaixaMensal;
	}

	public String getSaldoCaixaDinheiroMes() {
		return saldoCaixaDinheiroMes;
	}

	public void setSaldoCaixaDinheiroMes(String saldoCaixaDinheiroMes) {
		this.saldoCaixaDinheiroMes = saldoCaixaDinheiroMes;
	}

	public String getSaldoCaixaChequeMes() {
		return saldoCaixaChequeMes;
	}

	public void setSaldoCaixaChequeMes(String saldoCaixaChequeMes) {
		this.saldoCaixaChequeMes = saldoCaixaChequeMes;
	}

}

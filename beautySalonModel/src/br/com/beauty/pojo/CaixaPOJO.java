package br.com.beauty.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="caixa")
public class CaixaPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2054706364465580083L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCaixa")
	private Integer id;
	
	private Integer mes;
	private Date dataLancamento;
	private Date dataCaixaMensal;
	private String tipoLancamento;
	private String especie;
	private String banco;
	private String conta;
	private String nroCheque;
	private BigDecimal valor = new BigDecimal(0.0D);
	private BigDecimal saldoAtualCaixaDinheiro = new BigDecimal(0.0D);
	private BigDecimal saldoAtualCaixaCheque = new BigDecimal(0.0D);
	private BigDecimal saldoCaixaDinheiroMes = new BigDecimal(0.0D);
	private BigDecimal saldoCaixaChequeMes = new BigDecimal(0.0D);
	private String historico;
	private String descricao;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
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

	public Date getDataCaixaMensal() {
		return dataCaixaMensal;
	}

	public void setDataCaixaMensal(Date dataCaixaMensal) {
		this.dataCaixaMensal = dataCaixaMensal;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getSaldoAtualCaixaDinheiro() {
		return saldoAtualCaixaDinheiro;
	}

	public void setSaldoAtualCaixaDinheiro(BigDecimal saldoAtualCaixaDinheiro) {
		this.saldoAtualCaixaDinheiro = saldoAtualCaixaDinheiro;
	}

	public BigDecimal getSaldoAtualCaixaCheque() {
		return saldoAtualCaixaCheque;
	}

	public void setSaldoAtualCaixaCheque(BigDecimal saldoAtualCaixaCheque) {
		this.saldoAtualCaixaCheque = saldoAtualCaixaCheque;
	}

	public BigDecimal getSaldoCaixaDinheiroMes() {
		return saldoCaixaDinheiroMes;
	}

	public void setSaldoCaixaDinheiroMes(BigDecimal saldoCaixaDinheiroMes) {
		this.saldoCaixaDinheiroMes = saldoCaixaDinheiroMes;
	}

	public BigDecimal getSaldoCaixaChequeMes() {
		return saldoCaixaChequeMes;
	}

	public void setSaldoCaixaChequeMes(BigDecimal saldoCaixaChequeMes) {
		this.saldoCaixaChequeMes = saldoCaixaChequeMes;
	}

}

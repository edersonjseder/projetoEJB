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
@Table(name="contasreceber")
public class ContasAReceberPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7306455436830957602L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idContasReceber")
	private Integer id;
	
	private String cliente;
	private Integer nroParcelas;
	private Integer nroParcelasRecebidas;
	private Integer nroParcelasAReceber;
	private String statusContasReceber;	
	private String documento;	
	private BigDecimal valorAReceber = new BigDecimal(0.0D);
	private BigDecimal valorParcela = new BigDecimal(0.0D);
	private BigDecimal valorRecebido = new BigDecimal(0.0D);
	private BigDecimal totalAReceber = new BigDecimal(0.0D);
	private Date dataVencimento;
	private Date dataRecebimento;
	
	
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
	public Integer getNroParcelas() {
		return nroParcelas;
	}
	public void setNroParcelas(Integer nroParcelas) {
		this.nroParcelas = nroParcelas;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public BigDecimal getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}
	public BigDecimal getTotalAReceber() {
		return totalAReceber;
	}
	public void setTotalAReceber(BigDecimal totalAReceber) {
		this.totalAReceber = totalAReceber;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public Date getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public Integer getNroParcelasRecebidas() {
		return nroParcelasRecebidas;
	}
	public void setNroParcelasRecebidas(Integer nroParcelasRecebidas) {
		this.nroParcelasRecebidas = nroParcelasRecebidas;
	}
	public Integer getNroParcelasAReceber() {
		return nroParcelasAReceber;
	}
	public void setNroParcelasAReceber(Integer nroParcelasAReceber) {
		this.nroParcelasAReceber = nroParcelasAReceber;
	}
	public String getStatusContasReceber() {
		return statusContasReceber;
	}
	public void setStatusContasReceber(String statusContasReceber) {
		this.statusContasReceber = statusContasReceber;
	}
	public BigDecimal getValorAReceber() {
		return valorAReceber;
	}
	public void setValorAReceber(BigDecimal valorAReceber) {
		this.valorAReceber = valorAReceber;
	}
	public BigDecimal getValorRecebido() {
		return valorRecebido;
	}
	public void setValorRecebido(BigDecimal valorRecebido) {
		this.valorRecebido = valorRecebido;
	}
}

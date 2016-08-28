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
@Table(name="contaspagar")
public class ContasAPagarPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1064755983195278767L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idContasPagar")
	private Integer id;
	
	private String credor;
	private Integer nroParcelas;
	private Integer nroParcelasPagas;
	private Integer nroParcelasAPagar;
	private String statusContasPagar;	
	private String documento;	
	private BigDecimal valorAPagar = new BigDecimal(0.0D);
	private BigDecimal valorParcela = new BigDecimal(0.0D);
	private BigDecimal valorPago = new BigDecimal(0.0D);
	private BigDecimal totalAPagar = new BigDecimal(0.0D);
	private Date dataVencimento;
	private Date dataPagamento;
	
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
	public Integer getNroParcelas() {
		return nroParcelas;
	}
	public void setNroParcelas(Integer nroParcelas) {
		this.nroParcelas = nroParcelas;
	}
	public BigDecimal getValorAPagar() {
		return valorAPagar;
	}
	public void setValorAPagar(BigDecimal valorAPagar) {
		this.valorAPagar = valorAPagar;
	}
	public BigDecimal getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public BigDecimal getTotalAPagar() {
		return totalAPagar;
	}
	public void setTotalAPagar(BigDecimal totalAPagar) {
		this.totalAPagar = totalAPagar;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getStatusContasPagar() {
		return statusContasPagar;
	}
	public void setStatusContasPagar(String statusContasPagar) {
		this.statusContasPagar = statusContasPagar;
	}
	public Integer getNroParcelasPagas() {
		return nroParcelasPagas;
	}
	public void setNroParcelasPagas(Integer nroParcelasPagas) {
		this.nroParcelasPagas = nroParcelasPagas;
	}
	public BigDecimal getValorPago() {
		return valorPago;
	}
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	public Integer getNroParcelasAPagar() {
		return nroParcelasAPagar;
	}
	public void setNroParcelasAPagar(Integer nroParcelasAPagar) {
		this.nroParcelasAPagar = nroParcelasAPagar;
	}
}

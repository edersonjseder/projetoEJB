package br.com.beauty.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="estoque")
public class EstoquePOJO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8755167543275371662L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idEstoque")
	private Integer id;
	
	private Date dataProcesso;
	private Integer quantidade;
	private Integer qtdeAtualEstoque;
	private String tipoProcesso;
	private BigDecimal valorUnitario = new BigDecimal(0.0D);
	private BigDecimal valorTotalEstoque = new BigDecimal(0.0D);
	private String descricao;
	
	@ManyToOne(targetEntity = ProdutosPOJO.class)
	@JoinColumn(name = "idProdutos")
	private ProdutosPOJO produtos;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataProcesso() {
		return dataProcesso;
	}

	public void setDataProcesso(Date dataProcesso) {
		this.dataProcesso = dataProcesso;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQtdeAtualEstoque() {
		return qtdeAtualEstoque;
	}

	public void setQtdeAtualEstoque(Integer qtdeAtualEstoque) {
		this.qtdeAtualEstoque = qtdeAtualEstoque;
	}

	public String getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorTotalEstoque() {
		return valorTotalEstoque;
	}

	public void setValorTotalEstoque(BigDecimal valorTotalEstoque) {
		this.valorTotalEstoque = valorTotalEstoque;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProdutosPOJO getProdutos() {
		return produtos;
	}

	public void setProdutos(ProdutosPOJO produtos) {
		this.produtos = produtos;
	}
}

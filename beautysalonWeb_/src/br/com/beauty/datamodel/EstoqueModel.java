package br.com.beauty.datamodel;

import br.com.beauty.pojo.ProdutosPOJO;

public class EstoqueModel {
	
	private Integer id;
	private String dataProcesso;
	private String quantidade;
	private String qtdeAtualEstoque;
	private String tipoProcesso;
	private String valorUnitario;
	private String valorTotalEstoque;
	private String descricao;
	
	private ProdutosPOJO produtos = new ProdutosPOJO();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataProcesso() {
		return dataProcesso;
	}

	public void setDataProcesso(String dataProcesso) {
		this.dataProcesso = dataProcesso;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getQtdeAtualEstoque() {
		return qtdeAtualEstoque;
	}

	public void setQtdeAtualEstoque(String qtdeAtualEstoque) {
		this.qtdeAtualEstoque = qtdeAtualEstoque;
	}

	public String getTipoProcesso() {
		return tipoProcesso;
	}

	public void setTipoProcesso(String tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	public String getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getValorTotalEstoque() {
		return valorTotalEstoque;
	}

	public void setValorTotalEstoque(String valorTotalEstoque) {
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

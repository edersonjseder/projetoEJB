package br.com.beauty.datamodel;


public class ServicoModel {
	
	private Integer id;
	
	private String dataServico;
	private String nome;
	private String descricao;
	private String preco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public String getDataServico() {
		return dataServico;
	}

	public void setDataServico(String dataServico) {
		this.dataServico = dataServico;
	}
}

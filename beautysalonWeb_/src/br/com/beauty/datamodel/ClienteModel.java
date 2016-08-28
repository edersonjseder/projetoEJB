package br.com.beauty.datamodel;

import br.com.beauty.pojo.EnderecoPOJO;

public class ClienteModel {

	private Integer id;
	
	private String nome;
	private String cpf;
	private String sexo;
	private String telefone;
	private String email;
	
	private EnderecoPOJO endereco = new EnderecoPOJO();

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EnderecoPOJO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoPOJO endereco) {
		this.endereco = endereco;
	}
}

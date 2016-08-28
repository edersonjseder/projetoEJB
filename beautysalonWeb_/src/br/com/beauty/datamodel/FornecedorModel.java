package br.com.beauty.datamodel;

import br.com.beauty.pojo.EnderecoPOJO;

public class FornecedorModel {
	
	private Integer id;
	
	private String nomeFornecedor;
	private String cpfOuCnpj;
	private String ieOuRg;
	private String dataCadastro;
	private String telefone;
	private String fax;
	private String email;
	
	private EnderecoPOJO endereco = new EnderecoPOJO();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeFornecedor() {
		return nomeFornecedor;
	}
	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}
	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}
	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	public String getIeOuRg() {
		return ieOuRg;
	}
	public void setIeOuRg(String ieOuRg) {
		this.ieOuRg = ieOuRg;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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

package br.com.beauty.datamodel;

import br.com.beauty.pojo.EnderecoPOJO;
import br.com.beauty.pojo.User;
import br.com.beauty.pojo.UserRole;

public class FuncionarioModel {

	private Integer id;
	
	private String nome;
	private String cargo;
	private String cpf;
	private String sexo;
	private String dataNascimento;
	private String dataAdmissao;
	private String dataDemissao;
	private String telefone;
	private String email;
	private String remuneracao;
	private String statusFuncionario;
	private boolean ativo;
	
	private User user = new User();
	private UserRole userRole = new UserRole();
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
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(String dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public String getDataDemissao() {
		return dataDemissao;
	}
	public void setDataDemissao(String dataDemissao) {
		this.dataDemissao = dataDemissao;
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
	public String getRemuneracao() {
		return remuneracao;
	}
	public void setRemuneracao(String remuneracao) {
		this.remuneracao = remuneracao;
	}
	public EnderecoPOJO getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoPOJO endereco) {
		this.endereco = endereco;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public String getStatusFuncionario() {
		return statusFuncionario;
	}
	public void setStatusFuncionario(String statusFuncionario) {
		this.statusFuncionario = statusFuncionario;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}

package br.com.beauty.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="funcionario")
public class FuncionarioPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -916742016477228833L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idFuncionario")
	private Integer id;
	
	private String nome;
	private String cargo;
	private String cpf;
	private String sexo;
	private Date dataNascimento;
	private Date dataAdmissao;
	private Date dataDemissao;
	private String telefone;
	private String email;
	private BigDecimal remuneracao = new BigDecimal(0.0D);
	private boolean statusFuncionario;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idUser")
	private User user = new User();
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idUserRole")
	private UserRole userRole = new UserRole();
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idEndereco")
	private EnderecoPOJO endereco;
	
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
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	public BigDecimal getRemuneracao() {
		return remuneracao;
	}
	public void setRemuneracao(BigDecimal remuneracao) {
		this.remuneracao = remuneracao;
	}
	public Date getDataDemissao() {
		return dataDemissao;
	}
	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
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
		if(endereco == null){
			endereco = new EnderecoPOJO();
		}
		return endereco;
	}
	public void setEndereco(EnderecoPOJO endereco) {
		this.endereco = endereco;
	}
	public boolean isStatusFuncionario() {
		return statusFuncionario;
	}
	public void setStatusFuncionario(boolean statusFuncionario) {
		this.statusFuncionario = statusFuncionario;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
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

}

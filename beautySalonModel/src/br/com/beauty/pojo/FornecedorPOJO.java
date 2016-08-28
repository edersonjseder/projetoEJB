package br.com.beauty.pojo;

import java.io.Serializable;
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
@Table(name="fornecedor")
public class FornecedorPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3349022600090212041L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idFornecedor")
	private Integer id;
	
	private String nomeFornecedor;
	private String cpfOuCnpj;
	private String ieOuRg;
	private Date dataCadastro;
	private String telefone;
	private String fax;
	private String email;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idEndereco")
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
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

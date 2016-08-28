package br.com.beauty.pojo;

import java.io.Serializable;

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
@Table(name="endereco")
public class EnderecoPOJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 333444551L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idEndereco")
	private Integer id;

	private String endereco;
	private String bairro;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "idCidade")
	private CidadePOJO cidade = new CidadePOJO();
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "idEstado")
	private EstadoPOJO estado = new EstadoPOJO();
	private String cep;

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CidadePOJO getCidade() {
		return cidade;
	}

	public void setCidade(CidadePOJO cidade) {
		this.cidade = cidade;
	}

	public EstadoPOJO getEstado() {
		return estado;
	}

	public void setEstado(EstadoPOJO estado) {
		this.estado = estado;
	}
}

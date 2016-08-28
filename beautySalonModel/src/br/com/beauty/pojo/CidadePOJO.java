package br.com.beauty.pojo;

import java.io.Serializable;

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
@Table(name="cidade")
public class CidadePOJO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1046035414546407898L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCidade")
	private Integer id;
	
	private String nome;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idEstado")
	private EstadoPOJO estado = new EstadoPOJO();

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

	public EstadoPOJO getEstado() {
		return estado;
	}

	public void setEstado(EstadoPOJO estado) {
		this.estado = estado;
	}
	
}

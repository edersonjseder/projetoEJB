package br.com.beauty.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="estado")
public class EstadoPOJO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2182779267806175646L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idEstado")
	private Integer id;
	
	private String nome;
	private String uf;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "idCidade")
	private List<CidadePOJO> cidades = new ArrayList<CidadePOJO>();
	
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
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public List<CidadePOJO> getCidades() {
		return cidades;
	}
	public void setCidades(List<CidadePOJO> cidades) {
		this.cidades = cidades;
	}
	
}

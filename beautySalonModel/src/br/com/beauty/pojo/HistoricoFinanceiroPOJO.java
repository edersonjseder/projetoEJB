package br.com.beauty.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="historicofinanceiro")
public class HistoricoFinanceiroPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 317399105677042382L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idHistorico")
	private Integer id;
	private String historico;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}
	
}

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
@Table(name="agendamentos")
public class AgendamentosPOJO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3421503000597640923L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idAgendamentos")
	private Integer id;
	
	private Date dataAgendamento;
	private String horaAgendamento;
	private String statusAgendamento;
	private String descricao;
	private BigDecimal precoServico = new BigDecimal(0.0D);
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idServicos")
	private ServicosPOJO servicos = new ServicosPOJO();
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idFuncionario")
	private FuncionarioPOJO funcionarios = new FuncionarioPOJO();

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idClientes")
	private ClientesPOJO clientes = new ClientesPOJO();
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public String getHoraAgendamento() {
		return horaAgendamento;
	}

	public void setHoraAgendamento(String horaAgendamento) {
		this.horaAgendamento = horaAgendamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ServicosPOJO getServicos() {
		return servicos;
	}

	public void setServicos(ServicosPOJO servicos) {
		this.servicos = servicos;
	}

	public FuncionarioPOJO getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(FuncionarioPOJO funcionarios) {
		this.funcionarios = funcionarios;
	}

	public ClientesPOJO getClientes() {
		return clientes;
	}

	public void setClientes(ClientesPOJO clientes) {
		this.clientes = clientes;
	}

	public String getStatusAgendamento() {
		return statusAgendamento;
	}

	public void setStatusAgendamento(String statusAgendamento) {
		this.statusAgendamento = statusAgendamento;
	}

	public BigDecimal getPrecoServico() {
		return precoServico;
	}

	public void setPrecoServico(BigDecimal precoServico) {
		this.precoServico = precoServico;
	}
}

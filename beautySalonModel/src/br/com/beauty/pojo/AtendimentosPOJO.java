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
@Table(name="atendimentos")
public class AtendimentosPOJO implements Serializable{
	
	private static final long serialVersionUID = 118089L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idAtendimentos")
	private Integer id;
	
	private Date dataAtendimento;
	private String horaAtendimento;
	private String descricao;
	private String statusAtendimento;
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
	
	public Date getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	public String getHoraAtendimento() {
		return horaAtendimento;
	}
	public void setHoraAtendimento(String horaAtendimento) {
		this.horaAtendimento = horaAtendimento;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ClientesPOJO getClientes() {
		return clientes;
	}
	public void setClientes(ClientesPOJO clientes) {
		this.clientes = clientes;
	}
	public FuncionarioPOJO getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(FuncionarioPOJO funcionarios) {
		this.funcionarios = funcionarios;
	}
	public ServicosPOJO getServicos() {
		return servicos;
	}
	public void setServicos(ServicosPOJO servicos) {
		this.servicos = servicos;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getStatusAtendimento() {
		return statusAtendimento;
	}
	public void setStatusAtendimento(String statusAtendimento) {
		this.statusAtendimento = statusAtendimento;
	}
	public BigDecimal getPrecoServico() {
		return precoServico;
	}
	public void setPrecoServico(BigDecimal precoServico) {
		this.precoServico = precoServico;
	}

}

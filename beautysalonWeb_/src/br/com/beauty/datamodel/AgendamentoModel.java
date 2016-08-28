package br.com.beauty.datamodel;

import br.com.beauty.pojo.ClientesPOJO;
import br.com.beauty.pojo.FuncionarioPOJO;
import br.com.beauty.pojo.ServicosPOJO;

public class AgendamentoModel {
	
	private Integer id;
	
	private String dataAgendamento;
	private String horaAgendamento;
	private String statusAgendamento;
	private String descricao;
	private String precoServico;
	
	private ServicosPOJO servicos = new ServicosPOJO();
	private FuncionarioPOJO funcionarios = new FuncionarioPOJO();
	private ClientesPOJO clientes = new ClientesPOJO();
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(String dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public String getHoraAgendamento() {
		return horaAgendamento;
	}

	public void setHoraAgendamento(String horaAgendamento) {
		this.horaAgendamento = horaAgendamento;
	}

	public String getStatusAgendamento() {
		return statusAgendamento;
	}

	public void setStatusAgendamento(String statusAgendamento) {
		this.statusAgendamento = statusAgendamento;
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

	public String getPrecoServico() {
		return precoServico;
	}

	public void setPrecoServico(String precoServico) {
		this.precoServico = precoServico;
	}

}

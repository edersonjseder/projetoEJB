package br.com.beauty.datamodel;

import br.com.beauty.pojo.ClientesPOJO;
import br.com.beauty.pojo.FuncionarioPOJO;
import br.com.beauty.pojo.ServicosPOJO;

public class AtendimentoModel {
	
	private Integer id;
	
	private String dataAtendimento;
	private String horaAtendimento;
	private String descricao;
	private String statusAtendimento;
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
	public String getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(String dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	public String getHoraAtendimento() {
		return horaAtendimento;
	}
	public void setHoraAtendimento(String horaAtendimento) {
		this.horaAtendimento = horaAtendimento;
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
	public String getStatusAtendimento() {
		return statusAtendimento;
	}
	public void setStatusAtendimento(String statusAtendimento) {
		this.statusAtendimento = statusAtendimento;
	}
	public String getPrecoServico() {
		return precoServico;
	}
	public void setPrecoServico(String precoServico) {
		this.precoServico = precoServico;
	}
}

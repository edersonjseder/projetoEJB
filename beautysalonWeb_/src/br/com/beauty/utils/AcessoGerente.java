package br.com.beauty.utils;

public class AcessoGerente {
	
	public static final String ACESSO_CADASTRO_AGENDAMENTO = "/pages/protected/agendamento/agendamentoServico.xhtml";
	public static final String ACESSO_LISTAR_AGENDAMENTO = "/pages/protected/caixa/listaAgendamentos.xhtml";
	
	public static final String ACESSO_CADASTRO_ATENDIMENTO = "/pages/protected/atendimento/cadastroAtendimento.xhtml";
	public static final String ACESSO_LISTAR_ATENDIMENTO = "/pages/protected/atendimento/cadastroAtendimento.xhtml";
	
	public static final String ACESSO_CADASTRO_CAIXA = "/pages/protected/caixa/cadastroCaixa.xhtml";
	public static final String ACESSO_LISTAR_CAIXA = "/pages/protected/caixa/listaCaixas.xhtml";

	public static final String ACESSO_CADASTRO_CONTAS_PAGAR = "/pages/protected/contasPagar/cadastroContasPagar.xhtml";
	public static final String ACESSO_LISTAR_CONTAS_PAGAR = "/pages/protected/contasPagar/listaCadastroContasPagar.xhtml";

	public static final String ACESSO_CADASTRO_CONTAS_RECEBER = "/pages/protected/contasReceber/cadastroContasReceber.xhtml";
	public static final String ACESSO_LISTAR_CONTAS_RECEBER = "/pages/protected/contasReceber/listaCadastroContasReceber.xhtml";
	
	public static final String ACESSO_CADASTRO_CLIENTE = "/pages/protected/cliente/cadastroCliente.xhtml";
	public static final String ACESSO_LISTAR_CLIENTE = "/pages/protected/cliente/listaClientes.xhtml";
	
	public static final String ACESSO_CADASTRO_FORNECEDOR = "/pages/protected/fornecedor/cadastroFornecedor.xhtml";
	public static final String ACESSO_LISTAR_FORNECEDOR = "/pages/protected/fornecedor/listaFornecedor.xhtml";
	
	public static final String ACESSO_CADASTRO_FUNCIONARIO = "/pages/protected/funcionario/cadastroFuncionario.xhtml";
	public static final String ACESSO_LISTAR_FUNCIONARIO = "/pages/protected/funcionario/listaFuncionarios.xhtml";
	
	public static final String ACESSO_CADASTRO_PRODUTO = "/pages/protected/produto/cadastroProduto.xhtml";
	public static final String ACESSO_LISTAR_PRODUTO = "/pages/protected/produto/listaProdutos.xhtml";
	
	public static final String ACESSO_CADASTRO_SERVICO = "/pages/protected/servico/cadastroServico.xhtml";
	public static final String ACESSO_LISTAR_SERVICO = "/pages/protected/servico/listaServicos.xhtml";
	
	public static final String ACESSO_CADASTRO_ENTRADA_ESTOQUE = "/pages/protected/estoque/entrada/cadastroEntradaEstoque.xhtml";
	public static final String ACESSO_LISTAR_ENTRADA_ESTOQUE = "/pages/protected/estoque/entrada/listaCadastroEntradaEstoque.xhtml";

	public static final String ACESSO_CADASTRO_SAIDA_ESTOQUE = "/pages/protected/estoque/saida/cadastroSaidaEstoque.xhtml";
	public static final String ACESSO_LISTAR_SAIDA_ESTOQUE = "/pages/protected/estoque/saida/listaCadastroSaidaEstoque.xhtml";

	public static final String ACESSO_HOME_GERENTE = "/pages/users/gerente/homePage.xhtml";
	
	public static final String ACESSO_GERENTE = "/beautysalonWeb_/gerente/";

	public static boolean controlarAcesso(String paginaAtual){
		
		if(paginaAtual.equals(ACESSO_CADASTRO_ATENDIMENTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_AGENDAMENTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_CAIXA)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_CONTAS_PAGAR)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_CONTAS_RECEBER)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_CLIENTE)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_FORNECEDOR)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_FUNCIONARIO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_PRODUTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_SERVICO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_ENTRADA_ESTOQUE)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_SAIDA_ESTOQUE)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_AGENDAMENTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_ATENDIMENTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_CAIXA)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_CONTAS_PAGAR)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_CONTAS_RECEBER)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_CLIENTE)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_FORNECEDOR)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_FUNCIONARIO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_PRODUTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_SERVICO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_ENTRADA_ESTOQUE)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_SAIDA_ESTOQUE)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_HOME_GERENTE)){
			return true;
			
		}else{
			return false;
		}
	}
	
}

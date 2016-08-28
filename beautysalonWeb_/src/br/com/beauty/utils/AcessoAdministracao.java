package br.com.beauty.utils;

public class AcessoAdministracao {
	
	public static final String ACESSO_ERRO_ATENDIMENTO = "/beautysalonWeb_/pages/users/administracao/acesso/semAcesso.xhtml";
	
	public static final String ACESSO_CADASTRO_ATENDIMENTO = "/beautysalonWeb_/pages/protected/atendimento/cadastroAtendimento.xhtml";
	public static final String ACESSO_LISTAR_ATENDIMENTO = "/beautysalonWeb_/pages/protected/atendimento/cadastroAtendimento.xhtml";

	public static final String ACESSO_CADASTRO_CONTAS_PAGAR = "/beautysalonWeb_/pages/protected/contasPagar/cadastroContasPagar.xhtml";
	public static final String ACESSO_LISTAR_CONTAS_PAGAR = "/beautysalonWeb_/pages/protected/contasPagar/listaCadastroContasPagar.xhtml";

	public static final String ACESSO_CADASTRO_CONTAS_RECEBER = "/beautysalonWeb_/pages/protected/contasReceber/cadastroContasReceber.xhtml";
	public static final String ACESSO_LISTAR_CONTAS_RECEBER = "/beautysalonWeb_/pages/protected/contasReceber/listaCadastroContasReceber.xhtml";
	
	public static final String ACESSO_CADASTRO_CLIENTE = "/beautysalonWeb_/pages/protected/cliente/cadastroCliente.xhtml";
	public static final String ACESSO_LISTAR_CLIENTE = "/beautysalonWeb_/pages/protected/cliente/listaClientes.xhtml";
	
	public static final String ACESSO_CADASTRO_FORNECEDOR = "/beautysalonWeb_/pages/protected/fornecedor/cadastroFornecedor.xhtml";
	public static final String ACESSO_LISTAR_FORNECEDOR = "/beautysalonWeb_/pages/protected/fornecedor/listaFornecedor.xhtml";
	
	public static final String ACESSO_CADASTRO_FUNCIONARIO = "/beautysalonWeb_/pages/protected/funcionario/cadastroFuncionario.xhtml";
	public static final String ACESSO_LISTAR_FUNCIONARIO = "/beautysalonWeb_/pages/protected/funcionario/listaFuncionarios.xhtml";
	
	public static final String ACESSO_CADASTRO_PRODUTO = "/beautysalonWeb_/pages/protected/produto/cadastroProduto.xhtml";
	public static final String ACESSO_LISTAR_PRODUTO = "/beautysalonWeb_/pages/protected/produto/listaProdutos.xhtml";
	
	public static final String ACESSO_CADASTRO_SERVICO = "/beautysalonWeb_/pages/protected/servico/cadastroServico.xhtml";
	public static final String ACESSO_LISTAR_SERVICO = "/beautysalonWeb_/pages/protected/servico/listaServicos.xhtml";
	
	public static final String ACESSO_CADASTRO_ENTRADA_ESTOQUE = "/beautysalonWeb_/pages/protected/estoque/entrada/cadastroEntradaEstoque.xhtml";
	public static final String ACESSO_LISTAR_ENTRADA_ESTOQUE = "/beautysalonWeb_/pages/protected/estoque/entrada/listaCadastroEntradaEstoque.xhtml";

	public static final String ACESSO_CADASTRO_SAIDA_ESTOQUE = "/beautysalonWeb_/pages/protected/estoque/saida/cadastroSaidaEstoque.xhtml";
	public static final String ACESSO_LISTAR_SAIDA_ESTOQUE = "/beautysalonWeb_/pages/protected/estoque/saida/listaCadastroSaidaEstoque.xhtml";

	public static final String ACESSO_HOME_ATENDIMENTO = "/beautysalonWeb_/pages/users/administracao/homePage.xhtml";
	public static final String ACESSO_ATENDIMENTO = "/beautysalonWeb_/administracao/";
	
	public static boolean controlarAcesso(String paginaAtual){
		
		if(paginaAtual.equals(ACESSO_CADASTRO_ATENDIMENTO)){
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
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_ATENDIMENTO)){
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
			
		}else if(paginaAtual.equals(ACESSO_ATENDIMENTO)){
			return true;
			
		}else{
			return false;
		}
	}
}

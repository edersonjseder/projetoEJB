package br.com.beauty.utils;

public class AcessoCaixa {
	
	public static final String ACESSO_ERRO_CAIXA = "/beautysalonWeb_/pages/users/caixa/acesso/semAcesso.xhtml";

	public static final String ACESSO_CADASTRO_ATENDIMENTO = "/beautysalonWeb_/pages/protected/atendimento/cadastroAtendimento.xhtml";
	public static final String ACESSO_LISTAR_ATENDIMENTO = "/beautysalonWeb_/pages/protected/atendimento/cadastroAtendimento.xhtml";

	public static final String ACESSO_CADASTRO_CAIXA = "/beautysalonWeb_/pages/protected/caixa/cadastroCaixa.xhtml";
	public static final String ACESSO_LISTAR_CAIXA = "/beautysalonWeb_/pages/protected/caixa/listaCaixas.xhtml";
	
	public static final String ACESSO_HOME_CAIXA = "/beautysalonWeb_/pages/users/caixa/homePage.xhtml";
	
	public static final String ACESSO_CAIXA = "/beautysalonWeb_/caixa/";
	
	public static boolean controlarAcesso(String paginaAtual){
		
		if(paginaAtual.equals(ACESSO_CADASTRO_ATENDIMENTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CADASTRO_CAIXA)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_ATENDIMENTO)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_LISTAR_CAIXA)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_HOME_CAIXA)){
			return true;
			
		}else if(paginaAtual.equals(ACESSO_CAIXA)){
			return true;
			
		}else{
			return false;
		}
	}

}

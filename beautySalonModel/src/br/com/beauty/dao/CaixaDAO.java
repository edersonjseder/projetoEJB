package br.com.beauty.dao;

import java.util.Date;

import br.com.beauty.pojo.CaixaPOJO;

public interface CaixaDAO extends DAO<CaixaPOJO>{
	
	public Number buscarSaldoDinheiroPorMes(Integer mes);
	public Number buscarSaldoChequePorMes(Integer mes);
	public Number buscarTotalSaldoCaixaPorData(Date dtInicial, Date dtFinal);

}

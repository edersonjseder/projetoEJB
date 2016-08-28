package br.com.beauty.dao;

import java.util.Date;
import java.util.List;

import br.com.beauty.pojo.EstoquePOJO;

public interface EstoqueDAO extends DAO<EstoquePOJO> {
	
	public List<EstoquePOJO> listarByTipo(String tipo);
	public Number buscarTotalEstoquePorData(Date dtInicial, Date dtFinal, String tipo);

}

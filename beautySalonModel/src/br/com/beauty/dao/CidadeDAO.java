package br.com.beauty.dao;

import java.util.List;

import br.com.beauty.pojo.CidadePOJO;

public interface CidadeDAO extends DAO<CidadePOJO>{
	
	public List<CidadePOJO> listarById(Integer id);

}

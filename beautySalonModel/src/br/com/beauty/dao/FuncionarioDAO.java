package br.com.beauty.dao;

import java.util.List;

import br.com.beauty.pojo.FuncionarioPOJO;

public interface FuncionarioDAO extends DAO<FuncionarioPOJO> {
	
	public List<FuncionarioPOJO> buscarFuncionariosPorCargo(String cargo);

}

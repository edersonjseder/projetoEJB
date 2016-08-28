package br.com.beauty.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import br.com.beauty.dao.FuncionarioDAO;
import br.com.beauty.pojo.FuncionarioPOJO;

public class FuncionarioDAOImpl extends DAOImpl<FuncionarioPOJO> implements FuncionarioDAO {

	@Override
	public List<FuncionarioPOJO> buscarFuncionariosPorCargo(String cargo) {
		
		String consulta = "SELECT f FROM FuncionarioPOJO f where f.cargo = :cargo";
		TypedQuery<FuncionarioPOJO> typedQuery = getEntityManager().createQuery(consulta, FuncionarioPOJO.class);
		typedQuery.setParameter("cargo", cargo);
		List<FuncionarioPOJO> results = typedQuery.getResultList();
		
		return results;
	}
	
}

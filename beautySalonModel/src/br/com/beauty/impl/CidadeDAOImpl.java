package br.com.beauty.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import br.com.beauty.dao.CidadeDAO;
import br.com.beauty.pojo.CidadePOJO;

public class CidadeDAOImpl extends DAOImpl<CidadePOJO> implements CidadeDAO {

	@Override
	public List<CidadePOJO> listarById(Integer id) {
		String consulta = "SELECT c FROM CidadePOJO c where c.estado.id = :id";
		TypedQuery<CidadePOJO> typedQuery = getEntityManager().createQuery(consulta, CidadePOJO.class);
		typedQuery.setParameter("id", id);
		List<CidadePOJO> results = typedQuery.getResultList();
		
		return results;
	}

}

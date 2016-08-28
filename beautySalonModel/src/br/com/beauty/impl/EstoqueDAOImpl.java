package br.com.beauty.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.beauty.dao.EstoqueDAO;
import br.com.beauty.pojo.EstoquePOJO;

public class EstoqueDAOImpl extends DAOImpl<EstoquePOJO> implements EstoqueDAO {

	@Override
	public List<EstoquePOJO> listarByTipo(String tipo) {
		String consulta = "SELECT c FROM EstoquePOJO c where c.tipoProcesso = :tipo";
		TypedQuery<EstoquePOJO> typedQuery = getEntityManager().createQuery(consulta, EstoquePOJO.class);
		typedQuery.setParameter("tipo", tipo);
		List<EstoquePOJO> results = typedQuery.getResultList();
		
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Number buscarTotalEstoquePorData(Date dtInicial, Date dtFinal, String tipo) {
		
		String consulta = "SELECT e FROM EstoquePOJO e where e.dataProcesso between :dtInicial and :dtFinal and e.tipoProcesso = :tipo";
		Query query = getEntityManager().createQuery(consulta, EstoquePOJO.class);
		query.setParameter("dtInicial", dtInicial);
		query.setParameter("dtFinal", dtFinal);
		query.setParameter("dtFinal", tipo);
		List<EstoquePOJO> lista =  query.getResultList();
		
		Iterator<EstoquePOJO> iterator = lista.iterator();
		EstoquePOJO estoque = null;
		Double soma = 0.0;
		
		while(iterator.hasNext()){
			estoque = iterator.next();
			soma = soma + estoque.getValorTotalEstoque().doubleValue();
		}
		
		return soma;
	}
	
}

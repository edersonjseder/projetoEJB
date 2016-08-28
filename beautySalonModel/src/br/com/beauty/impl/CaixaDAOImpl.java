package br.com.beauty.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import br.com.beauty.dao.CaixaDAO;
import br.com.beauty.pojo.CaixaPOJO;

public class CaixaDAOImpl extends DAOImpl<CaixaPOJO> implements CaixaDAO {
	
	@SuppressWarnings("unchecked")
	public Number buscarSaldoDinheiroPorMes(Integer mes){
		
		String consulta = "SELECT c FROM CaixaPOJO c where c.mes = :mes";
		Query query = getEntityManager().createQuery(consulta, CaixaPOJO.class);
		query.setParameter("mes", mes);
		List<CaixaPOJO> lista =  query.getResultList();
		
		Iterator<CaixaPOJO> iterator = lista.iterator();
		CaixaPOJO caixa = null;
		Double soma = 0.0;
		
		while(iterator.hasNext()){
			caixa = iterator.next();
			soma = soma + caixa.getSaldoCaixaDinheiroMes().doubleValue();
		}
		
		return soma;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Number buscarSaldoChequePorMes(Integer mes) {
		
		String consulta = "SELECT c FROM CaixaPOJO c where c.mes = :mes";
		Query query = getEntityManager().createQuery(consulta, CaixaPOJO.class);
		query.setParameter("mes", mes);
		List<CaixaPOJO> lista =  query.getResultList();
		
		Iterator<CaixaPOJO> iterator = lista.iterator();
		CaixaPOJO caixa = null;
		Double soma = 0.0;
		
		while(iterator.hasNext()){
			caixa = iterator.next();
			soma = soma + caixa.getSaldoCaixaChequeMes().doubleValue();
		}
		
		return soma;
	}
	
	@SuppressWarnings("unchecked")
	public Number buscarTotalSaldoCaixaPorData(Date dtInicial, Date dtFinal) {
		
		String consulta = "SELECT c.saldoCaixaDinheiroMes, c.saldoCaixaChequeMes FROM CaixaPOJO c where c.dataLancamento between :dtInicial and :dtFinal";
		Query query = getEntityManager().createQuery(consulta, CaixaPOJO.class);
		query.setParameter("dtInicial", dtInicial);
		query.setParameter("dtFinal", dtFinal);
		List<CaixaPOJO> lista =  query.getResultList();
		
		Iterator<CaixaPOJO> iterator = lista.iterator();
		CaixaPOJO estoque = null;
		Double soma = 0.0;
		
		while(iterator.hasNext()){
			estoque = iterator.next();
			soma = soma + estoque.getSaldoCaixaDinheiroMes().doubleValue() + estoque.getSaldoCaixaChequeMes().doubleValue();
		}
		
		return soma;
	}

}

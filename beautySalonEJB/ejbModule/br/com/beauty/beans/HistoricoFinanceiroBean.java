package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.HistoricoFinanceiroDAO;
import br.com.beauty.impl.HistoricoFinanceiroDAOImpl;
import br.com.beauty.interfaces.HistoricoFinanceiro;
import br.com.beauty.pojo.HistoricoFinanceiroPOJO;

@Stateful
public class HistoricoFinanceiroBean implements HistoricoFinanceiro {
	
	private HistoricoFinanceiroDAO historicoFinanceiroDAO = new HistoricoFinanceiroDAOImpl();

	@Override
	public void insert(HistoricoFinanceiroPOJO historicoFinanceiro) {
		historicoFinanceiroDAO.save(historicoFinanceiro);
	}

	@Override
	public HistoricoFinanceiroPOJO retrieve(Integer identity) {
		return historicoFinanceiroDAO.retrieveById(identity);
	}

	@Override
	public void update(HistoricoFinanceiroPOJO historicoFinanceiro) {
		historicoFinanceiroDAO.update(historicoFinanceiro);
	}

	@Override
	public List<HistoricoFinanceiroPOJO> listar() {
		return historicoFinanceiroDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		historicoFinanceiroDAO.removeById(id);
	}

	@Override
	public void delete(HistoricoFinanceiroPOJO historicoFinanceiro) throws EJBException {
		historicoFinanceiroDAO.remove(historicoFinanceiro);
	}

}

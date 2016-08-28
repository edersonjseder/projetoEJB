package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.AtendimentoDAO;
import br.com.beauty.impl.AtendimentoDAOImpl;
import br.com.beauty.interfaces.Atendimentos;
import br.com.beauty.pojo.AtendimentosPOJO;

@Stateful
public class AtendimentosBean implements Atendimentos {
	
	private AtendimentoDAO atendimentoDAO = new AtendimentoDAOImpl();

	@Override
	public void insert(AtendimentosPOJO atendimentos) {
		atendimentoDAO.save(atendimentos);
	}

	@Override
	public AtendimentosPOJO retrieve(Integer identity) {
		
		return atendimentoDAO.retrieveById(identity);
	}

	@Override
	public void update(AtendimentosPOJO atendimentos) {
		atendimentoDAO.update(atendimentos);
	}

	@Override
	public List<AtendimentosPOJO> listar() {
		return atendimentoDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		atendimentoDAO.removeById(id);
	}

	@Override
	public void delete(AtendimentosPOJO atendimentos) throws EJBException {
		atendimentoDAO.remove(atendimentos);
	}

}

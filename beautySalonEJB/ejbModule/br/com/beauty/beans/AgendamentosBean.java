package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.AgendamentoDAO;
import br.com.beauty.impl.AgendamentoDAOImpl;
import br.com.beauty.interfaces.Agendamentos;
import br.com.beauty.pojo.AgendamentosPOJO;

@Stateful
public class AgendamentosBean implements Agendamentos {
	
	private AgendamentoDAO agendamentoDAO = new AgendamentoDAOImpl();

	@Override
	public void insert(AgendamentosPOJO agendamentos) {
		agendamentoDAO.save(agendamentos);
		
	}

	@Override
	public AgendamentosPOJO retrieve(Integer identity) {
		return agendamentoDAO.retrieveById(identity);
	}

	@Override
	public void update(AgendamentosPOJO agendamentos) {
		agendamentoDAO.update(agendamentos);
		
	}

	@Override
	public void deleteById(Integer id) {
		agendamentoDAO.removeById(id);
		
	}

	@Override
	public List<AgendamentosPOJO> listar() {
		return agendamentoDAO.listar();
	}

	@Override
	public void delete(AgendamentosPOJO agendamentos) throws EJBException {
		agendamentoDAO.remove(agendamentos);
	}

}

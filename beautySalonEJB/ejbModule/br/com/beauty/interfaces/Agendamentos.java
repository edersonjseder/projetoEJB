package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.AgendamentosPOJO;

@Remote
public interface Agendamentos {
	public void insert(AgendamentosPOJO agendamentos) throws EJBException;
	public AgendamentosPOJO retrieve(Integer identity) throws EJBException;
	public void update(AgendamentosPOJO agendamentos) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(AgendamentosPOJO agendamentos) throws EJBException;
	public List<AgendamentosPOJO> listar() throws EJBException;
}

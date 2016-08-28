package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.EstadoDAO;
import br.com.beauty.impl.EstadoDAOImpl;
import br.com.beauty.interfaces.Estado;
import br.com.beauty.pojo.EstadoPOJO;

@Stateful
public class EstadoBean implements Estado {
	
	private EstadoDAO estadoDAO = new EstadoDAOImpl();

	@Override
	public void insert(EstadoPOJO estado) {
		estadoDAO.save(estado);
	}

	@Override
	public EstadoPOJO retrieve(Integer identity) {
		return estadoDAO.retrieveById(identity);
	}

	@Override
	public void update(EstadoPOJO estado) {
		estadoDAO.update(estado);
	}

	@Override
	public List<EstadoPOJO> listar() {
		return estadoDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		estadoDAO.removeById(id);
	}

	@Override
	public void delete(EstadoPOJO estado) throws EJBException {
		estadoDAO.remove(estado);
	}
}

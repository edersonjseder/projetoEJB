package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.ClientesDAO;
import br.com.beauty.impl.ClientesDAOImpl;
import br.com.beauty.interfaces.Clientes;
import br.com.beauty.pojo.ClientesPOJO;

@Stateful
public class ClientesBean implements Clientes {
	
	private ClientesDAO clientesDAO = new ClientesDAOImpl();

	@Override
	public void insert(ClientesPOJO clientes) {
		clientesDAO.save(clientes);
	}

	@Override
	public ClientesPOJO retrieve(Integer identity) {
		return clientesDAO.retrieveById(identity);
	}

	@Override
	public void update(ClientesPOJO clientes) {
		clientesDAO.update(clientes);
	}

	@Override
	public List<ClientesPOJO> listar() {
		return clientesDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		clientesDAO.removeById(id);
	}

	@Override
	public void delete(ClientesPOJO clientes) throws EJBException {
		clientesDAO.remove(clientes);
	}

}

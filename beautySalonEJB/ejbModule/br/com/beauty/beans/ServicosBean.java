package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.ServicosDAO;
import br.com.beauty.impl.ServicosDAOImpl;
import br.com.beauty.interfaces.Servicos;
import br.com.beauty.pojo.ServicosPOJO;

@Stateful
public class ServicosBean implements Servicos {
	
	private ServicosDAO servicosDAO = new ServicosDAOImpl();

	@Override
	public void insert(ServicosPOJO servicos) {
		servicosDAO.save(servicos);
	}

	@Override
	public ServicosPOJO retrieve(Integer identity) {
		return servicosDAO.retrieveById(identity);
	}

	@Override
	public void update(ServicosPOJO servicos) {
		servicosDAO.update(servicos);
	}

	@Override
	public List<ServicosPOJO> listar() {
		return servicosDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		servicosDAO.removeById(id);
	}

	@Override
	public void delete(ServicosPOJO servicos) throws EJBException {
		servicosDAO.remove(servicos);
	}
}

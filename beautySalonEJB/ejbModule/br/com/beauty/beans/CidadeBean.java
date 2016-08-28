package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.CidadeDAO;
import br.com.beauty.impl.CidadeDAOImpl;
import br.com.beauty.interfaces.Cidade;
import br.com.beauty.pojo.CidadePOJO;

@Stateful
public class CidadeBean implements Cidade {
	
	private CidadeDAO cidadeDAO = new CidadeDAOImpl();

	@Override
	public void insert(CidadePOJO cidade) {
		cidadeDAO.save(cidade);
	}

	@Override
	public CidadePOJO retrieve(Integer identity) {
		return cidadeDAO.retrieveById(identity);
	}

	@Override
	public void update(CidadePOJO cidade) {
		cidadeDAO.update(cidade);
	}

	@Override
	public List<CidadePOJO> listar() {
		return cidadeDAO.listar();
	}

	@Override
	public List<CidadePOJO> listarById(Integer id) throws EJBException {
		return cidadeDAO.listarById(id);
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		cidadeDAO.removeById(id);
	}

	@Override
	public void delete(CidadePOJO cidade) throws EJBException {
		cidadeDAO.remove(cidade);
	}
}

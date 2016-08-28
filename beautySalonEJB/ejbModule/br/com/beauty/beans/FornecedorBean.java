package br.com.beauty.beans;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.FornecedorDAO;
import br.com.beauty.impl.FornecedorDAOImpl;
import br.com.beauty.interfaces.Fornecedor;
import br.com.beauty.pojo.FornecedorPOJO;

@Stateful
public class FornecedorBean implements Fornecedor {
	
	private FornecedorDAO fornecedorDAO = new FornecedorDAOImpl();

	@Override
	public void insert(FornecedorPOJO fornecedor) {
		fornecedorDAO.save(fornecedor);
	}

	@Override
	public FornecedorPOJO retrieve(Integer identity) {
		return fornecedorDAO.retrieveById(identity);
	}

	@Override
	public void update(FornecedorPOJO fornecedor) {
		fornecedorDAO.update(fornecedor);
	}

	@Override
	public List<FornecedorPOJO> listar() {
		return fornecedorDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		fornecedorDAO.removeById(id);
	}

	@Override
	public void delete(FornecedorPOJO fornecedor) throws EJBException {
		fornecedorDAO.remove(fornecedor);
	}

}

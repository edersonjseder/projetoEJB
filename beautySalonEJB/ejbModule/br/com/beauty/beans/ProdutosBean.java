package br.com.beauty.beans;

import java.util.List;

import javax.ejb.Stateful;

import br.com.beauty.dao.ProdutosDAO;
import br.com.beauty.impl.ProdutosDAOImpl;
import br.com.beauty.interfaces.Produtos;
import br.com.beauty.pojo.ProdutosPOJO;

@Stateful
public class ProdutosBean implements Produtos {
	
	private ProdutosDAO produtosDAO = new ProdutosDAOImpl();

	@Override
	public void insert(ProdutosPOJO produtos) {
		produtosDAO.save(produtos);
	}

	@Override
	public ProdutosPOJO retrieve(Integer identity) {
		return produtosDAO.retrieveById(identity);
	}

	@Override
	public void update(ProdutosPOJO produtos) {
		produtosDAO.update(produtos);
	}

	@Override
	public List<ProdutosPOJO> listar() {
		return produtosDAO.listar();
	}

	@Override
	public void deleteById(Integer id) {
		produtosDAO.removeById(id);
	}

	@Override
	public void delete(ProdutosPOJO produtos) {
		produtosDAO.remove(produtos);
	}

}

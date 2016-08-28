package br.com.beauty.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.EstoqueDAO;
import br.com.beauty.impl.EstoqueDAOImpl;
import br.com.beauty.interfaces.Estoque;
import br.com.beauty.pojo.EstoquePOJO;

@Stateful
public class EstoqueBean implements Estoque {
	
	private EstoqueDAO estoqueDAO = new EstoqueDAOImpl();

	@Override
	public void insert(EstoquePOJO estoque) throws EJBException {
		
		Integer quantidade = estoque.getQuantidade();
		Double valorUnitario = estoque.getValorUnitario().doubleValue();
		
		Double valorTotalEstoque = (valorUnitario * quantidade);
		
		estoque.setQtdeAtualEstoque(quantidade);
		estoque.setValorTotalEstoque(new BigDecimal(valorTotalEstoque));
		
		estoqueDAO.save(estoque);
	}

	@Override
	public EstoquePOJO retrieve(Integer identity) throws EJBException {
		return estoqueDAO.retrieveById(identity);
	}

	@Override
	public void update(EstoquePOJO estoque) throws EJBException {
		estoqueDAO.update(estoque);
	}

	@Override
	public List<EstoquePOJO> listar() throws EJBException {
		return estoqueDAO.listar();
	}

	@Override
	public List<EstoquePOJO> listarByTipo(String tipo) throws EJBException {
		return estoqueDAO.listarByTipo(tipo);
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		estoqueDAO.removeById(id);
	}

	@Override
	public void delete(EstoquePOJO estoque) throws EJBException {
		estoqueDAO.remove(estoque);
	}

	@Override
	public Number buscarTotalEstoquePorData(Date dtInicial, Date dtFinal, String tipo) throws EJBException {
		return estoqueDAO.buscarTotalEstoquePorData(dtInicial, dtFinal, tipo);
	}
}

package br.com.beauty.beans;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.ContasAPagarDAO;
import br.com.beauty.impl.ContasAPagarDAOImpl;
import br.com.beauty.interfaces.ContasAPagar;
import br.com.beauty.pojo.ContasAPagarPOJO;

@Stateful
public class ContasAPagarBean implements ContasAPagar {
	
	private ContasAPagarDAO contasAPagarDAO = new ContasAPagarDAOImpl();
	
	@Override
	public void insert(ContasAPagarPOJO contasAPagar) throws EJBException {
		
		Double total = (contasAPagar.getValorParcela().doubleValue() * contasAPagar.getNroParcelas());
		
		contasAPagar.setTotalAPagar(new BigDecimal(total));
		
		contasAPagarDAO.save(contasAPagar);
	}
	
	@Override
	public ContasAPagarPOJO retrieve(Integer identity) throws EJBException {
		return contasAPagarDAO.retrieveById(identity);
	}
	
	@Override
	public void update(ContasAPagarPOJO contasAPagar) throws EJBException {
		Double valorParcial = null;
		Double valorParcialAPagar = null;
		
		if(null != contasAPagar.getNroParcelasPagas() || contasAPagar.getNroParcelasPagas() != 0){
			
			valorParcial = contasAPagar.getValorParcela().doubleValue() * contasAPagar.getNroParcelasPagas();
			
			contasAPagar.setValorPago(new BigDecimal(valorParcial));
			
			valorParcialAPagar = contasAPagar.getTotalAPagar().doubleValue() - valorParcial;
			
			contasAPagar.setValorAPagar(new BigDecimal(valorParcialAPagar));
			
			if(contasAPagar.getNroParcelasAPagar() == 0){
				contasAPagar.setStatusContasPagar("Deferida");
			}
			
		}
		
		contasAPagarDAO.update(contasAPagar);
	}
	
	@Override
	public void deleteById(Integer id) throws EJBException {
		contasAPagarDAO.removeById(id);
	}
	
	@Override
	public void delete(ContasAPagarPOJO contasAPagar) throws EJBException {
		contasAPagarDAO.remove(contasAPagar);
	}
	
	@Override
	public List<ContasAPagarPOJO> listar() throws EJBException {
		return contasAPagarDAO.listar();
	}


}

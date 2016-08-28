package br.com.beauty.beans;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateful;

import br.com.beauty.dao.ContasAReceberDAO;
import br.com.beauty.impl.ContasAReceberDAOImpl;
import br.com.beauty.interfaces.ContasAReceber;
import br.com.beauty.pojo.ContasAReceberPOJO;

@Stateful
public class ContasAReceberBean implements ContasAReceber {
	
	private ContasAReceberDAO contasAReceberDAO = new ContasAReceberDAOImpl();

	@Override
	public void insert(ContasAReceberPOJO contasAReceber) throws EJBException {
		
		Double total = (contasAReceber.getValorParcela().doubleValue() * contasAReceber.getNroParcelas());
		
		contasAReceber.setTotalAReceber(new BigDecimal(total));
		
		contasAReceberDAO.save(contasAReceber);
	}

	@Override
	public ContasAReceberPOJO retrieve(Integer identity) throws EJBException {
		return contasAReceberDAO.retrieveById(identity);
	}

	@Override
	public void update(ContasAReceberPOJO contasAReceber) throws EJBException {
		Double valorParcial = null;
		Double valorParcialAPagar = null;
		
		if(null != contasAReceber.getNroParcelasRecebidas() || contasAReceber.getNroParcelasRecebidas() != 0){
			
			valorParcial = contasAReceber.getValorParcela().doubleValue() * contasAReceber.getNroParcelasRecebidas();
			
			contasAReceber.setValorRecebido(new BigDecimal(valorParcial));
			
			valorParcialAPagar = contasAReceber.getTotalAReceber().doubleValue() - valorParcial;
			
			contasAReceber.setValorAReceber(new BigDecimal(valorParcialAPagar));
			
			if(contasAReceber.getNroParcelasAReceber() == 0){
				contasAReceber.setStatusContasReceber("Deferida");
			}
			
		}
		
		contasAReceberDAO.update(contasAReceber);
	}

	@Override
	public List<ContasAReceberPOJO> listar() throws EJBException {
		return contasAReceberDAO.listar();
	}

	@Override
	public void deleteById(Integer id) throws EJBException {
		contasAReceberDAO.removeById(id);
	}

	@Override
	public void delete(ContasAReceberPOJO contasAReceber) throws EJBException {
		contasAReceberDAO.remove(contasAReceber);
	}
	
}

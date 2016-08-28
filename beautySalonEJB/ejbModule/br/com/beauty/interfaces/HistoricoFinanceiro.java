package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.HistoricoFinanceiroPOJO;

@Remote
public interface HistoricoFinanceiro {
	public void insert(HistoricoFinanceiroPOJO historicoFinanceiro) throws EJBException;
	public HistoricoFinanceiroPOJO retrieve(Integer identity) throws EJBException;
	public void update(HistoricoFinanceiroPOJO historicoFinanceiro) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(HistoricoFinanceiroPOJO historicoFinanceiro) throws EJBException;
	public List<HistoricoFinanceiroPOJO> listar() throws EJBException;
	
}

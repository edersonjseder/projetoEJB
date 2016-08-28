package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.ContasAReceberPOJO;

@Remote
public interface ContasAReceber {
	public void insert(ContasAReceberPOJO contasAReceber) throws EJBException;
	public ContasAReceberPOJO retrieve(Integer identity) throws EJBException;
	public void update(ContasAReceberPOJO contasAReceber) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(ContasAReceberPOJO contasAReceber) throws EJBException;
	public List<ContasAReceberPOJO> listar() throws EJBException;
	
}

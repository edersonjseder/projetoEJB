package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.ContasAPagarPOJO;

@Remote
public interface ContasAPagar {
	public void insert(ContasAPagarPOJO contasAPagar) throws EJBException;
	public ContasAPagarPOJO retrieve(Integer identity) throws EJBException;
	public void update(ContasAPagarPOJO contasAPagar) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(ContasAPagarPOJO contasAPagar) throws EJBException;
	public List<ContasAPagarPOJO> listar() throws EJBException;
	
}

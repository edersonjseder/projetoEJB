package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.FornecedorPOJO;

@Remote
public interface Fornecedor {
	public void insert(FornecedorPOJO fornecedor) throws EJBException;
	public FornecedorPOJO retrieve(Integer identity) throws EJBException;
	public void update(FornecedorPOJO fornecedor) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(FornecedorPOJO fornecedor) throws EJBException;
	public List<FornecedorPOJO> listar() throws EJBException;
	
}

package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.CidadePOJO;

@Remote
public interface Cidade {
	public void insert(CidadePOJO cidade) throws EJBException;
	public CidadePOJO retrieve(Integer identity) throws EJBException;
	public void update(CidadePOJO cidade) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(CidadePOJO cidade) throws EJBException;
	public List<CidadePOJO> listar() throws EJBException;
	public List<CidadePOJO> listarById(Integer id) throws EJBException;
	
}

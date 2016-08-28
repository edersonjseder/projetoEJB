package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.EstadoPOJO;

@Remote
public interface Estado {
	public void insert(EstadoPOJO estado) throws EJBException;
	public EstadoPOJO retrieve(Integer identity) throws EJBException;
	public void update(EstadoPOJO estado) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(EstadoPOJO estado) throws EJBException;
	public List<EstadoPOJO> listar() throws EJBException;
	
}

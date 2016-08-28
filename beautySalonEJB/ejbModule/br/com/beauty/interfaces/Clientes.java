package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.ClientesPOJO;

@Remote
public interface Clientes {
	public void insert(ClientesPOJO clientes) throws EJBException;
	public ClientesPOJO retrieve(Integer identity) throws EJBException;
	public void update(ClientesPOJO clientes) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(ClientesPOJO clientes) throws EJBException;
	public List<ClientesPOJO> listar() throws EJBException;
	
}

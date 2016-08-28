package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.ServicosPOJO;

@Remote
public interface Servicos {
	public void insert(ServicosPOJO servicos) throws EJBException;
	public ServicosPOJO retrieve(Integer identity) throws EJBException;
	public void update(ServicosPOJO servicos) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(ServicosPOJO servicos) throws EJBException;
	public List<ServicosPOJO> listar() throws EJBException;
	
}

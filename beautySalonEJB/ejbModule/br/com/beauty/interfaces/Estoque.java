package br.com.beauty.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.EstoquePOJO;

@Remote
public interface Estoque {
	public void insert(EstoquePOJO estoque) throws EJBException;
	public EstoquePOJO retrieve(Integer identity) throws EJBException;
	public void update(EstoquePOJO estoque) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(EstoquePOJO estoque) throws EJBException;
	public List<EstoquePOJO> listar() throws EJBException;
	public List<EstoquePOJO> listarByTipo(String tipo) throws EJBException;
	public Number buscarTotalEstoquePorData(Date dtInicial, Date dtFinal, String tipo) throws EJBException;
	
}

package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.AtendimentosPOJO;

@Remote
public interface Atendimentos {
	public void insert(AtendimentosPOJO atendimentos) throws EJBException;
	public AtendimentosPOJO retrieve(Integer identity) throws EJBException;
	public void update(AtendimentosPOJO atendimentos) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(AtendimentosPOJO atendimentos) throws EJBException;
	public List<AtendimentosPOJO> listar() throws EJBException;
	
}

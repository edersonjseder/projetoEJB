package br.com.beauty.interfaces;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import br.com.beauty.pojo.FuncionarioPOJO;

@Remote
public interface Funcionarios {
	public void insert(FuncionarioPOJO funcionario) throws EJBException;
	public FuncionarioPOJO retrieve(Integer identity) throws EJBException;
	public void update(FuncionarioPOJO funcionario) throws EJBException;
	public void deleteById(Integer id) throws EJBException;
	public void delete(FuncionarioPOJO funcionario) throws EJBException;
	public List<FuncionarioPOJO> listar() throws EJBException;
	public List<FuncionarioPOJO> buscarFuncionariosPorCargo(String cargo) throws EJBException;
	
}
